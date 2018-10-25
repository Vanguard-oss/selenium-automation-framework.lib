package com.vanguard.web.automation.tools.selenium;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.properties.PropertiesManager;

@PrepareForTest({ExpectedConditions.class, SeleniumElementFinder.class})
@RunWith(PowerMockRunner.class)
public class SeleniumElementFinderTest {

	WebDriver mockDriver;
	SeleniumElementFinder finder;
	WebDriverWait mockWait;
	By mockLocator;
	WebElement mockElement;
	
	@Before
	public void SeleniumBrowserMethodsSetup() {
		mockLocator = Mockito.mock(By.class);
		mockElement = Mockito.mock(WebElement.class);
		mockWait = Mockito.mock(WebDriverWait.class);
		mockDriver = Mockito.mock(WebDriver.class);
		finder = new SeleniumElementFinder(mockDriver);
	}
	
	@After
	public void validate() {
	    Mockito.validateMockitoUsage();
	}
	
	//TODO: Use EasyMock for whichever of these tests we can instead of Mockito, just so we can get Jacoco coverage.
	//These tests work as is for now, and many require Powermock, but for those that don't it would be nice to see the coverage in Jacoco.
	
	@Test
	public void getAbsoluteWaitTimeInSecondsTest() {
		int maxWaitSystemPropertyValue = Integer.valueOf(PropertiesManager.getFrameworkProperty(SeleniumElementFinder.MAX_WAIT_SYSTEM_PROPERTY));
		Assert.assertNotNull(maxWaitSystemPropertyValue);
		Assert.assertEquals(maxWaitSystemPropertyValue, finder.getAbsoluteWaitTimeInSeconds());
	}
	
