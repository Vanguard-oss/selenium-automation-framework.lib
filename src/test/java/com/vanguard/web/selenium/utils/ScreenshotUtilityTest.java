package com.vanguard.web.selenium.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.OutputType;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.exceptions.WebDriverAndTakesScreenshotImpl;

@PrepareForTest({ScreenshotUtility.class, FileUtils.class})
@RunWith(PowerMockRunner.class)
public class ScreenshotUtilityTest {

	WebDriverAndTakesScreenshotImpl driver = EasyMock.createMock(WebDriverAndTakesScreenshotImpl.class);
	
	@Test
	public void verifyTakeScreenshotCallsCopyFromFileUtilsTest() throws Exception {
		PowerMockito.mockStatic(FileUtils.class);
		PowerMockito.doNothing().when(FileUtils.class, "copyFile", Mockito.any(File.class), Mockito.any(File.class));
		File mockFile = Mockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(mockFile);
		
		EasyMock.expect(driver.getScreenshotAs(OutputType.FILE)).andReturn(mockFile);
		EasyMock.replay(driver);
		ScreenshotUtility.takeScreenshot("message", driver);
		EasyMock.verify(driver);
		PowerMockito.verifyStatic(FileUtils.class, Mockito.times(1));
		FileUtils.copyFile(Mockito.any(File.class), Mockito.any(File.class));
	}

	@Test
	public void getTimeStampTest() {
		Date date = new GregorianCalendar(1776, Calendar.JULY, 4).getTime();
		String expected = "04-07-1776_00-00-00";
		String actual = ScreenshotUtility.getTimeStamp(date);
		Assert.assertEquals(expected, actual);
	}

}
