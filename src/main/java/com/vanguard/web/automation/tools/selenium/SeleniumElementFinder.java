package com.vanguard.web.automation.tools.selenium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vanguard.web.selenium.properties.PropertiesManager;

/**
 * Contains all the ways you can find a WebElement within the framework.
 * All methods are overloaded to take a maxTimeInSec which will wait UP-TO that much time to find the element,
 * or you can remove that input and it will attempt to find the element (or fail) immediately.
 * 
 */
public class SeleniumElementFinder {

	protected static final String MAX_WAIT_SYSTEM_PROPERTY = "SELENIUM_MAX_WAIT";
	private static final int ABSOLUTE_MAX_WAIT_TIME_SECONDS = PropertiesManager.getFrameworkProperty(MAX_WAIT_SYSTEM_PROPERTY) == null ? 90 : 
		Integer.valueOf(PropertiesManager.getFrameworkProperties().getProperty(MAX_WAIT_SYSTEM_PROPERTY));

	private WebDriver driver;
	
	public SeleniumElementFinder(WebDriver driver) {
		super();
		this.driver = driver;
	}

	
	/**
	 * Get the WebElement without waiting any time for it to become enabled
	 * @param locator
	 * @return The element found by the locator if it was found and is clickable currently on the page.  
	 * If no such element exists, null is returned.
	 */
	public WebElement getElement(final By locator) {
		return getElement(locator, 0);
	}

	/**
	 * Get the WebElement by waiting up-to the maxTimeInSeconds for the element to be both found and clickable.
	 * @param locator
	 * @param maxTimeInSec
	 * @return The element found by the locator if it was found and is clickable on the page within the maxTimeInSeconds to wait.
	 * If no such element exists, null is returned.
	 */
	public WebElement getElement(final By locator, int maxTimeInSec) {
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return  getElementIfClickableWithinWaitTime(locator, wait);
	}

	/**
	 * Get the WebElement without waiting any time for it to become present
	 * @param locator
	 * @return The element found by the locator if one is found anywhere in the DOM (may be clickable or not).  
	 * If no such element exists, null is returned.
	 */
	public WebElement getElementPresentInDom(final By locator) {
		return getElementPresentInDOM(locator, 0);
	}

	/**
	 * Get the WebElement by waiting up-to the maxTimeInSeconds for the element to be present.
	 * @param locator
	 * @param maxTimeInSec
	 * @return The element found by the locator if one is found within maxTimeInSec anywhere in the DOM (may be clickable or not).
	 * If no such element exists, null is returned.
	 */
	public WebElement getElementPresentInDOM(final By locator, int maxTimeInSec) {
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return getElementIfVisibleWithinWaitTime(locator, wait);
	}

	public List<WebElement> getElements(final By locator) {
		return getElements(locator, 0);
	}

	public List<WebElement> getElements(final By locator, int maxTimeInSec) {
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return getAllElementsIfEnabledWithinWaitTime(locator, wait);
	}

	public List<WebElement> getElementsPresentInDom(final By locator) {
		return getElementsPresentInDom(locator, 0);
	}

	public List<WebElement> getElementsPresentInDom(final By locator, int maxTimeInSec) {
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return getAllElementsIfVisisbleWithinWaitTime(locator, wait);
	}