	@Test
	public void getElementNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);

		WebElement expectedElement = finder.getElement(mockLocator);
		WebElement actualElement = finder.getElement(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockLocator);
		Assert.assertEquals(expectedElement, actualElement);
	}
	@Test
	public void getElementTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);

		WebElement expectedElement = mockElement;
		WebElement actualElement = finder.getElement(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockLocator);
		Assert.assertEquals(expectedElement, actualElement);
	}

	@Test
	public void getElementPresentInDOMNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfElementLocated(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockLocator)).thenReturn(expectedConditionExpression);	
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);

		WebElement expectedElement = finder.getElementPresentInDom(mockLocator);
		WebElement actualElement = finder.getElementPresentInDOM(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockLocator);
		Assert.assertEquals(expectedElement, actualElement);
	}

	@Test
	public void getElementPresentInDOMTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfElementLocated(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);
		
		WebElement expectedElement = mockElement;
		WebElement actualElement = finder.getElementPresentInDOM(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockLocator);
		Assert.assertEquals(expectedElement, actualElement);
	}

	
	@Test
	public void getElementsNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		WebElement subElem1IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		int numOfTimesCallingRealMethod = 2;
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsEnabled);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		List<WebElement> expectedElementsList = finder.getElements(mockLocator);
		List<WebElement> actualElementsList = finder.getElements(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockLocator);
		Mockito.verify(subElem1IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedElementsList, actualElementsList);
	}
	@Test
	public void getElementsTest() throws Exception {
		WebElement subElem1IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		int numOfTimesCallingRealMethod = 1;
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsEnabled);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		ArrayList<WebElement> expectedElementList = new ArrayList<WebElement>();
		expectedElementList.add(subElem1IsEnabled);
		expectedElementList.add(subElem3IsEnabled);
		List<WebElement> actualElementsList = finder.getElements(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockLocator);
		Mockito.verify(subElem1IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedElementList, actualElementsList);
	}

	@Test
	public void getElementsPresentInDOMNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		WebElement elem1 = Mockito.mock(WebElement.class);
		WebElement elem2 = Mockito.mock(WebElement.class);
		WebElement elem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(elem1);
		allMockSubElements.add(elem2);
		allMockSubElements.add(elem3);
		int numOfTimesCallingRealMethod = 2;
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<List<WebElement>> expectedConditionExpression = ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(allMockSubElements);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedElementsList = finder.getElementsPresentInDom(mockLocator);
		List<WebElement> actualElementsList = finder.getElementsPresentInDom(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockLocator);
		Assert.assertEquals(expectedElementsList, actualElementsList);
	}
	@Test
	public void getElementsPresentInDOMTest() throws Exception {
		WebElement elem1 = Mockito.mock(WebElement.class);
		WebElement elem2 = Mockito.mock(WebElement.class);
		WebElement elem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(elem1);
		allMockSubElements.add(elem2);
		allMockSubElements.add(elem3);
		int numOfTimesCallingRealMethod = 1;
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<List<WebElement>> expectedConditionExpression = ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(allMockSubElements);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedElementList = allMockSubElements;
		List<WebElement> actualElementsList = finder.getElementsPresentInDom(mockLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockLocator);
		Assert.assertEquals(expectedElementList, actualElementsList);
	}
	
	@Test
	public void getSubElementFromParentByNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedSubElement = finder.getSubElement(mockParentBy, mockSubLocator);
		WebElement actualSubElement = finder.getSubElement(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}
	@Test
	public void getSubElementFromParentByTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedFirstEnabledSubElement = subElem3IsEnabled;
		WebElement actualSubElement = finder.getSubElement(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedFirstEnabledSubElement, actualSubElement);
	}

	@Test
	public void getSubElementFromParentElementNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedSubElement = finder.getSubElement(mockParentElement, mockSubLocator);
		WebElement actualSubElement = finder.getSubElement(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}
	@Test
	public void getSubElementFromParentElementTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedFirstEnabledSubElement = subElem3IsEnabled;
		WebElement actualSubElement = finder.getSubElement(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedFirstEnabledSubElement, actualSubElement);
	}

	@Test
	public void getSubElementPresentInDOMFromParentByNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> parentExpectedConditionExpr = ExpectedConditions.presenceOfElementLocated(mockParentBy);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockParentBy)).thenReturn(parentExpectedConditionExpr);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(parentExpectedConditionExpr)).thenReturn(mockParentElement);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElement(mockSubLocator)).thenReturn(subElem1);

		WebElement expectedSubElement = finder.getSubElementPresentInDom(mockParentBy, mockSubLocator);
		WebElement actualSubElement = finder.getSubElementPresentInDom(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(parentExpectedConditionExpr);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockSubLocator);
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}
	@Test
	public void getSubElementPresentInDOMFromParentByTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> parentExpectedConditionExpr = ExpectedConditions.presenceOfElementLocated(mockParentBy);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockParentBy)).thenReturn(parentExpectedConditionExpr);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(parentExpectedConditionExpr)).thenReturn(mockParentElement);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElement(mockSubLocator)).thenReturn(subElem1);

		WebElement expectedSubElement = subElem1;
		WebElement actualSubElement = finder.getSubElementPresentInDom(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(parentExpectedConditionExpr);
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockSubLocator);
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}

	@Test
	public void getSubElementPresentInDOMFromParentElementNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElement(mockSubLocator)).thenReturn(subElem1);

		WebElement expectedSubElement = finder.getSubElementPresentInDom(mockParentElement, mockSubLocator);
		WebElement actualSubElement = finder.getSubElementPresentInDom(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockSubLocator);
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}
	@Test
	public void getSubElementPresentInDOMFromParentElementTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElement(mockSubLocator)).thenReturn(subElem1);

		WebElement expectedSubElement = subElem1;
		WebElement actualSubElement = finder.getSubElementPresentInDom(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockSubLocator);
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}
	@Test
	public void getSubElementPresentInDOMFromParentElementReturnsNullIfParentIsNullTest() throws Exception {
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = null;
		
		WebElement expectedSubElement = null;
		WebElement actualSubElement = finder.getSubElementPresentInDom(mockParentElement, mockSubLocator, 0);
		
		Assert.assertEquals(expectedSubElement, actualSubElement);
	}

	
	@Test
	public void getSubElementsFromParentByNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> parentExpectedConditionExpr = ExpectedConditions.elementToBeClickable(mockParentBy);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockParentBy)).thenReturn(parentExpectedConditionExpr);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(parentExpectedConditionExpr)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		List<WebElement> expectedSubElementList = finder.getSubElements(mockParentBy, mockSubLocator);
		List<WebElement> actualSubElementList = finder.getSubElements(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(parentExpectedConditionExpr);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		/*parentElement.findElements mock triggers once for finding all sub elements while potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to get all subElements that are enabled*/ 
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod * 2)).findElements(mockSubLocator);
		/*subElem1IsNOTEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem2IsEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem2IsEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem3IsEnabled.isEnabled() mock does NOT trigger for when it's searching for all sub elements because
		 * the algorithm has already found an enabled element in subElem2IsEnabled for this case.
		 * But it does trigger for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsFromParentByTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> parentExpectedConditionExpr = ExpectedConditions.elementToBeClickable(mockParentBy);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockParentBy)).thenReturn(parentExpectedConditionExpr);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(parentExpectedConditionExpr)).thenReturn(mockParentElement);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		ArrayList<WebElement> expectedSubElementList = new ArrayList<WebElement>();
		expectedSubElementList.add(subElem2IsEnabled);
		expectedSubElementList.add(subElem3IsEnabled);
		List<WebElement> actualSubElementList = finder.getSubElements(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(parentExpectedConditionExpr);
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		/*parentElement.findElements mock triggers once for finding all sub elements while potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to get all subElements that are enabled*/ 
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod * 2)).findElements(mockSubLocator);
		/*subElem1IsNOTEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem2IsEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem2IsEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem3IsEnabled.isEnabled() mock does NOT trigger for when it's searching for all sub elements because
		 * the algorithm has already found an enabled element in subElem2IsEnabled for this case.
		 * But it does trigger for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsFromParentElementNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		List<WebElement> expectedSubElementList = finder.getSubElements(mockParentElement, mockSubLocator);
		List<WebElement> actualSubElementList = finder.getSubElements(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		/*parentElement.findElements mock triggers once for finding all sub elements while potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to get all subElements that are enabled*/ 
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod * 2)).findElements(mockSubLocator);
		/*subElem1IsNOTEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem2IsEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem2IsEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem3IsEnabled.isEnabled() mock does NOT trigger for when it's searching for all sub elements because
		 * the algorithm has already found an enabled element in subElem2IsEnabled for this case.
		 * But it does trigger for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsFromParentElementTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		ArrayList<WebElement> expectedSubElementList = new ArrayList<WebElement>();
		expectedSubElementList.add(subElem2IsEnabled);
		expectedSubElementList.add(subElem3IsEnabled);
		List<WebElement> actualSubElementList = finder.getSubElements(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		/*parentElement.findElements mock triggers once for finding all sub elements while potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to get all subElements that are enabled*/ 
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod * 2)).findElements(mockSubLocator);
		/*subElem1IsNOTEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem2IsEnabled.isEnabled() mock triggers once for when it's searching for all sub elements and potentially waiting for maxWaitTime, 
		 * then again for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem2IsEnabled, Mockito.times(numOfTimesCallingRealMethod * 2)).isEnabled();
		/*subElem3IsEnabled.isEnabled() mock does NOT trigger for when it's searching for all sub elements because
		 * the algorithm has already found an enabled element in subElem2IsEnabled for this case.
		 * But it does trigger for each realMethod call to see if it's enabled and needs to be added to the final collection*/
		Mockito.verify(subElem3IsEnabled, Mockito.times(numOfTimesCallingRealMethod)).isEnabled();
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}

	
	@Test
	public void getSubElementsPresentInDOMFromParentByNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		WebElement subElem2 = Mockito.mock(WebElement.class);
		WebElement subElem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1);
		allMockSubElements.add(subElem2);
		allMockSubElements.add(subElem3);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedSubElementList = finder.getSubElementsPresentInDom(mockParentBy, mockSubLocator);
		List<WebElement> actualSubElementList = finder.getSubElementsPresentInDom(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsPresentInDOMFromParentByTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		By mockParentBy = Mockito.mock(By.class);
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		WebElement subElem2 = Mockito.mock(WebElement.class);
		WebElement subElem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1);
		allMockSubElements.add(subElem2);
		allMockSubElements.add(subElem3);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		Mockito.when(mockDriver.findElement(mockParentBy)).thenReturn(mockParentElement);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedSubElementList = allMockSubElements;
		List<WebElement> actualSubElementList = finder.getSubElementsPresentInDom(mockParentBy, mockSubLocator, 0);
		
		Mockito.verify(mockDriver, Mockito.times(numOfTimesCallingRealMethod)).findElement(mockParentBy);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsPresentInDOMFromParentElementNoTimeInputIsSameAsZeroSecondInputTest() throws Exception {
		int numOfTimesCallingRealMethod = 2;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		WebElement subElem2 = Mockito.mock(WebElement.class);
		WebElement subElem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1);
		allMockSubElements.add(subElem2);
		allMockSubElements.add(subElem3);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedSubElementList = finder.getSubElementsPresentInDom(mockParentElement, mockSubLocator);
		List<WebElement> actualSubElementList = finder.getSubElementsPresentInDom(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}
	@Test
	public void getSubElementsPresentInDOMFromParentElementTest() throws Exception {
		int numOfTimesCallingRealMethod = 1;
		By mockSubLocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1 = Mockito.mock(WebElement.class);
		WebElement subElem2 = Mockito.mock(WebElement.class);
		WebElement subElem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1);
		allMockSubElements.add(subElem2);
		allMockSubElements.add(subElem3);
		
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, mockSubLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1);
		Mockito.when(mockParentElement.findElements(mockSubLocator)).thenReturn(allMockSubElements);

		List<WebElement> expectedSubElementList = allMockSubElements;
		List<WebElement> actualSubElementList = finder.getSubElementsPresentInDom(mockParentElement, mockSubLocator, 0);
		
		Mockito.verify(mockWait, Mockito.times(numOfTimesCallingRealMethod)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(numOfTimesCallingRealMethod)).findElements(mockSubLocator);
		Assert.assertEquals(expectedSubElementList, actualSubElementList);
	}

	
	
	@Test
	public void getElementIfClickableWithinWaitTimeSuccessfulTest() throws Exception {
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);

		WebElement expectedElement = mockElement;
		WebElement actualElement = finder.getElementIfClickableWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockDriver, Mockito.times(1)).findElement(mockLocator);
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(expectedElement, actualElement);
	}
	@Test
	public void getElementIfClickableWithinWaitTimeTimeoutTest() throws Exception {
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(TimeoutException.class);

		WebElement actualElement = finder.getElementIfClickableWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertNull(actualElement);
	}

	@Test
	public void getElementIfVisibleWithinWaitTimeSuccessfulTest() throws Exception {
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfElementLocated(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockDriver.findElement(mockLocator)).thenReturn(mockElement);

		WebElement expectedElement = mockElement;
		WebElement actualElement = finder.getElementIfVisibleWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockDriver, Mockito.times(1)).findElement(mockLocator);
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(expectedElement, actualElement);
	}
	@Test
	public void getElementIfVisibleWithinWaitTimeTimeoutTest() throws Exception {
		PowerMockito.whenNew(WebDriverWait.class).withAnyArguments().thenReturn(mockWait);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfElementLocated(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfElementLocated(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(TimeoutException.class);
		
		WebElement actualElement = finder.getElementIfVisibleWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertNull(actualElement);
	}

	@Test
	public void getAllElementsIfEnabledWithinWaitTimeSuccessfulTest() {
		WebElement subElem1IsEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsEnabled);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);
		
		ArrayList<WebElement> expectedEnabledElementList = new ArrayList<WebElement>();
		expectedEnabledElementList.add(subElem1IsEnabled);
		expectedEnabledElementList.add(subElem3IsEnabled);
		List<WebElement> actualEnabledElementList = finder.getAllElementsIfEnabledWithinWaitTime(mockLocator, mockWait);

		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Mockito.verify(subElem1IsEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(1)).isEnabled();
		Assert.assertEquals(expectedEnabledElementList, actualEnabledElementList);
	}
	@Test
	public void getAllElementsIfEnabledWithinWaitTimeTimeoutTest() {
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new TimeoutException());
		
		List<WebElement> actualElementList = finder.getAllElementsIfEnabledWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(0, actualElementList.size());;
	}
	@Test
	public void getAllElementsIfEnabledWithinWaitTimeWebDriverExceptionTest() {
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.elementToBeClickable(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.elementToBeClickable(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new WebDriverException());

		List<WebElement> actualElementList = finder.getAllElementsIfEnabledWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(0, actualElementList.size());;
	}

	@Test
	public void getAllElementsIfVisisbleWithinWaitTimeSuccessfulTest() {
		WebElement elem1 = Mockito.mock(WebElement.class);
		WebElement elem2 = Mockito.mock(WebElement.class);
		WebElement elem3 = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allElementList = new ArrayList<WebElement>();
		allElementList.add(elem1);
		allElementList.add(elem2);
		allElementList.add(elem3);
		
		ExpectedCondition<List<WebElement>> expectedConditionExpression = ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(allElementList);
		Mockito.when(mockDriver.findElements(mockLocator)).thenReturn(allElementList);
		
		ArrayList<WebElement> expectedElementsList = allElementList;
		List<WebElement> actualElementList = finder.getAllElementsIfVisisbleWithinWaitTime(mockLocator, mockWait);

		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Mockito.verify(mockDriver, Mockito.times(1)).findElements(mockLocator);
		Assert.assertEquals(expectedElementsList, actualElementList);
	}
	@Test
	public void getAllElementsIfVisisbleWithinWaitTimeTimeoutTest() {
		ExpectedCondition<List<WebElement>> expectedConditionExpression = ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new TimeoutException());

		List<WebElement> actualElementList = finder.getAllElementsIfVisisbleWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(0, actualElementList.size());;
	}
	@Test
	public void getAllElementsIfVisisbleWithinWaitTimeWebDriverExceptionTest() {
		ExpectedCondition<List<WebElement>> expectedConditionExpression = ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfAllElementsLocatedBy(mockLocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new WebDriverException());

		List<WebElement> actualElementList = finder.getAllElementsIfVisisbleWithinWaitTime(mockLocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(0, actualElementList.size());;
	}
	
	@Test
	public void getSubElementIfEnabledWithinWaitTimeSuccessfulTest() {
		By sublocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		WebElement subElem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement subElem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockSubElements = new ArrayList<WebElement>();
		allMockSubElements.add(subElem1IsNOTEnabled);
		allMockSubElements.add(subElem2IsNOTEnabled);
		allMockSubElements.add(subElem3IsEnabled);
		
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(subElem1IsNOTEnabled);
		Mockito.when(mockParentElement.findElements(sublocator)).thenReturn(allMockSubElements);
		Mockito.when(subElem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(subElem3IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedFirstEnabledSubElement = subElem3IsEnabled;
		WebElement actualSubElement = finder.getSubElementIfEnabledWithinWaitTime(mockParentElement, sublocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Mockito.verify(mockParentElement, Mockito.times(1)).findElements(sublocator);
		Mockito.verify(subElem1IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(subElem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(subElem3IsEnabled, Mockito.times(1)).isEnabled();
		Assert.assertEquals(expectedFirstEnabledSubElement, actualSubElement);
	}
	@Test
	public void getSubElementIfEnabledWithinWaitTimeTimeoutTest() {
		final By sublocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator)).thenReturn(expectedConditionExpression);		
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new TimeoutException());

		WebElement actualElement = finder.getSubElementIfEnabledWithinWaitTime(mockParentElement, sublocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertNull(actualElement);
	}
	
	@Test
	public void getSubElementIfVisibleWithinWaitTimeSuccessfulTest() {
		final By sublocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenReturn(mockElement);
		Mockito.when(mockParentElement.findElement(sublocator)).thenReturn(mockElement);

		WebElement expectedElement = mockElement;
		WebElement actualElement = finder.getSubElementIfVisibleWithinWaitTime(mockParentElement, sublocator, mockWait);
		
		Mockito.verify(mockParentElement, Mockito.times(1)).findElement(sublocator);
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertEquals(expectedElement, actualElement);
	}
	@Test
	public void getSubElementIfVisibleWithinWaitTimeTimeoutTest() {
		final By sublocator = mockLocator;
		WebElement mockParentElement = Mockito.mock(WebElement.class);
		
		ExpectedCondition<WebElement> expectedConditionExpression = ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator);
		PowerMockito.mockStatic(ExpectedConditions.class);
		PowerMockito.when(ExpectedConditions.presenceOfNestedElementLocatedBy(mockParentElement, sublocator)).thenReturn(expectedConditionExpression);
		Mockito.when(mockWait.until(expectedConditionExpression)).thenThrow(new TimeoutException());

		WebElement actualElement = finder.getSubElementIfVisibleWithinWaitTime(mockParentElement, sublocator, mockWait);
		
		Mockito.verify(mockWait, Mockito.times(1)).until(expectedConditionExpression);
		Assert.assertNull(actualElement);
	}
	
	@Test
	public void getFirstEnabledElementFromListReturnsNullIfNoElementsInListAreEnabled() {
		WebElement elem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem3IsNOTEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockElements = new ArrayList<WebElement>();
		allMockElements.add(elem1IsNOTEnabled);
		allMockElements.add(elem2IsNOTEnabled);
		allMockElements.add(elem3IsNOTEnabled);
		
		Mockito.when(elem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem3IsNOTEnabled.isEnabled()).thenReturn(false);

		WebElement firstEnabledElementInListExpectedNull = finder.getFirstEnabledElementFromList(allMockElements);
		Mockito.verify(elem1IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem3IsNOTEnabled, Mockito.times(1)).isEnabled();
		Assert.assertNull(firstEnabledElementInListExpectedNull);
	}
	@Test
	public void getFirstEnabledElementFromListReturnsSecondItemWhenFirstIsNotEnabled() {
		WebElement elem1IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem2IsEnabled = Mockito.mock(WebElement.class);
		WebElement elem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockElements = new ArrayList<WebElement>();
		allMockElements.add(elem1IsNOTEnabled);
		allMockElements.add(elem2IsEnabled);
		allMockElements.add(elem3IsEnabled);
		
		Mockito.when(elem1IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem2IsEnabled.isEnabled()).thenReturn(true);

		WebElement expectedFirstEnabledElementInList = elem2IsEnabled;
		WebElement actualFirstEnabledElementInList = finder.getFirstEnabledElementFromList(allMockElements);
		
		Mockito.verify(elem1IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem2IsEnabled, Mockito.times(1)).isEnabled();
		Assert.assertEquals(expectedFirstEnabledElementInList, actualFirstEnabledElementInList);
	}
	
	@Test
	public void getAllEnabledElementsFromListTest() {
		WebElement elem1IsEnabled = Mockito.mock(WebElement.class);
		WebElement elem2IsNOTEnabled = Mockito.mock(WebElement.class);
		WebElement elem3IsEnabled = Mockito.mock(WebElement.class);
		ArrayList<WebElement> allMockElements = new ArrayList<WebElement>();
		allMockElements.add(elem1IsEnabled);
		allMockElements.add(elem2IsNOTEnabled);
		allMockElements.add(elem3IsEnabled);
		
		Mockito.when(elem1IsEnabled.isEnabled()).thenReturn(true);
		Mockito.when(elem2IsNOTEnabled.isEnabled()).thenReturn(false);
		Mockito.when(elem3IsEnabled.isEnabled()).thenReturn(true);

		List<WebElement> expectedElementsList = new ArrayList<WebElement>();
		expectedElementsList.add(elem1IsEnabled);
		expectedElementsList.add(elem3IsEnabled);
		List<WebElement> actualElementsList = finder.getAllEnabledElementsFromList(allMockElements);
		
		Mockito.verify(elem1IsEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem2IsNOTEnabled, Mockito.times(1)).isEnabled();
		Mockito.verify(elem3IsEnabled, Mockito.times(1)).isEnabled();
		Assert.assertEquals(expectedElementsList, actualElementsList);
	}
	

	@Test
	public void getMaxWaitTimeWithValidationChecksReturnsInputIfBetweenZeroAndAbsMaxWaitTime() {
		int input = 3;
		int expectedMaxTime = input;
		int actualMaxTime = finder.getMaxWaitTimeWithValidationChecks(input);
		Assert.assertEquals(expectedMaxTime, actualMaxTime);
	}
	@Test
	public void getMaxWaitTimeWithValidationChecksReturnsPropertyValueIfNegativeInput() {
		int input = -2;
		int expectedMaxTime = finder.getAbsoluteWaitTimeInSeconds();
		int actualMaxTime = finder.getMaxWaitTimeWithValidationChecks(input);
		Assert.assertEquals(expectedMaxTime, actualMaxTime);
	}
	@Test
	public void getMaxWaitTimeWithValidationChecksReturnsPropertyValueIfInputIsMoreThanMaxPropertyValue() {
		int input = finder.getAbsoluteWaitTimeInSeconds() + 1;
		int expectedMaxTime = finder.getAbsoluteWaitTimeInSeconds();
		int actualMaxTime = finder.getMaxWaitTimeWithValidationChecks(input);
		Assert.assertEquals(expectedMaxTime, actualMaxTime);
	}

	
	@Test
	public void getSecondsLeftTest()
	{
		long startTimeInSeconds = (long)1100;
		long curTimeInSeconds = (long)1102;
		int maxTimeToTake = 5;
		int expected = 3; //[5 - (1102 - 1100)]
		int actual = finder.getSecondsLeft(startTimeInSeconds, curTimeInSeconds, maxTimeToTake);
		Assert.assertEquals(expected, actual);
	}
}

