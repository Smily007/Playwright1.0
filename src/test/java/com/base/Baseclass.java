package com.base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.testng.annotations.*;

public class Baseclass {
	protected Playwright playwright;
	protected Browser browser;
	protected BrowserContext context;
	protected Page page;

	public void setup(String browserType) {
		playwright = Playwright.create();

		switch (browserType.toLowerCase()) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			break;

		case "chromium":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		case "firefox":
			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		case "webkit":
			browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser type: " + browserType);
		}

		context = browser.newContext();
		page = context.newPage();
	}

	public void navigate(String url) {
		page.navigate(url);
	}

	public void click(Locator locator) {
		locator.click();
	}

	public void fill(Locator locator, String text) {
		locator.fill(text);
	}

	public void waitFor(Locator locator) {
		locator.waitFor();
	}

	public String getText(Locator locator) {
		return locator.textContent();
	}

	public boolean verifyText(Locator locator, String expectedText) {
		String actualText = locator.textContent();
		return actualText.equals(expectedText);
	}

	public boolean isVisible(Locator locator) {
		return locator.isVisible();
	}

	public boolean isEnabled(Locator locator) {
		return locator.isEnabled();
	}

	public void hover(Locator locator) {
		locator.hover();
	}

	// Select dropdown by visible text
	public void selectDropdownByVisibleText(Locator locator, String visibleText) {
		locator.selectOption(new SelectOption().setLabel(visibleText));
	}

	// Press a key (example: ENTER, TAB, etc.)
	public void pressKey(Locator locator, String key) {
		locator.press(key);
	}

	// Upload file
	public void uploadFile(Locator locator, Path filePath) {
		locator.setInputFiles(filePath);
	}

	// Scroll to element
	public void scrollToElement(Locator locator) {
		locator.scrollIntoViewIfNeeded();
	}

	// Select dropdown by value
	public void selectDropdownByValue(Locator locator, String value) {
		locator.selectOption(new SelectOption().setValue(value));
	}

	// Take screenshot of an element
	public byte[] takeElementScreenshot(Locator locator) {
		return locator.screenshot();
	}

	// Refresh page
	public void refreshPage() {
		page.reload();
	}

	// Get current page URL
	public String getCurrentUrl() {
		return page.url();
	}

	public static void takeFullPageScreenshot(Page page) {
		try {
			// Use current working directory
			String saveFolderPath = System.getProperty("user.dir");

			// Ensure the folder exists (technically always exists, but for consistency)
			File folder = new File(saveFolderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			// Get and sanitize the page title
			String pageTitle = page.title();
			if (pageTitle == null || pageTitle.isEmpty()) {
				pageTitle = "screenshot";
			}
			String safeTitle = pageTitle.replaceAll("[^a-zA-Z0-9\\-_]", "_");

			// Set the screenshot path
			Path screenshotPath = Paths.get(saveFolderPath, safeTitle + ".png");

			// Take and save the screenshot
			page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(screenshotPath));

			System.out.println("Screenshot saved at: " + screenshotPath);

		} catch (Exception e) {
			System.err.println("Failed to take screenshot: " + e.getMessage());
		}
	}

	public static String captureScreen(Page page, String tname) {
		// Generate timestamp
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// Create dynamic screenshot file path
		String screenshotDir = System.getProperty("user.dir") + "/screenshots/";

		String destination = screenshotDir + tname + "_" + timeStamp + ".png";

		// Ensure the directory exists
		File directory = new File(screenshotDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		try {
			// Take the screenshot using Playwright
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(destination)));
		} catch (Exception e) {
			System.out.println("Error while capturing screenshot: " + e.getMessage());
		}

		return destination;
	}

	public static void dragAndDrop(Locator source, Locator target) {
		try {
			source.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			target.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			source.dragTo(target);
			System.out.println("Drag and Drop completed.");
		} catch (Exception e) {
			System.err.println("Drag and Drop failed: " + e.getMessage());
		}
	}

//	public static Frame getIframe(Page page, String iframeSelector) {
//	    try {
//	        // Locate the iframe and wait for it to become visible
//	        Locator iframeLocator = page.locator(iframeSelector);
//	        iframeLocator.waitFor(new Locator.WaitForOptions()
//	            .setState(WaitForSelectorState.VISIBLE)
//	            .setTimeout(10000)); // wait max 10 seconds
//
//	        // Ensure that the iframe is loaded
//	        if (iframeLocator.isVisible()) {
//	            System.out.println("Iframe is visible, trying to get frame...");
//	        } else {
//	            System.err.println("Iframe is not visible: " + iframeSelector);
//	            throw new RuntimeException("Iframe not visible: " + iframeSelector);
//	        }
//
//	        // Get the Frame using the locator's context (we're using frameLocator())
//	        Frame frame = iframeLocator.frame();
//	        if (frame == null) {
//	            System.err.println("Failed to access the frame inside iframe: " + iframeSelector);
//	            throw new RuntimeException("Iframe not loaded properly: " + iframeSelector);
//	        }
//
//	        // Return the frame object
//	        System.out.println("Successfully switched to iframe: " + iframeSelector);
//	        return frame;
//
//	    } catch (Exception e) {
//	        System.err.println("Error accessing iframe: " + e.getMessage());
//	        throw new RuntimeException("Error accessing iframe: " + iframeSelector, e);
//	    }
//	}

	public static int getValuePositionInList(List<Locator> elements, String value) {
		for (int i = 0; i < elements.size(); i++) {
			String text = elements.get(i).textContent().trim();
			if (text.toLowerCase().contains(value.toLowerCase())) {
				return i;
			}
		}
		return -1; // Return -1 if not found
	}

	public static int getValuePositionInAttributes(List<Locator> elements, String value) {
		for (int i = 0; i < elements.size(); i++) {
			String attributeValue = elements.get(i).getAttribute("value");
			if (attributeValue != null && attributeValue.trim().contains(value)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isValuePresentInList(List<Locator> elements, String value) {
		for (Locator element : elements) {
			String text = element.textContent().trim();
			System.out.println("Actual: " + text.toLowerCase());
			System.out.println("Expected: " + value.toLowerCase());
			if (text.toLowerCase().contains(value.toLowerCase())) {
				System.out.println("****** Found match ******");
				return true;
			}
		}
		return false;
	}

	public static void sortDropDown(Locator dropdownLocator) {
		List<String> originalList = dropdownLocator.allInnerTexts();
		List<String> tempList = new ArrayList<>(originalList);

		System.out.println("Original list: " + originalList);
		System.out.println("Temp list before sort: " + tempList);

		Collections.sort(tempList);

		System.out.println("Original list after sorting: " + originalList);
		System.out.println("Temp list after sorting: " + tempList);

		if (originalList.equals(tempList)) {
			System.out.println("Dropdown is sorted.");
		} else {
			System.out.println("Dropdown is NOT sorted.");
		}
	}

	public static void printEachItemText(List<ElementHandle> elementHandles) {
		for (ElementHandle element : elementHandles) {
			String text = element.textContent();
			System.out.println("Item text: " + text);
		}
	}

	@AfterSuite
	public void teardown() {
		browser.close();
		playwright.close();
	}
}
