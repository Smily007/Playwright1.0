package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateExcel {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private FileOutputStream outputStream;
	private FileInputStream inputStream;
	private static File copiedExcelFile;

	// Testdata pathHelper = new Testdata();

	public XSSFWorkbook loadWorkbook() {
		File sourceDirectory = new File(Testdata.FilePath);
		File originalFile = new File(sourceDirectory, "TestCases.xlsx");

		if (copiedExcelFile == null) {
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			File targetDirectory = new File(Testdata.TestcasePath);
			copiedExcelFile = new File(targetDirectory, "Test_" + timestamp + ".xlsx");

			try {
				System.out.println(originalFile);
				Files.copy(originalFile.toPath(), copiedExcelFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			inputStream = new FileInputStream(copiedExcelFile);
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return workbook;
	}

	public int findColumnIndex(String sheetName, String columnName) {
		sheet = loadWorkbook().getSheet(sheetName);
		Row headerRow = sheet.getRow(sheet.getFirstRowNum());
		for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
			if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;
	}

	public int findRowIndex(String sheetName, String testcaseId) {
		sheet = loadWorkbook().getSheet(sheetName);
		int columnIndex = findColumnIndex(sheetName, "Test case ID");
		System.out.println(columnIndex);
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
			Cell cell = sheet.getRow(i).getCell(columnIndex);
			System.out.println(cell.getStringCellValue());
			if (cell.getStringCellValue().equalsIgnoreCase(testcaseId)) {
				return i;
			}
		}
		return 0;
	}

	public void updateTestResult(String sheetName, String testcaseId, String status, String comments) {
		workbook = loadWorkbook();
		sheet = workbook.getSheet(sheetName);

		int rowIndex = findRowIndex(sheetName, testcaseId);
		int statusColIndex = findColumnIndex(sheetName, "status");
		int commentsColIndex = findColumnIndex(sheetName, "comments");

		Row row = sheet.getRow(rowIndex);
		row.createCell(statusColIndex).setCellValue(status);
		row.createCell(commentsColIndex).setCellValue(comments);

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			outputStream = new FileOutputStream(copiedExcelFile);
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
