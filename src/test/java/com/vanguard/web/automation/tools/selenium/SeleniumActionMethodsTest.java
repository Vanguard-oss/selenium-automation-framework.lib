package com.vanguard.web.automation.tools.selenium;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.base.Function;

@PrepareForTest({SeleniumActionMethods.class})
@RunWith(PowerMockRunner.class)
public class SeleniumActionMethodsTest {
	
	WebDriver mockDriver;
	By mockLocator;
	WebElement mockElement;
	SeleniumActionMethods actions;
	SeleniumElementFinder mockFinder;
	ErrorHandler mockErrorHandler;
	FluentWaitGenerator mockFluentWaitGenerator;
	Actions mockSeleniumActions;
	
	@Before
	public void SeleniumBrowserMethodsSetup() {
		mockLocator = Mockito.mock(By.class);
		mockElement = Mockito.mock(WebElement.class);
		mockDriver = Mockito.mock(WebDriver.class);
		mockFinder = Mockito.mock(SeleniumElementFinder.class);
		mockErrorHandler = Mockito.mock(ErrorHandler.class);
		mockFluentWaitGenerator = Mockito.mock(FluentWaitGenerator.class);
		mockSeleniumActions = Mockito.mock(Actions.class);
		actions = new SeleniumActionMethods(mockDriver, mockFinder, "AnyPageName");
		actions.setErrorHandler(mockErrorHandler);
		actions.setFluentWaitGenerator(mockFluentWaitGenerator);
	}

	@After
	public void validate() {
	    Mockito.validateMockitoUsage();
	}
	
	//TODO: Use EasyMock for nearly all these tests instead of Mockito, just so we can get Jacoco coverage, these tests work as is for now.
	
	@Test
	public void isDisplayedFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		Mockito.when(mockElement.isDisplayed()).thenReturn(true);
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.isDisplayed(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void isDisplayedFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumIsDisplayedIfElementIsNOTNullTest() {
		WebElement mockElement = EasyMock.createMock(WebElement.class);
		EasyMock.expect(mockElement.isDisplayed()).andReturn(true);
		EasyMock.replay(mockElement);
		actions.isDisplayed(mockElement);
		EasyMock.verify(mockElement);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void isDisplayedWillReturnFalseIfWeTimeoutLookingForTheElementTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenThrow(new TimeoutException());
		
		boolean expected = false;
		boolean actual = actions.isDisplayed(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void isDisplayedWillReturnTrueIfWeMockTheFluentWaitToReturnTrueTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isDisplayed(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}

	@Test
	public void isAtLeastOneElementDisplayedReturnsTrueIfThereIsAnElementThatIsDisplayedInTheListTest() {
		WebElement elem1IsNOTDisplayed = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTDisplayed = Mockito.mock(WebElement.class);
		WebElement elem3IsDisplayed = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTDisplayed);
		allElements.add(elem2IsNOTDisplayed);
		allElements.add(elem3IsDisplayed);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTDisplayed.isDisplayed()).thenReturn(false);
		Mockito.when(elem2IsNOTDisplayed.isDisplayed()).thenReturn(false);
		Mockito.when(elem3IsDisplayed.isDisplayed()).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isAtLeastOneElementDisplayed(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTDisplayed, Mockito.times(1)).isDisplayed();
		Mockito.verify(elem2IsNOTDisplayed, Mockito.times(1)).isDisplayed();
		Mockito.verify(elem3IsDisplayed, Mockito.times(1)).isDisplayed();
	}
	@Test
	public void isAtLeastOneElementDisplayedReturnsFalseIfThereAreNoElementsThatAreDisplayedInTheListTest() {
		WebElement elem1IsNOTDisplayed = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTDisplayed = Mockito.mock(WebElement.class);
		WebElement elem3IsNOTDisplayed = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTDisplayed);
		allElements.add(elem2IsNOTDisplayed);
		allElements.add(elem3IsNOTDisplayed);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTDisplayed.isDisplayed()).thenReturn(false);
		Mockito.when(elem2IsNOTDisplayed.isDisplayed()).thenReturn(false);
		Mockito.when(elem3IsNOTDisplayed.isDisplayed()).thenReturn(false);
		
