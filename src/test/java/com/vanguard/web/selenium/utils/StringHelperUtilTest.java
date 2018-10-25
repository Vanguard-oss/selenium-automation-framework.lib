package com.vanguard.web.selenium.utils;

import org.junit.Assert;
import org.junit.Test;


public class StringHelperUtilTest {
	
	
	@Test
	public void camelCaseOfEmptyStringShouldReturnDefaultValueWhenCappingFirstLetterTest() {
		Assert.assertEquals(StringHelperUtil.DEFAULT_CAMELCASE_RESULT, StringHelperUtil.camelCase("", true));
	}
	@Test
	public void camelCaseOfEmptyStringShouldReturnDefaultValueWhenNOTCappingFirstLetterTest() {
		Assert.assertEquals(StringHelperUtil.DEFAULT_CAMELCASE_RESULT, StringHelperUtil.camelCase("", false));
	}
	
	@Test
	public void camelCaseShouldReturnCorrectStringFirstLetterCapsTest() {
		String input = "this is my test & 1 * string";
		String expected = "ThisIsMyTest1String";
		String actual = StringHelperUtil.camelCase(input, true);
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void camelCaseShouldReturnCorrectStringFirstLetterNotCapsTest() {
		String input = "this is my test1234string";
		String expected = "thisIsMyTest1234string";
		String actual = StringHelperUtil.camelCase(input, false);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isNullOrEmptyShouldReturnTrueIfNull() {
		Assert.assertTrue(StringHelperUtil.isNullOrEmpty(null));
	}
	@Test
	public void isNullOrEmptyShouldReturnTrueIfEmpty() {
		Assert.assertTrue(StringHelperUtil.isNullOrEmpty(""));
	}
	@Test
	public void isNullOrEmptyShouldReturnFalseIfInputIsNotEmpty() {
		Assert.assertFalse(StringHelperUtil.isNullOrEmpty("anyStringValue"));
	}

}
