package com.vanguard.web.automation.tools.selenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.vanguard.web.selenium.exceptions.NullWebDriverException;



/**
 * Contains all the methods that are browser specific.
 * Ex: navigating, resizing, getTitle, etc.
 * 
 */
public class SeleniumBrowserMethods {
	
	private WebDriver driver;
	
	public SeleniumBrowserMethods(WebDriver driver) {
		super();
		if(driver == null) {
			throw new NullWebDriverException();
		}
		this.driver = driver;
	}

	
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	public void visit(final String url) {
		driver.get(url);
	}
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}
	
	//TODO: Add rest of the browser options.  The currently implemented methods should give people 90+% of the functionality needed.

	@Deprecated
	/**
	 * maximize() is really a hack.  With modern media queries and responsive pages, you should really specify the exact size you want,
	 * rather than relying on the native size of the current environment.
	 * 
	 * If we use maximize() the test may behave differently between running on a phone, tablet, or PC.
	 */
	public void maximize() {
		driver.manage().window().maximize();
	}

	/**
	 * @param width
	 * @param height
	 * Example: width=1920, height=1080 = standard 1080p size of 1920x1080
	 */
	public void resize(int width, int height) {
		Dimension newDimension = new Dimension(width, height);
		driver.manage().window().setSize(newDimension);
	}
}
