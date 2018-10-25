package com.vanguard.web.selenium.exceptions;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.utils.ScreenshotUtility;


@PrepareForTest({CouldNotTakeActionOnElementException.class, ScreenshotUtility.class})
@RunWith(PowerMockRunner.class)
public class CouldNotTakeActionOnElementExceptionTest {

	WebDriver driver = EasyMock.createMock(WebDriver.class);
	
	@Test
	public void CouldNotTakeActionOnElementExceptionCreationTest() throws Exception {
		String locator = "By.xpath(\"//input[@text='Continue']\")";
		String page = "MadeUpPage";
		String action = "click";
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class, "takeScreenshot", Mockito.anyString(), Mockito.any(WebDriver.class));
		new CouldNotTakeActionOnElementException(locator, page, action, driver);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}

}
