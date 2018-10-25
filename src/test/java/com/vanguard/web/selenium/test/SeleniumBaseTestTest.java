package com.vanguard.web.selenium.test;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.properties.PropertiesManager;

@PrepareForTest({SeleniumBaseTest.class, System.class})
@RunWith(PowerMockRunner.class)
public class SeleniumBaseTestTest {

	
	SeleniumBaseTest baseTest = new SeleniumBaseTest();
	
	@Test
	public void setUpGeckoDriverPropertyWithSystemPropertySetTest() throws Exception {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getProperty(Mockito.anyString())).thenReturn(PropertiesManager.FRAMEWORK_PROPERTIES_FILE_LOCATION);
		baseTest.setUpGeckoDriverProperty();
		PowerMockito.verifyStatic(System.class, Mockito.times(2));
		System.getProperty(Mockito.anyString());
	}

	@Test
	public void setUpGeckoDriverPropertyWithSystemPropertyNotSetTest() throws Exception {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getProperty(Mockito.anyString())).thenReturn(null);
		baseTest.setUpGeckoDriverProperty();
		PowerMockito.verifyStatic(System.class, Mockito.times(1));
		System.getProperty(Mockito.anyString());
		PowerMockito.verifyStatic(System.class, Mockito.times(1));
		System.setProperty(SeleniumBaseTest.GECKO_DRIVER_SYSTEM_PROPERTY_TO_SET, 
				PropertiesManager.getFrameworkProperties().getProperty(SeleniumBaseTest.GECKO_DRIVER_LOCATION_PROPERTY));
	}
	
	@Test
	public void baseTestTeardownWhenDriverIsNotNullTest() {
		WebDriver mockDriver = EasyMock.createMock(WebDriver.class);
		baseTest.driver = mockDriver;
		/*expect*/mockDriver.quit();
		EasyMock.expectLastCall();
		EasyMock.replay(mockDriver);
		baseTest.baseTestTeardown();
	}
}
