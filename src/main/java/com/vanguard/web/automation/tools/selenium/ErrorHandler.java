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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.vanguard.web.selenium.exceptions.CouldNotTakeActionOnElementException;
import com.vanguard.web.selenium.properties.PropertiesManager;

/**
 * Called when an error is encountered somewhere in the test execution.
 * This class determines if a test should fail and stop executing, or log an error to the console and keep running.
 * Then perfoms the appropriate step (throwing an error, or just logging something to the console).
 * 
 */
public class ErrorHandler {
	private static final String SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY = "failIfElementNotFound";
	protected static final int stacktraceDepthOffset = 2; //Depth:0="StackTrace", Depth:1="getMethodNameFromStackTrace"
	
	protected void throwOrLogError(WebDriver driver, final String locator, final String action, String pageName) {
		if(Boolean.valueOf(System.getProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY)) ||
				Boolean.valueOf(PropertiesManager.getFrameworkProperty(SELENIUM_FAIL_IF_NOT_FOUND_PROPERTY))) {
			throw new CouldNotTakeActionOnElementException(locator, pageName, action, driver);
		}
		logNullElementWarning(locator, action, pageName);
	}
	protected void logNullElementWarning(final String locator, final String action, String pageName) {
		Logger.getAnonymousLogger().warning(CouldNotTakeActionOnElementException.getMessage(locator, pageName, action));
	}
	protected String getMethodNameFromStackTrace(final int depth) {
		String returnVal = "";
		try{
			returnVal = Thread.currentThread().getStackTrace()[depth].getMethodName();
		} catch(Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "Can not get method name from stacktrace.  Error details:");
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
		}
		return returnVal;
	}
}
