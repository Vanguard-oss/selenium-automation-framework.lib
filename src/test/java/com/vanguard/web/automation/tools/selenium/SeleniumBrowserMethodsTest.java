package com.vanguard.web.automation.tools.selenium;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;

import com.vanguard.web.selenium.exceptions.NullWebDriverException;





public class SeleniumBrowserMethodsTest {
	
	@Test(expected=NullWebDriverException.class)
	public void SeleniumBrowserMethodsConstructorWithNullDriverTest() {
		new SeleniumBrowserMethods(null);
	}
	
	@Test
	public void SeleniumBrowserMethodsConstructorSuccessfulTest() {
		SeleniumBrowserMethods actual = new SeleniumBrowserMethods(EasyMock.createMock(WebDriver.class));
		Assert.assertNotNull(actual);
	}

	WebDriver mockDriver;
	SeleniumBrowserMethods browser;
	
	@Before
	public void SeleniumBrowserMethodsSetup() {
		mockDriver = EasyMock.createMock(WebDriver.class);
		browser = new SeleniumBrowserMethods(mockDriver);
	}
	
	@Test
	public void getCurrentUrlTest() {
		EasyMock.expect(mockDriver.getCurrentUrl()).andReturn(null);
		EasyMock.replay(mockDriver);
		browser.getCurrentUrl();
		EasyMock.verify(mockDriver);
	}

	@Test
	public void visitTest() {
		String anyURL = "http://www.anyurl.com";
		/*expect*/mockDriver.get(anyURL);
		EasyMock.expectLastCall().once();
		EasyMock.replay(mockDriver);
		browser.visit(anyURL);
		EasyMock.verify(mockDriver);
	}
	@Test
	public void getTitleTest() {
		EasyMock.expect(mockDriver.getTitle()).andReturn(null);
		EasyMock.replay(mockDriver);
		browser.getTitle();
		EasyMock.verify(mockDriver);
	}
	@Test
	public void navigateBackTest() {
		Navigation mockNavigate = EasyMock.createMock(Navigation.class);
		EasyMock.expect(mockDriver.navigate()).andReturn(mockNavigate).times(2);
		EasyMock.replay(mockDriver);
		/*expect*/mockDriver.navigate().back();
		EasyMock.expectLastCall().once();
		EasyMock.replay(mockNavigate);
		browser.navigateBack();
		EasyMock.verify(mockNavigate);
	}
	@SuppressWarnings("deprecation")
	@Test
	public void maximizeTest() {
		Options mockManage = EasyMock.createMock(Options.class);
		Window mockWindow = EasyMock.createMock(Window.class);
		EasyMock.expect(mockDriver.manage()).andReturn(mockManage).times(2);
		EasyMock.expect(mockManage.window()).andReturn(mockWindow).times(2);
		EasyMock.replay(mockDriver);
		EasyMock.replay(mockManage);
		/*expect*/mockWindow.maximize();
		EasyMock.expectLastCall();
		EasyMock.replay(mockWindow);
		browser.maximize();
		EasyMock.verify(mockWindow);
	}
	@Test
	public void resizeTest() {
		int width = 1920;
		int height = 1080;
		Options mockManage = EasyMock.createMock(Options.class);
		Window mockWindow = EasyMock.createMock(Window.class);
		EasyMock.expect(mockDriver.manage()).andReturn(mockManage).times(2);
		EasyMock.expect(mockManage.window()).andReturn(mockWindow).times(2);
		EasyMock.replay(mockDriver);
		EasyMock.replay(mockManage);
		/*expect*/mockWindow.setSize(new Dimension(width, height));
		EasyMock.expectLastCall().once();
		EasyMock.replay(mockWindow);
		browser.resize(width,height);
		EasyMock.verify(mockWindow);
	}
}
