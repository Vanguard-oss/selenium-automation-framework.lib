package com.vanguard.web.selenium.exceptions;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.selenium.utils.ScreenshotUtility;


/**
 * Thrown when an element is either not present at all, or is not enabled and we want to take some action on it.
 * Ex: click(), getText(), type() etc.
 * In addition to failing the test with this error, we take a screenshot and give it a name that illustrates 
 * what the locator was, what page we were on, and what action we were trying to take, so the user can have more info on the test failure.
 * 
 * NOTE: This is NOT thrown for isDisplayed(), isEnabled(), or isSelected(), those methods would just return a value of false.
 * 
 */
public class CouldNotTakeActionOnElementException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CouldNotTakeActionOnElementException(String locator, String page, String action, WebDriver driver) {
		super(getMessage(locator, page, action));
		ScreenshotUtility.takeScreenshot(getMessage(locator, page, action), driver);
	}

	public static String getMessage(String locator, String page, String action) {
		return "Element could not be found. Locator: " + locator + ", on Page: " + page + ", while trying to take action: " + action;
	}

}
