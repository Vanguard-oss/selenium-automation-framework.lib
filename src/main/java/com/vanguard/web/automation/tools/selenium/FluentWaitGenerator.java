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

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class FluentWaitGenerator {
	protected FluentWait<WebDriver> getFluentWait(WebDriver driver, int maxTimeInSec) {
		return new FluentWait<WebDriver>(driver)    
			    .withTimeout(maxTimeInSec, TimeUnit.SECONDS)    
			    .pollingEvery(100, TimeUnit.MILLISECONDS)   
			    .ignoring(NoSuchElementException.class);
	}
	
	protected FluentWait<WebDriver> getFluentWait(WebDriver driver, int pollingInMilliseconds, Class<? extends Throwable> classToIgnore, int maxTimeInSec) {
		return new FluentWait<WebDriver>(driver)    
			    .withTimeout(maxTimeInSec, TimeUnit.SECONDS)    
			    .pollingEvery(pollingInMilliseconds, TimeUnit.MILLISECONDS)   
			    .ignoring(classToIgnore);
	}
}
