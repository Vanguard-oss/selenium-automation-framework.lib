package com.vanguard.web.selenium.exceptions;

/**
 * Thrown when trying to create a page object when the driver object is null.  All page constructors must be passed a valid WebDriver object.
 * 
 */
public class NullWebDriverException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String defaultMessage = "You can not create an instance of this object with a null WebDriver";

	public NullWebDriverException() {
		super(defaultMessage);
	}
	
}
