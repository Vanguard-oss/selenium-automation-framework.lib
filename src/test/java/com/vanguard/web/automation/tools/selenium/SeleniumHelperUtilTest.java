package com.vanguard.web.automation.tools.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class SeleniumHelperUtilTest {
	
	@Test
	public void updateXPathIfNeededForSubElementsWhenUsingXPathOfRelativePathTest() {
		By expected = By.xpath(".//input");
		By actual = SeleniumHelperUtil.updateXPathIfNeededForSubElements(By.xpath("//input"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void updateXPathIfNeededForSubElementsWhenUsingXPathOfAbsolutePathTest() {
		By expected = By.xpath("/tr[5]/td[3]/input");
		By actual = SeleniumHelperUtil.updateXPathIfNeededForSubElements(By.xpath("/tr[5]/td[3]/input"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void updateXPathIfNeededForSubElementsWhenUsingIDTest() {
		By expected = By.id("anyID");
		By actual = SeleniumHelperUtil.updateXPathIfNeededForSubElements(By.id("anyID"));
		Assert.assertEquals(expected, actual);
	}
	
}
