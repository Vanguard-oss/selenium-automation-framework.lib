package com.vanguard.web.selenium.exceptions;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.selenium.utils.ScreenshotUtility;

/**
 * Thrown any time a PageObject's isLoaded() method waits the entire explicit wait time and returns false
 * This indicates something went wrong in your test, as the browser is no longer where the test expects it to be.
 * A screenshot is taken along with failing the test to give the user more context around the failure.
 * 
 */
public class SeleniumNavigationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "Could not load Page";

	public SeleniumNavigationException(WebDriver driver) {
		super(defaultMessage);
		ScreenshotUtility.takeScreenshot(defaultMessage, driver);
	}
	
	public SeleniumNavigationException(String message, WebDriver driver) {
		super(message);
		ScreenshotUtility.takeScreenshot(message, driver);
	}

}
