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


@PrepareForTest({InvalidElementException.class, ScreenshotUtility.class})
@RunWith(PowerMockRunner.class)
public class InvalidElementExceptionTest {

	WebDriver driver = EasyMock.createMock(WebDriver.class);
	
	@Test
	public void InvalidElementExceptionCreationTest() throws Exception {
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class, "takeScreenshot", Mockito.anyString(), Mockito.any(WebDriver.class));
		new InvalidElementException("error message", driver);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}
	
}
