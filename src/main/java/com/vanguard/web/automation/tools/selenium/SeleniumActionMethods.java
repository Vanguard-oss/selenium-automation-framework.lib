package com.vanguard.web.automation.tools.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

/**
 * Wraps all Selenium action methods like click, isDisplayed, etc.
 * Adds error handling around those methods for users.
 * Added additional functionality like highlighting elements (useful for debugging tests).
 * 
 */
public class SeleniumActionMethods {
	
	private static final String JS_HIGHLIGHTED_STYLE = "border: 10px solid red; border-style: dashed;";
	private static final String JS_SET_ATTRIBUTE_COMMAND = "arguments[0].setAttribute(arguments[1], arguments[2])";
	private static final String STYLE = "style";
	private static final String ELEMENT_PASSED_INTO_METHOD = "element passed into method";
	private SeleniumElementFinder finder;
	private ErrorHandler errorHandler;
	private FluentWaitGenerator fluentWaitGenerator;
	private WebDriver driver;
	private String pageName;
	
	public SeleniumActionMethods(WebDriver driver, SeleniumElementFinder finder, String pageName) {
		super();
		this.finder = finder;
		this.driver = driver;
		this.pageName = pageName;
		this.errorHandler = new ErrorHandler();
		this.fluentWaitGenerator = new FluentWaitGenerator();
	}
	//For Tests
	protected void setErrorHandler(ErrorHandler newErrorHandler) {
		this.errorHandler = newErrorHandler;
	}
	protected void setFluentWaitGenerator(FluentWaitGenerator newFluentWaitGenerator) {
		this.fluentWaitGenerator = newFluentWaitGenerator;
	}
	protected String getPageName() {
		return pageName;
	}
	
	
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not displayed (no error will be thrown).
	 * Returns true if the element is found and is displayed.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isDisplayed(final By locator) {
		return isDisplayed(locator, 0);
	}
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not displayed (no error will be thrown).
	 * Returns true if the element is found and is displayed.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isDisplayed(final WebElement element) {
		boolean returnVal = false;
		if(element!=null){
			returnVal = element.isDisplayed();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
		return returnVal;
	}

	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not displayed within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is displayed.
	 * 
	 * @param locator
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isDisplayed(final By locator, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isDisplayedFlag;
		try {
			isDisplayedFlag = wait.until(getExpectedConditionForIsAtLeastOneElementDisplayed(locator));
		} catch (TimeoutException e) {
			isDisplayedFlag = false;
		}
		return isDisplayedFlag;
	}

	protected Function<WebDriver, Boolean> getExpectedConditionForIsAtLeastOneElementDisplayed(final By locator) {
		return getBooleanFunction(isAtLeastOneElementDisplayed(locator, driver));
	}
	private Function<WebDriver, Boolean> getBooleanFunction(boolean condition) {
	    return x -> condition;
	}
	protected Boolean isAtLeastOneElementDisplayed(final By locator, final WebDriver driver) {
		List<WebElement> allElementsFound = driver.findElements(locator);
		for (WebElement webElement : allElementsFound) {
			if(webElement.isDisplayed()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not displayed within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is displayed.
	 * 
	 * @param element
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isDisplayed(final WebElement element, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isDisplayedFlag;
		try {
			isDisplayedFlag = wait.until(driver -> element.isDisplayed());
		} catch (TimeoutException e) {
			isDisplayedFlag = false;
		}
		return isDisplayedFlag;
	}
	



	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not enabled (no error will be thrown).
	 * Returns true if the element is found and is enabled.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isEnabled(final By locator) {
		return isEnabled(locator, 0);
	}
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not enabled (no error will be thrown).
	 * Returns true if the element is found and is enabled.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isEnabled(final WebElement element) {
		boolean returnVal = false;
		if(element!=null){
			returnVal = element.isEnabled();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
		return returnVal;
	}

	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not enabled within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is enabled.
	 * 
	 * @param locator
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isEnabled(final By locator, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isEnabledFlag;
		try {
			isEnabledFlag = wait.until(getAtLeastOneElementIsEnabledFunction(locator));
		} catch (TimeoutException e) {
			isEnabledFlag = false;
		}
		return isEnabledFlag;
	}

	protected Function<WebDriver, Boolean> getAtLeastOneElementIsEnabledFunction(final By locator) {
		return getBooleanFunction(isAtLeastOneElementEnabled(locator, driver));
	}
	protected  Boolean isAtLeastOneElementEnabled(final By locator, final WebDriver driver) {
		List<WebElement> allElementsFound = driver.findElements(locator);
		for (WebElement webElement : allElementsFound) {
			if(webElement.isEnabled()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not enabled within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is enabled.
	 * 
	 * @param element
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isEnabled(final WebElement element, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isEnabledFlag;
		try {
			isEnabledFlag = wait.until(driver -> element.isEnabled());
		} catch (TimeoutException e) {
			isEnabledFlag = false;
		}
		return isEnabledFlag;
	}

	
	
	
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not selected (no error will be thrown).
	 * Returns true if the element is found and is selected.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isSelected(final By locator) {
		return isSelected(locator, 0);
	}
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not selected (no error will be thrown).
	 * Returns true if the element is found and is selected.
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isSelected(final WebElement element) {
		boolean returnVal = false;
		if(element!=null){
			returnVal = element.isSelected();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
		return returnVal;
	}

	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not selected within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is selected.
	 * 
	 * @param locator
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isSelected(final By locator, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isSelectedFlag;
		try {
			isSelectedFlag = wait.until(getAtLeastOneElementIsSelectedFunction(locator));
		} catch (TimeoutException e) {
			isSelectedFlag = false;
		}
		return isSelectedFlag;
	}
	protected Function<WebDriver, Boolean> getAtLeastOneElementIsSelectedFunction(final By locator) {
		return getBooleanFunction(isAtLeastOneElementSelected(locator, driver));
	}
	protected Boolean isAtLeastOneElementSelected(final By locator, final WebDriver driver) {
		List<WebElement> allElementsFound = driver.findElements(locator);
		for (WebElement webElement : allElementsFound) {
			if(webElement.isSelected()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Unlike the Selenium API, this method will return false if the element is not found or is not selected within the maxTimeInSec (no error will be thrown).
	 * Returns true if the element is found and is selected.
	 * 
	 * @param element
	 * @param maxTimeInSec - Waits up-to this much time.  Continues the test as soon as condition is true.
	 * @return
	 */
	public boolean isSelected(final WebElement element, int maxTimeInSec) {
		Wait<WebDriver> wait = fluentWaitGenerator.getFluentWait(driver, maxTimeInSec);
		boolean isSelectedFlag;
		try {
			isSelectedFlag = wait.until(driver -> element.isSelected());
		} catch (TimeoutException e) {
			isSelectedFlag = false;
		}
		return isSelectedFlag;
	}

	
	/**
	 * 
	 * @param text - Types this into the element found from the locator.
	 * @param locator
	 */
	public void type(final String text, final By locator) {
		type(text, locator, 0);
	}
	/**
	 * 
	 * @param text - Types this into the element.
	 * @param locator
	 */
	public void type(final String text, final WebElement element) {
		if(element!=null){
			element.sendKeys(text);
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
	}

	/**
	 * 
	 * @param text - Types this into the element found from the locator.
	 * @param locator - Looks for the element for up to maxTimeInSec.
	 * @param maxTimeInSec
	 */
	public void type(final String text, final By locator, final int maxTimeInSec) {
		WebElement element = finder.getElement(locator, maxTimeInSec);
		if(element!=null){
			element.sendKeys(text);
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
		}
	}

	/**
	 * Clears the input WebElement that's found from the locator.
	 * @param locator
	 */
	public void clear(final By locator) {
		clear(locator, 0);
	}
	/**
	 * Clears the input WebElement.
	 * @param element
	 */
	public void clear(final WebElement element) {
		if(element!=null){
			element.clear();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
	}

	/**
	 * Clears the input WebElement that's found from the locator.
	 * @param locator - Looks for the element for up to maxTimeInSec.
	 * @param maxTimeInSec
	 */
	public void clear(final By locator, final int maxTimeInSec) {
		WebElement element = finder.getElement(locator, maxTimeInSec);
		if(element!=null){
			element.clear();
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
		}
	}

	/**
	 * Clicks the element found by the locator.
	 * @param locator
	 */
	public void click(final By locator) {
		click(locator, 0);
	}
	/**
	 * Clicks the element
	 * @param element
	 */
	public void click(WebElement element) {
		if(element!=null){
			element.click();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
	}

	/**
	 * Clicks the element found by the locator.
	 * @param locator - Looks for the element for up to maxTimeInSec.
	 * @param maxTimeInSec
	 */
	public void click(By locator, int maxTimeInSec) {
		WebElement element = finder.getElement(locator, maxTimeInSec);
		if(element!=null){
			element.click();
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
		}
	}



	/** Returns a single String that matches the contents of the FIRST
	 * element found by the locator. 
	 * @param locator - the locator expected to have only 1 match.  If 
	 * the locator matches to multiple elements, this will return the text 
	 * of the first element it matches only.  If you want all element texts
	 * of all the elements matching this locator use getAllTexts() method.
	 * @return a single string of the contents of the first matched element.
	 */	
	public String getText(final By locator) {
		WebElement element = finder.getElement(locator);
		if(element!=null){
			return element.getText();
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
			return "";
		}
	}
	public String getText(final WebElement element) {
		if(element!=null){
			return element.getText();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
			return "";
		}
	}	

	/** Returns a list of all element texts that are found from searching the locator
	 * This method differers from getText() in that it expects the locator to have 
	 * multiple matches.
	 * 
	 * @param locator - the locator expected to have multiple matches. ie: By.xpath("//div[contains('@id', 'partialIDWithManyMatches')]")
	 * @return a list of strings for the contents of each of the matches.
	 */
	public List<String> getAllTexts(final By locator) {
		List<WebElement> allElements = finder.getElements(locator);
		List<String> allTexts = new ArrayList<String>();
		for (WebElement element : allElements) {
			allTexts.add(getText(element));
		}
		return allTexts;
	}

	public String getAttribute(final By locator, final String attributeName) {
		WebElement element = finder.getElement(locator);
		if(element!=null){
			return element.getAttribute(attributeName);
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
			return "";
		}
	}

	public void moveCursorToElement(final By locator) {
		WebElement element = finder.getElement(locator);
		if(element!=null){
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
		}
	}
	public void moveCursorToElement(final WebElement element) {
		if(element!=null){
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
	}
	
	
	public void scrollDownPercentageOfPageHeight(double percentOfPageHeight) {
		int height = driver.manage().window().getSize().getHeight();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, " + height*percentOfPageHeight + ");");
	}
	public void scrollUpPercentageOfPageHeight(double percentOfPageHeight) {
		int height = driver.manage().window().getSize().getHeight();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, -" + height*percentOfPageHeight + ");");
	}
	public void scrollRightPercentageOfPageWidth(double percentOfPageWidth) {
		int width = driver.manage().window().getSize().getWidth();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(" + width*percentOfPageWidth + ", 0);");
	}
	public void scrollLeftPercentageOfPageWidth(double percentOfPageWidth) {
		int width = driver.manage().window().getSize().getWidth();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(-" + width*percentOfPageWidth+ ", 0);");
	}

	/**
	 * Typically used for debugging test cases.
	 * Will highlight the element found from the locator.
	 * @param locator
	 * @param durationInSecToHighlight - Time in seconds to pause and highlight the element before continuing the test case.
	 */
	public void highlightElement(final By locator, final double durationInSecToHighlight) {
		if(finder.getElement(locator) != null) {
			highlightElement(finder.getElement(locator), durationInSecToHighlight);
		} else {
			errorHandler.throwOrLogError(driver, locator.toString(), getActionString(), pageName);
		}
	}
	/**
	 * Typically used for debugging test cases.
	 * Will highlight the element.
	 * @param element
	 * @param durationInSecToHighlight - Time in seconds to pause and highlight the element before continuing the test case.
	 */
	public void highlightElement(final WebElement element, final double durationInSecToHighlight){
		if(element!=null){
			String original_style = element.getAttribute(STYLE);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					JS_SET_ATTRIBUTE_COMMAND,
					element,
					STYLE,
					JS_HIGHLIGHTED_STYLE);
			if (durationInSecToHighlight > 0) {
				try {
					Thread.sleep((long) (durationInSecToHighlight * 1000));
				} catch (InterruptedException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Can not sleep correctly when highlighting an element.  Error details:");
					Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
				}
				js.executeScript(
						JS_SET_ATTRIBUTE_COMMAND,
						element,
						STYLE,
						original_style);
			}
		} else {
			errorHandler.throwOrLogError(driver, ELEMENT_PASSED_INTO_METHOD, getActionString(), pageName);
		}
	}
	
	/**
	 * Used by the framework only.  Used to print more clear error messages.
	 * @return
	 */
	protected String getActionString() {
		return errorHandler.getMethodNameFromStackTrace(ErrorHandler.stacktraceDepthOffset + 1);
	}
}
