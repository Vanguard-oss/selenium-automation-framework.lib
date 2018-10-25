package com.vanguard.web.selenium.exceptions;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.selenium.utils.ScreenshotUtility;


/**
 * Used within TableUtilities to signify an index out of bounds when looking for a cell Element outside the row or column bounds of the table.
 * 
 */
public class InvalidElementException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidElementException(String message, WebDriver driver) {
		super(message);
		ScreenshotUtility.takeScreenshot(message, driver);
	}

}
