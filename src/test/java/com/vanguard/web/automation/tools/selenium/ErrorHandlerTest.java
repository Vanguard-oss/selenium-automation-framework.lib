/*
 ****************************************************************************
 *
 * Copyright (c)2018 The Vanguard Group of Investment Companies (VGI)
 * All rights reserved.
 *
 * This source code is CONFIDENTIAL and PROPRIETARY to VGI. Unauthorized
 * distribution, adaptation, or use may be subject to civil and criminal
 * penalties.
 *
 ****************************************************************************
 Module Description:

 $HeadURL:$
 $LastChangedRevision:$
 $Author:$
 $LastChangedDate:$
*/
package com.vanguard.web.automation.tools.selenium;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.exceptions.CouldNotTakeActionOnElementException;
import com.vanguard.web.selenium.properties.PropertiesManager;
import com.vanguard.web.selenium.utils.ScreenshotUtility;

@PrepareForTest({ErrorHandler.class, CouldNotTakeActionOnElementException.class, ScreenshotUtility.class, Logger.class})
@RunWith(PowerMockRunner.class)
public class ErrorHandlerTest {
	
	private static final String SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY = "failIfElementNotFound";
	ErrorHandler errorHandler;
	WebDriver mockDriver;
	CouldNotTakeActionOnElementException mockCouldNotTakeActionOnElementException;
	
	@Before
	public void setup() {
		errorHandler = new ErrorHandler();
		mockDriver = Mockito.mock(WebDriver.class);
		mockCouldNotTakeActionOnElementException = Mockito.mock(CouldNotTakeActionOnElementException.class);
	}
	
	@After
	public void validate() {
	    Mockito.validateMockitoUsage();
	}
	
	
	@Test(expected=CouldNotTakeActionOnElementException.class)
	public void throwOrLogErrorThrowsNewErrorWhenSystemPropertyIsTrueTest() throws Exception {
		System.setProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "true");
		PropertiesManager.setFrameworkProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "false");
		PowerMockito.whenNew(CouldNotTakeActionOnElementException.class).withAnyArguments().thenReturn(mockCouldNotTakeActionOnElementException);
		PowerMockito.spy(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class, "takeScreenshot", Mockito.anyString(), Mockito.any(WebDriver.class));
		
		errorHandler.throwOrLogError(mockDriver, "anyLocator", "anyAction", "anyPage");
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
		PowerMockito.verifyNew(CouldNotTakeActionOnElementException.class);
	}
	@Test(expected=CouldNotTakeActionOnElementException.class)
	public void throwOrLogErrorThrowsNewErrorWhenSystemPropertyIsFalseButFrameworkPropertyIsTrueTest() throws Exception {
		System.setProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "false");
		PropertiesManager.setFrameworkProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "true");
		PowerMockito.whenNew(CouldNotTakeActionOnElementException.class).withAnyArguments().thenReturn(mockCouldNotTakeActionOnElementException);
		PowerMockito.spy(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class, "takeScreenshot", Mockito.anyString(), Mockito.any(WebDriver.class));
		
		errorHandler.throwOrLogError(mockDriver, "anyLocator", "anyAction", "anyPage");
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
		PowerMockito.verifyNew(CouldNotTakeActionOnElementException.class);
	}
	@Test()
	public void throwOrLogErrorDoesNOTThrowNewErrorWhenBothSystemPropertyIsAndFrameworkPropertiesAreFalse() throws Exception {
		Logger mockLogger = Mockito.mock(Logger.class);
		System.setProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "false");
		PropertiesManager.setFrameworkProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY, "false");
		PowerMockito.mockStatic(Logger.class);
		PowerMockito.when(Logger.getAnonymousLogger()).thenReturn(mockLogger);
		Mockito.doNothing().when(mockLogger).warning(Mockito.anyString());
		PowerMockito.mockStatic(CouldNotTakeActionOnElementException.class);
		PowerMockito.doReturn("anyString").when(CouldNotTakeActionOnElementException.class, "getMessage", Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		errorHandler.throwOrLogError(mockDriver, "anyLocator", "anyAction", "anyPage");
		Mockito.verify(mockLogger, Mockito.times(1)).warning(Mockito.anyString());
		PowerMockito.verifyStatic(CouldNotTakeActionOnElementException.class, Mockito.times(1));
		CouldNotTakeActionOnElementException.getMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	public void getMethodNameFromStackTraceOfDepthOffsetIsThisMethodNameTest() {
		Assert.assertEquals("getMethodNameFromStackTraceOfDepthOffsetIsThisMethodNameTest", errorHandler.getMethodNameFromStackTrace(ErrorHandler.stacktraceDepthOffset));
	}
}
