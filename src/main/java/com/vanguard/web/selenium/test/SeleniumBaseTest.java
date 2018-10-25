package com.vanguard.web.selenium.test;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vanguard.web.selenium.properties.PropertiesManager;

public class SeleniumBaseTest {

	private static final Logger logger = Logger.getLogger( SeleniumBaseTest.class.getName() );	
	protected WebDriver driver;
	static final String GECKO_DRIVER_LOCATION_PROPERTY = "geckoDriverLocation";
	static final String GECKO_DRIVER_SYSTEM_PROPERTY_TO_SET = "webdriver.gecko.driver";
	
	@Before
	public void baseTestSetup() {
		setUpGeckoDriverProperty();
		launchTheBrowser();
	}

	void setUpGeckoDriverProperty() {
		File frameworkProperties = new File(PropertiesManager.FRAMEWORK_PROPERTIES_FILE_LOCATION);
		String errorMessage = "You must set the [" + GECKO_DRIVER_LOCATION_PROPERTY + "] property within your System properties, or your properties file: " + frameworkProperties.getAbsolutePath();
		if((System.getProperty(GECKO_DRIVER_LOCATION_PROPERTY) == null)) {
			if(PropertiesManager.getFrameworkProperty(GECKO_DRIVER_LOCATION_PROPERTY) == null) {
				logger.log(Level.SEVERE, errorMessage);
			} else {
				System.setProperty(GECKO_DRIVER_SYSTEM_PROPERTY_TO_SET, PropertiesManager.getFrameworkProperties().getProperty(GECKO_DRIVER_LOCATION_PROPERTY));	
			}
		} else {
			System.setProperty(GECKO_DRIVER_SYSTEM_PROPERTY_TO_SET, System.getProperty(GECKO_DRIVER_LOCATION_PROPERTY));
		}
	}
	
	private void launchTheBrowser() {
		driver = new FirefoxDriver();
	}
	
	@After
	public void baseTestTeardown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
}