	public WebElement getSubElement(By parentLocator, By subLocator) {
		WebElement parent = getElement(parentLocator);
		return getSubElement(parent, subLocator, 0);
	}
	public WebElement getSubElement(By parentLocator, By subLocator, int maxTimeInSec) {
		Date start = new Date();
		WebElement parent = getElement(parentLocator, maxTimeInSec);
		Date mid = new Date();
		return getSubElement(parent, subLocator, (int)(mid.getTime() - start.getTime()));
	}
	public WebElement getSubElement(WebElement parentElement, By subLocator) {
		return getSubElement(parentElement, subLocator, 0);
	}
	public WebElement getSubElement(WebElement parentElement, By subLocator, int maxTimeInSec) {
		if(parentElement == null){
			return null;
		}
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);		
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return getSubElementIfEnabledWithinWaitTime(parentElement, subLocator, wait);
	}
	
	public WebElement getSubElementPresentInDom(By parentLocator, By subLocator) {
		WebElement parent = getElementPresentInDOM(parentLocator, 0);
		return getSubElementPresentInDom(parent, subLocator, 0);
	}
	public WebElement getSubElementPresentInDom(By parentLocator, By subLocator, int maxTimeInSec) {
		Date start = new Date();
		WebElement parent = getElementPresentInDOM(parentLocator, maxTimeInSec);
		Date mid = new Date();
		return getSubElementPresentInDom(parent, subLocator, getSecondsLeft(start.getTime(), mid.getTime(), maxTimeInSec));
	}
	public WebElement getSubElementPresentInDom(WebElement parentElement, By subLocator) {
		return getSubElementPresentInDom(parentElement, subLocator, 0);
	}
	public WebElement getSubElementPresentInDom(WebElement parentElement, By subLocator, int maxTimeInSec) {
		if(parentElement == null){
			return null;
		}
		maxTimeInSec = getMaxWaitTimeWithValidationChecks(maxTimeInSec);	
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		WebDriverWait wait = new WebDriverWait(driver, maxTimeInSec);
		return getSubElementIfVisibleWithinWaitTime(parentElement, subLocator, wait);
	}

	/**
	 * Get all subElements that are currently clickable on the page.
	 * @param parentLocator
	 * @param subLocator
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentLocator does not match to any element, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElements(By parentLocator, By subLocator) {
		return getSubElements(parentLocator, subLocator, 0);
	}
	/**
	 * Get all subElements that are clickable within maxTimeInSec on the page.
	 * Note: We will wait for any 1 subElement to become clickable, and then get all that are enabled at that time.
	 * @param parentLocator
	 * @param subLocator
	 * @param maxTimeInSec
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentLocator does not match to any element, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElements(By parentLocator, By subLocator, int maxTimeInSec) {
		Date start = new Date();
		WebElement parentElement = getElement(parentLocator, maxTimeInSec);
		List<WebElement> enabledSubElements;
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		Date mid = new Date();
		//Look for any subElement for up to the wait time.  
		//Once at least 1 element is found and enabled, we'll look to get all that are at that time.
		if(getSubElement(parentElement, subLocator, getSecondsLeft(start.getTime(), mid.getTime(), maxTimeInSec)) == null){
			enabledSubElements = new ArrayList<WebElement>();
		} else {
			List<WebElement> allSubElements = parentElement.findElements(subLocator);
			enabledSubElements = getAllEnabledElementsFromList(allSubElements);
		}
		return enabledSubElements;
	}
	/**
	 * Get all subElements that are currently clickable on the page.
	 * @param parentElement
	 * @param subLocator
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentElement is null, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElements(WebElement parentElement, By subLocator) {
		return getSubElements(parentElement, subLocator, 0);
	}
	/**
	 * Get all subElements that are clickable within maxTimeInSec on the page.
	 * Note: We will wait for any 1 subElement to become clickable, and then get all that are enabled at that time.
	 * @param parentElement
	 * @param subLocator
	 * @param maxTimeInSec
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentElement is null, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElements(WebElement parentElement, By subLocator, int maxTimeInSec){
		List<WebElement> enabledSubElements;
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		//Look for any subElement for up to the wait time.  
		//Once at least 1 element is found and enabled, we'll look to get all that are at that time.
		if(getSubElement(parentElement, subLocator, maxTimeInSec) == null){
			enabledSubElements = new ArrayList<WebElement>();
		} else {
			List<WebElement> allSubElements = parentElement.findElements(subLocator);
			enabledSubElements = getAllEnabledElementsFromList(allSubElements);
		}
		return enabledSubElements;
	}

	
	/**
	 * Get all subElements that are currently present anywhere in the DOM (may be clickable or not).
	 * @param parentLocator
	 * @param subLocator
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentLocator does not match to any element, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElementsPresentInDom(By parentLocator, By subLocator) {
		return getSubElementsPresentInDom(parentLocator, subLocator, 0);
	}
	/**
	 * Get all subElements that are present anywhere in the DOM within maxTimeInSec (may be clickable or not).
	 * Note: We will wait for any 1 subElement to be found, and then get all that are found at that time.
	 * @param parentLocator
	 * @param subLocator
	 * @param maxTimeInSec
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentLocator does not match to any element, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElementsPresentInDom(By parentLocator, By subLocator, int maxTimeInSec) {
		Date start = new Date();
		WebElement parentElement = getElement(parentLocator, maxTimeInSec);
		List<WebElement> presentSubElements;
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		Date mid = new Date();
		//Look for any subElement for up to the wait time.  
		//Once at least 1 element is found, we'll look to get all that are at that time.
		if(getSubElementPresentInDom(parentElement, subLocator, getSecondsLeft(start.getTime(), mid.getTime(), maxTimeInSec)) == null){
			presentSubElements = new ArrayList<WebElement>();
		}
		presentSubElements = parentElement.findElements(subLocator);
		return presentSubElements;
	}
	/**
	 * Get all subElements that are currently present anywhere in the DOM (may be clickable or not).
	 * @param parentElement
	 * @param subLocator
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentElement is null, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElementsPresentInDom(WebElement parentElement, By subLocator) {
		return getSubElementsPresentInDom(parentElement, subLocator, 0);
	}
	/**
	 * Get all subElements that are currently present anywhere in the DOM within maxTimeInSec (may be clickable or not).
	 * Note: We will wait for any 1 subElement to be found, and then get all that are enabled at that time.
	 * @param parentElement
	 * @param subLocator
	 * @param maxTimeInSec
	 * @return a List of elements anywhere below the parent element in the DOM that match the subLocator.
	 * If the parentElement is null, or the sublocator does not match to any element, 
	 * than a "new ArrayList<WebElement>()" is returned.
	 */
	public List<WebElement> getSubElementsPresentInDom(WebElement parentElement, By subLocator, int maxTimeInSec) {
		List<WebElement> presentSubElements;
		subLocator = SeleniumHelperUtil.updateXPathIfNeededForSubElements(subLocator);
		//Look for any subElement for up to the wait time.  
		//Once at least 1 element is found, we'll look to get all that are at that time.
		if(getSubElementPresentInDom(parentElement, subLocator, maxTimeInSec) == null){
			presentSubElements = new ArrayList<WebElement>();
		}
		presentSubElements = parentElement.findElements(subLocator);
		return presentSubElements;
	}



	
	//NOTE: The following methods are helper methods for the framework only, and are NOT public.
	
	protected int getMaxWaitTimeWithValidationChecks(int maxTimeInSec) {
		if(maxTimeInSec < 0 || maxTimeInSec > ABSOLUTE_MAX_WAIT_TIME_SECONDS){
			maxTimeInSec = ABSOLUTE_MAX_WAIT_TIME_SECONDS; //can't be less than 0 or greater than Absolute Max set by System Properties
		}
		return maxTimeInSec;
	}

	protected WebElement getElementIfClickableWithinWaitTime(final By locator, WebDriverWait wait) {
		WebElement returnElement;
		try {
			//Wait until the element is visible and enabled such that you can click it.
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			returnElement = driver.findElement(locator);
		} catch (TimeoutException e) {
			//If we waited the entire maxTime and the element still wasn't visible and enabled, return null.
			returnElement = null;
		}
		return returnElement;
	}

	protected WebElement getElementIfVisibleWithinWaitTime(final By locator, WebDriverWait wait) {
		WebElement returnElement;
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			returnElement = driver.findElement(locator);
		} catch (TimeoutException e) {
			returnElement = null;
		}
		return returnElement;
	}

	protected List<WebElement> getAllElementsIfEnabledWithinWaitTime(final By locator, WebDriverWait wait) {
		List<WebElement> returnElements;
		try {
			//Wait until at least 1 element is enabled that matches the location
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			returnElements = getAllEnabledElementsFromList(driver.findElements(locator));
		} catch (TimeoutException e) {
			//If we waited the entire maxTime and the element still wasn't visible and enabled, return an empty list.
			returnElements = new ArrayList<WebElement>();
		} catch (WebDriverException wde) {
			returnElements = new ArrayList<WebElement>();
		}
		return returnElements;
	}

	protected List<WebElement> getAllElementsIfVisisbleWithinWaitTime(final By locator, WebDriverWait wait) {
		List<WebElement> returnElements;
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			returnElements = driver.findElements(locator);
		} catch (TimeoutException e) {
			//If we waited the entire maxTime and the element still wasn't visible and enabled, return null.
			returnElements = new ArrayList<WebElement>();
		} catch (WebDriverException wde) {
			returnElements = new ArrayList<WebElement>();
		}
		return returnElements;
	}

	protected WebElement getSubElementIfEnabledWithinWaitTime(WebElement parentElement, By subLocator, WebDriverWait wait) {
		WebElement subElement;
		try {
			//Wait until we detect a sub element nested within the parent
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, subLocator));
			subElement = getFirstEnabledElementFromList(parentElement.findElements(subLocator));
		} catch (TimeoutException e) {
			//If we waited the entire maxTime and the element still wasn't visible and enabled, return null.
			subElement = null;
		}
		return subElement;
	}

	protected WebElement getSubElementIfVisibleWithinWaitTime(WebElement parentElement, By subLocator, WebDriverWait wait) {
		WebElement subElement;
		try {
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, subLocator));
			subElement = parentElement.findElement(subLocator);
		} catch (TimeoutException e) {
			subElement = null;
		}
		return subElement;
	}
	
	
	protected WebElement getFirstEnabledElementFromList(List<WebElement> elements) {
		WebElement firstEnabledElement = null;
		for (WebElement subElement : elements) {
			if(subElement.isEnabled()){
				firstEnabledElement = subElement;
				break;
			}
		}
		return firstEnabledElement;
	}
	protected List<WebElement> getAllEnabledElementsFromList(List<WebElement> elements) {
		List<WebElement> allEnabledElements = new ArrayList<WebElement>();
		for (WebElement element : elements) {
			if(element.isEnabled()){
				allEnabledElements.add(element);
			}
		}
		return allEnabledElements;
	}
	
	protected int getSecondsLeft(long startTimeInSeconds, long curTimeInSeconds, int maxTimeToTake) {
		int secondsTakenSoFar = (int)(curTimeInSeconds - startTimeInSeconds);
		int secondsLeft = maxTimeToTake - secondsTakenSoFar;
		return secondsLeft;
	}
	
	protected int getAbsoluteWaitTimeInSeconds() {
		return ABSOLUTE_MAX_WAIT_TIME_SECONDS;
	}
}
