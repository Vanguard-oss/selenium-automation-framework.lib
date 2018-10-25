package com.vanguard.web.selenium.startingpages;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.automation.tools.selenium.SeleniumPageObject;
import com.vanguard.web.selenium.exceptions.SeleniumNavigationException;

/**
 * Nearly all project level Page Objects should extend this object.
 * This gives access to the actions, finder, and browser objects in protected fashion.
 * This object will verify the page is currently loaded in the browser upon instantiation.
 * 		If the browser is not currently on the page after the isLoaded() call, 
 * 		then we throw an error and stop the test.
 * The only Pages that should not extend this object are the starting pages of your application.
 *
 */
public abstract class SeleniumBasePage extends SeleniumPageObject{
	
	public SeleniumBasePage(WebDriver driver) {
		super(driver);
		if(!isLoaded()) {
			throw new SeleniumNavigationException("Page " + this.getClass().getSimpleName() + 
					" was not loaded in the browser when trying to construct the object.", driver);
		}
	}

}
