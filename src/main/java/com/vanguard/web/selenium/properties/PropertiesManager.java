package com.vanguard.web.selenium.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesManager {

	public static final String FRAMEWORK_PROPERTIES_FILE_LOCATION = "src/test/resources/seleniumFramework.properties";
	private static Properties frameworkProperties = new Properties();
	private static boolean hasBeenLoaded = false; //TODO: Seems like should be a better way to do this
	
	/**
	 * Loads the Framework Properties from the specified constant location.
	 * This file should include things like: 
	 * failIfElementNotFound=true|false - 
	 * 		true will fail a test as soon as it encounters an element it can't find, false will log an error in the console and continue the test.
	 * geckoDriverLocation=/some/path/to/the/geckodriver/executable
	 * SELENIUM_MAX_WAIT=60 - the maximum time in seconds you want any explicit wait anywhere in your suite to wait for.
	 */
	public static void loadFrameworkProperties() {

		try (
			InputStream input = new FileInputStream(FRAMEWORK_PROPERTIES_FILE_LOCATION);
		) {
			frameworkProperties.load(input);
		} catch (IOException ex) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "The properties file at: " + FRAMEWORK_PROPERTIES_FILE_LOCATION + " was NOT found.  " +
					"The framework will use defaults for all properties that should be in that file.");
			Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
		}
	}

	public static Properties getFrameworkProperties() {
		if(!hasBeenLoaded) {
			loadFrameworkProperties();
			hasBeenLoaded=true;
		}
		return cloneFrameworkProperties();
	}
	
	private static Properties cloneFrameworkProperties() {
		Properties clone = (Properties) frameworkProperties.clone();
		for (String key : frameworkProperties.stringPropertyNames()) {
			String value = frameworkProperties.getProperty(key);
			clone.setProperty(key, value);
		}
		return clone;
	}

	public static void setFrameworkProperty(String name, String value) {
		getFrameworkProperties();
		frameworkProperties.setProperty(name, value);
	}
	public static String getFrameworkProperty(String name) {
		return getFrameworkProperties().getProperty(name);
	}
}
