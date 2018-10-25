package com.vanguard.web.selenium.startingpages;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.automation.tools.selenium.SeleniumPageObject;
import com.vanguard.web.selenium.exceptions.SeleniumNavigationException;

/**
 * This class should be extended for only the few Page Objects that will ever start a test.
 * These should only include the starting pages of your application.
 * All other Page Objects should extend from SeleniumBasePage.
 *
 */
public abstract class SeleniumStartPage extends SeleniumPageObject{
	
	public SeleniumStartPage(WebDriver driver) {
		super(driver);
	}

	public abstract String getPageUrl();
	
	public void navigateToPage() {
		browser.visit(getPageUrl());
		if(!isLoaded()) {
			throw new SeleniumNavigationException("Page " + this.getClass().getSimpleName() + 
					" was determined to not be loaded after navigating to " + getPageUrl(), driver);
		}
	}
	
}
