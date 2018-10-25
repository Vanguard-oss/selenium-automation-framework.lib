package com.vanguard.web.automation.tools.selenium;

import org.openqa.selenium.WebDriver;

/**
 * All Page Objects across the entire test suite should extend this class (or some class that extends this class).
 * 
 */
public abstract class SeleniumPageObject {

	protected WebDriver driver;
	protected SeleniumElementFinder finder;
	protected SeleniumActionMethods actions;
	protected SeleniumBrowserMethods browser;
	protected TableUtilities tableHelper;
	
	public SeleniumPageObject(WebDriver driver) {
		super();
		this.driver = driver;
		this.finder = new SeleniumElementFinder(driver);
		this.actions = new SeleniumActionMethods(driver, finder, this.getClass().getSimpleName());
		this.browser = new SeleniumBrowserMethods(driver);
		this.tableHelper = new TableUtilities(driver, finder, actions);
	}

	public abstract boolean isLoaded();
}