		boolean expected = false;
		boolean actual = actions.isAtLeastOneElementDisplayed(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTDisplayed, Mockito.times(1)).isDisplayed();
		Mockito.verify(elem2IsNOTDisplayed, Mockito.times(1)).isDisplayed();
		Mockito.verify(elem3IsNOTDisplayed, Mockito.times(1)).isDisplayed();
	}
	
	
	@Test
	public void isEnabledFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		Mockito.when(mockElement.isEnabled()).thenReturn(true);
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.isEnabled(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void isEnabledFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumIsDisplayedIfElementIsNOTNullTest() {
		Mockito.when(mockElement.isEnabled()).thenReturn(true);
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.isEnabled(mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).isEnabled();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void isEnabledWillReturnFalseIfWeTimeoutLookingForTheElementTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenThrow(new TimeoutException());
		
		boolean expected = false;
		boolean actual = actions.isEnabled(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void isEnabledWillReturnTrueIfWeMockTheFluentWaitToReturnTrueTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isEnabled(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}

	@Test
	public void isAtLeastOneElementEnabledReturnsTrueIfThereIsAnElementThatIsEnabledInTheListTest() {
		WebElement elem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem3IsEnabled = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTEnabled);
		allElements.add(elem2IsNOTEnabled);
		allElements.add(elem3IsEnabled);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem3IsEnabled.isEnabled()).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isAtLeastOneElementEnabled(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem3IsEnabled, Mockito.times(1)).isEnabled();
	}
	@Test
	public void isAtLeastOneElementEnabledReturnsFalseIfThereAreNoElementsThatAreEnabledInTheListTest() {
		WebElement elem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem3IsNOTEnabled = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTEnabled);
		allElements.add(elem2IsNOTEnabled);
		allElements.add(elem3IsNOTEnabled);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem3IsNOTEnabled.isEnabled()).thenReturn(false);
		
		boolean expected = false;
		boolean actual = actions.isAtLeastOneElementEnabled(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem3IsNOTEnabled, Mockito.times(1)).isEnabled();
	}


	@Test
	public void isSelectedFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		Mockito.when(mockElement.isSelected()).thenReturn(true);
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.isSelected(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void isSelectedFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumIsDisplayedIfElementIsNOTNullTest() {
		Mockito.when(mockElement.isSelected()).thenReturn(true);
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.isSelected(mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).isSelected();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void isSelectedWillReturnFalseIfWeTimeoutLookingForTheElementTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenThrow(new TimeoutException());
		
		boolean expected = false;
		boolean actual = actions.isSelected(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void isSelectedWillReturnTrueIfWeMockTheFluentWaitToReturnTrueTest() throws Exception {
		FluentWait<WebDriver> mockWait = Mockito.mock(FluentWait.class);
		Mockito.when(mockFluentWaitGenerator.getFluentWait(Mockito.any(WebDriver.class), Mockito.anyInt())).thenReturn(mockWait);
		Mockito.when(mockWait.until(Mockito.any(Function.class))).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isSelected(mockLocator);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockWait, Mockito.times(1)).until(Mockito.any(Function.class));
	}

	@Test
	public void isAtLeastOneElementSelectedReturnsTrueIfThereIsAnElementThatIsSelectedInTheListTest() {
		WebElement elem1IsNOTSelected = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTSelected = Mockito.mock(WebElement.class);
		WebElement elem3IsSelected = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTSelected);
		allElements.add(elem2IsNOTSelected);
		allElements.add(elem3IsSelected);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTSelected.isSelected()).thenReturn(false);
		Mockito.when(elem2IsNOTSelected.isSelected()).thenReturn(false);
		Mockito.when(elem3IsSelected.isSelected()).thenReturn(true);
		
		boolean expected = true;
		boolean actual = actions.isAtLeastOneElementSelected(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTSelected, Mockito.times(1)).isSelected();
		Mockito.verify(elem2IsNOTSelected, Mockito.times(1)).isSelected();
		Mockito.verify(elem3IsSelected, Mockito.times(1)).isSelected();
	}
	@Test
	public void isAtLeastOneElementSelectedReturnsFalseIfThereAreNoElementsThatAreSelectedInTheListTest() {
		WebElement elem1IsNOTSelected = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTSelected = Mockito.mock(WebElement.class);
		WebElement elem3IsNOTSelected = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1IsNOTSelected);
		allElements.add(elem2IsNOTSelected);
		allElements.add(elem3IsNOTSelected);
		
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1IsNOTSelected.isSelected()).thenReturn(false);
		Mockito.when(elem2IsNOTSelected.isSelected()).thenReturn(false);
		Mockito.when(elem3IsNOTSelected.isSelected()).thenReturn(false);
		
		boolean expected = false;
		boolean actual = actions.isAtLeastOneElementSelected(mockLocator, mockDriver);
		Assert.assertEquals(expected, actual);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(elem1IsNOTSelected, Mockito.times(1)).isSelected();
		Mockito.verify(elem2IsNOTSelected, Mockito.times(1)).isSelected();
		Mockito.verify(elem3IsNOTSelected, Mockito.times(1)).isSelected();
	}

	
	@Test
	public void typeFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.type("anyString", mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void typeFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumSendKeysIfElementIsNOTNullTest() {
		Mockito.doNothing().when(mockElement).sendKeys(Mockito.anyString());
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.type("anyString", mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).sendKeys(Mockito.anyString());
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void typeFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.type("anyString", mockLocator);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void typeFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumSendKeysIfElementIsNOTNullTest() {
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(mockElement);
		Mockito.doNothing().when(mockElement).sendKeys(Mockito.anyString());
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.type("anyString", mockLocator, 0);
		Mockito.verify(mockFinder, Mockito.times(1)).getElement(mockLocator, 0);
		Mockito.verify(mockElement, Mockito.times(1)).sendKeys(Mockito.anyString());
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}


	@Test
	public void clearFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.clear(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clearFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumClearIfElementIsNOTNullTest() {
		Mockito.doNothing().when(mockElement).clear();
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.clear(mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).clear();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clearFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.clear(mockLocator);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clearFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumClearIfElementIsNOTNullTest() {
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(mockElement);
		Mockito.doNothing().when(mockElement).clear();
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.clear(mockLocator, 0);
		Mockito.verify(mockFinder, Mockito.times(1)).getElement(mockLocator, 0);
		Mockito.verify(mockElement, Mockito.times(1)).clear();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}


	@Test
	public void clickFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.click(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clickFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumClickIfElementIsNOTNullTest() {
		Mockito.doNothing().when(mockElement).click();
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.click(mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).click();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clickFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.click(mockLocator);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void clickFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumClickIfElementIsNOTNullTest() {
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(mockElement);
		Mockito.doNothing().when(mockElement).click();
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.click(mockLocator, 0);
		Mockito.verify(mockFinder, Mockito.times(1)).getElement(mockLocator, 0);
		Mockito.verify(mockElement, Mockito.times(1)).click();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}


	@Test
	public void getTextFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getText(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void getTextFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumGetTextIfElementIsNOTNullTest() {
		Mockito.when(mockElement.getText()).thenReturn("anyTextValue");
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getText(mockElement);
		Mockito.verify(mockElement, Mockito.times(1)).getText();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void getTextFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getText(mockLocator);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void getTextFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumGetTextIfElementIsNOTNullTest() {
		Mockito.when(mockFinder.getElement(mockLocator)).thenReturn(mockElement);
		Mockito.when(mockElement.getText()).thenReturn("anyTextValue");
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getText(mockLocator);
		Mockito.verify(mockFinder, Mockito.times(1)).getElement(mockLocator);
		Mockito.verify(mockElement, Mockito.times(1)).getText();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}

	
	@Test
	public void getAllTextsFromLocatorShouldReturnEmptyListWhenNoMatchesAreFoundTest() {
		Mockito.when(mockFinder.getElements(mockLocator)).thenReturn(new ArrayList<WebElement>());
		
		List<String> actualTextList = actions.getAllTexts(mockLocator);
		Assert.assertEquals(0, actualTextList.size());
		Mockito.verify(mockFinder, Mockito.times(1)).getElements(mockLocator);
	}
	@Test
	public void getAllTextsFromLocatorShouldReturnAListOfTextsWhenLocatorHasMultipleMatchesTest() {
		WebElement elem1 = Mockito.mock(WebElement.class);
		WebElement elem2 = Mockito.mock(WebElement.class);
		WebElement elem3 = Mockito.mock(WebElement.class);
		List<WebElement> allElements = new ArrayList<WebElement>();
		allElements.add(elem1);
		allElements.add(elem2);
		allElements.add(elem3);
		String elemTextValue1 = "anyTextValue1";
		String elemTextValue2 = "anyTextValue2";
		String elemTextValue3 = "anyTextValue3";
		
		Mockito.when(mockFinder.getElements(mockLocator)).thenReturn(allElements);
		Mockito.when(elem1.getText()).thenReturn(elemTextValue1);
		Mockito.when(elem2.getText()).thenReturn(elemTextValue2);
		Mockito.when(elem3.getText()).thenReturn(elemTextValue3);
		
		List<String> expectedAllTextsList = new ArrayList<String>();
		expectedAllTextsList.add(elemTextValue1);
		expectedAllTextsList.add(elemTextValue2);
		expectedAllTextsList.add(elemTextValue3);
		List<String> actualAllTextsList = actions.getAllTexts(mockLocator);
		
		Assert.assertEquals(expectedAllTextsList, actualAllTextsList);
		Mockito.verify(mockFinder, Mockito.times(1)).getElements(mockLocator);
		Mockito.verify(elem1, Mockito.times(1)).getText();
		Mockito.verify(elem2, Mockito.times(1)).getText();
		Mockito.verify(elem3, Mockito.times(1)).getText();
	}


	@Test
	public void getAttributeFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getAttribute(mockLocator, "anyAttributeName");
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void getAttributeFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumGetAttributeIfElementIsNOTNullTest() {
		String attributeName = "anyAttributeName"; 
		Mockito.when(mockFinder.getElement(mockLocator)).thenReturn(mockElement);
		Mockito.when(mockElement.getAttribute(attributeName)).thenReturn("anyValue");
		Mockito.doNothing().when(mockErrorHandler).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.getAttribute(mockLocator, attributeName);
		Mockito.verify(mockFinder, Mockito.times(1)).getElement(mockLocator);
		Mockito.verify(mockElement, Mockito.times(1)).getAttribute(attributeName);
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}


	@Test
	public void moveCursorToElementFromElementShouldCallErrorHandlerWarningIfElementIsNullTest() {
		mockElement = null;
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn("mockMethodNameFromStack");
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.moveCursorToElement(mockElement);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void moveCursorToElementFromElementShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumActionIfElementIsNOTNullTest() throws Exception {
		PowerMockito.whenNew(Actions.class).withAnyArguments().thenReturn(mockSeleniumActions);
		Actions mockMoveToElementActions = Mockito.mock(Actions.class);
		Action mockBuildAction = Mockito.mock(Action.class);
		Mockito.when(mockSeleniumActions.moveToElement(mockElement)).thenReturn(mockMoveToElementActions);
		Mockito.when(mockMoveToElementActions.build()).thenReturn(mockBuildAction);
		Mockito.doNothing().when(mockBuildAction).perform();
		
		actions.moveCursorToElement(mockElement);
		Mockito.verify(mockSeleniumActions, Mockito.times(1)).moveToElement(mockElement);
		Mockito.verify(mockMoveToElementActions, Mockito.times(1)).build();
		Mockito.verify(mockBuildAction, Mockito.times(1)).perform();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void moveCursorToElementFromLocatorShouldCallErrorHandlerWarningIfElementIsNullTest() {
		String mockMethodNameFromStack = "mockMethodNameFromStack";
		Mockito.when(mockFinder.getElement(mockLocator, 0)).thenReturn(null);
		Mockito.when(mockErrorHandler.getMethodNameFromStackTrace(Mockito.anyInt())).thenReturn(mockMethodNameFromStack);
		Mockito.doNothing().when(mockErrorHandler).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		actions.moveCursorToElement(mockLocator);
		Mockito.verify(mockErrorHandler, Mockito.times(1)).getMethodNameFromStackTrace(Mockito.anyInt());
		Mockito.verify(mockErrorHandler, Mockito.times(1)).throwOrLogError(Mockito.any(WebDriver.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	@Test
	public void moveCursorToElementFromLocatorShouldNOTCallErrorHandlerWarningAndShouldCallSeleniumActionIfElementIsNOTNullTest() throws Exception {
		PowerMockito.whenNew(Actions.class).withAnyArguments().thenReturn(mockSeleniumActions);
		Actions mockMoveToElementActions = Mockito.mock(Actions.class);
		Action mockBuildAction = Mockito.mock(Action.class);
		Mockito.when(mockSeleniumActions.moveToElement(mockElement)).thenReturn(mockMoveToElementActions);
		Mockito.when(mockMoveToElementActions.build()).thenReturn(mockBuildAction);
		Mockito.doNothing().when(mockBuildAction).perform();
		
		actions.moveCursorToElement(mockElement);
		Mockito.verify(mockSeleniumActions, Mockito.times(1)).moveToElement(mockElement);
		Mockito.verify(mockMoveToElementActions, Mockito.times(1)).build();
		Mockito.verify(mockBuildAction, Mockito.times(1)).perform();
		Mockito.verify(mockErrorHandler, Mockito.times(0)).logNullElementWarning(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	
}
