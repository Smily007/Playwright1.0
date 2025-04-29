package com.utilities;

import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

public class Fakedata {

	static Faker faker = new Faker();

	public static String getFullName() {
		return faker.name().fullName(); // e.g. "Rajiv Sharma"
	}

	public static String getFirstName() {
		return faker.name().firstName(); // e.g. "Anjali"
	}

	public static String getLastName() {
		return faker.name().lastName(); // e.g. "Patel"
	}

	public static String getEmail() {
		return faker.internet().emailAddress(); // e.g. "rajiv.sharma@example.com"
	}

	public static String getPhoneNumber() {
		return faker.phoneNumber().cellPhone(); // e.g. "+91 98765 43210"
	}

	public static String getPANNumber() {
		return faker.regexify("[A-Z]{5}[0-9]{4}[A-Z]"); // e.g. "BNZAA2315K"
	}

	public static String getCity() {
		return faker.address().city(); // e.g. "Mumbai"
	}

	public static String getState() {
		return faker.address().state(); // e.g. "Maharashtra"
	}

	public static String getCompanyName() {
		return faker.company().name(); // e.g. "Infosys Ltd."
	}

	public static String getStreetAddress() {
		return faker.address().streetAddress(); // e.g. "123 MG Road"
	}

	public static String getZipCode() {
		return faker.address().zipCode(); // e.g. "400001"
	}

	public static String getCountry() {
		return faker.address().country(); // e.g. "India"
	}

	public static String getFullAddress() {
		return faker.address().fullAddress(); // e.g. "123 MG Road, Pune, Maharashtra 400001, India"
	}

	public static String getJobTitle() {
		return faker.job().title(); // e.g. "Software Engineer"
	}

	public static String getJobField() {
		return faker.job().field(); // e.g. "Technology"
	}

	public static String getCompanyIndustry() {
		return faker.company().industry(); // e.g. "Information Technology"
	}

	public static String getCompanyCatchPhrase() {
		return faker.company().catchPhrase(); // e.g. "Empowering the future of technology"
	}

	public static String getCreditCardNumber() {
		return faker.finance().creditCard(); // e.g. "1234 5678 9012 3456"
	}

	public static String getRandomWord() {
		return faker.lorem().word(); // e.g. "innovation"
	}

	public static String getRandomSentence() {
		return faker.lorem().sentence(); // e.g. "Technology is evolving rapidly."
	}

	public static String getRandomParagraph() {
		return faker.lorem().paragraph(); // e.g. "In the modern world, technology is advancing at a rapid pace. It's
											// changing the way we live and work."
	}

	public static String getPastDate() {
	    return faker.date().past(10, TimeUnit.DAYS).toString();  // Random date in the past 10 days
	}

	public static String getFutureDate() {
	    return faker.date().future(5, TimeUnit.DAYS).toString();  // Random date in the next 5 days
	}

	public static String getBirthday() {
	    return faker.date().birthday().toString();  // Random birthday date
	}

}
