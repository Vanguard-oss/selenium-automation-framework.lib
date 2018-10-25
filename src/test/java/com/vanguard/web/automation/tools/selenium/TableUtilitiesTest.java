package com.vanguard.web.automation.tools.selenium;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vanguard.web.selenium.exceptions.InvalidElementException;
import com.vanguard.web.selenium.exceptions.WebDriverAndTakesScreenshotImpl;
import com.vanguard.web.selenium.utils.ScreenshotUtility;



@PrepareForTest(ScreenshotUtility.class)
@RunWith(PowerMockRunner.class)
public class TableUtilitiesTest {
	
	private WebDriver driver = EasyMock.createMock(WebDriverAndTakesScreenshotImpl.class);
	private SeleniumElementFinder finder = EasyMock.createMock(SeleniumElementFinder.class);
	private SeleniumActionMethods actions = EasyMock.createMock(SeleniumActionMethods.class);
	private TableUtilities utils = new TableUtilities(driver, finder, actions);
	
	final WebElement mockRow1 = EasyMock.createMock(WebElement.class);
	final WebElement mockRow2 = EasyMock.createMock(WebElement.class);
	final WebElement mockRow3 = EasyMock.createMock(WebElement.class);

	final WebElement mockCell_1_1 = EasyMock.createMock(WebElement.class);
	final WebElement mockCell_1_2 = EasyMock.createMock(WebElement.class);

	final WebElement mockCell_2_1 = EasyMock.createMock(WebElement.class);
	final WebElement mockCell_2_2 = EasyMock.createMock(WebElement.class);

	final WebElement mockCell_3_1 = EasyMock.createMock(WebElement.class);
	final WebElement mockCell_3_2 = EasyMock.createMock(WebElement.class);
	
	
	@Test
	public void getAllTablesCallsFinderTest() {
		EasyMock.expect(finder.getElements(By.xpath("//table"))).andReturn(null);
		EasyMock.replay(finder);
		utils.getAllTables();
		EasyMock.verify(finder);
	}

	@Test
	public void getAllTablesWithinElementCallsFinderTest() {
		By sampleBy = By.id("someID");
		EasyMock.expect(finder.getSubElements(sampleBy, By.xpath(".//table"))).andReturn(null);
		EasyMock.replay(finder);
		utils.getAllTablesWithinElement(sampleBy);
		EasyMock.verify(finder);
	}
	
	
	@Test
	public void getCellOverFromExactStringMatchReturnsNullIfMatchIsEmptyTest() {
		WebElement anyElement = EasyMock.createMock(WebElement.class);
		String emptyExactMatchString = "";
		Assert.assertNull(utils.getCellOverFromExactStringMatch(anyElement, emptyExactMatchString, 0, 0));
	}

	@Test
	public void getCellOverFromExactStringMatchReturnsNullIfTableIsNullTest() {
		WebElement nullTable = null;
		String anyMatchString = "anyValidMatchString";
		Assert.assertNull(utils.getCellOverFromExactStringMatch(nullTable, anyMatchString, 0, 0));
	}

	@Test
	public void getCellOverFromExactStringMatchReturnsSuccessfulTest() {
		WebElement mockTable = EasyMock.createMock(WebElement.class);
		String anyMatchString = "anyValidMatchString";
		EasyMock.expect(finder.getSubElements(mockTable, By.xpath(".//tr"), 0)).andReturn(getMockAllRows());
		EasyMock.expect(finder.getSubElements(mockRow1, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1()).times(2);
		
		EasyMock.expect(actions.getText(mockCell_1_1)).andReturn(anyMatchString);
		
		EasyMock.replay(finder);
		EasyMock.replay(actions);
		WebElement expected = mockCell_1_2;
		WebElement actual = utils.getCellOverFromExactStringMatch(mockTable, anyMatchString, 0, 1);
		EasyMock.verify(finder);
		EasyMock.verify(actions);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void getCellOverFromExactStringMatchReturnsNullIfNoMatchTest() {
		WebElement mockTable = EasyMock.createMock(WebElement.class);
		String anyMatchString = "anyValidMatchString";
		EasyMock.expect(finder.getSubElements(mockTable, By.xpath(".//tr"), 0)).andReturn(getMockAllRows());
		EasyMock.expect(finder.getSubElements(mockRow1, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1());
		EasyMock.expect(finder.getSubElements(mockRow2, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow2());
		EasyMock.expect(finder.getSubElements(mockRow3, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow3());
		
		EasyMock.expect(actions.getText(mockCell_1_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_1_2)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_2_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_2_2)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_3_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_3_2)).andReturn("");
		
		EasyMock.replay(finder);
		EasyMock.replay(actions);
		WebElement actual = utils.getCellOverFromExactStringMatch(mockTable, anyMatchString, 0, 0);
		EasyMock.verify(finder);
		EasyMock.verify(actions);
		Assert.assertNull(actual);
	}
	

	@Test
	public void getCellOverFromPartialStringMatchReturnsNullIfMatchIsEmptyTest() {
		WebElement anyElement = EasyMock.createMock(WebElement.class);
		String emptyExactMatchString = "";
		Assert.assertNull(utils.getCellOverFromPartialStringMatch(anyElement, emptyExactMatchString, 0, 0));
	}

	@Test
	public void getCellOverFromPartialStringMatchReturnsNullIfTableIsNullTest() {
		WebElement nullTable = null;
		String anyMatchString = "anyValidMatchString";
		Assert.assertNull(utils.getCellOverFromPartialStringMatch(nullTable, anyMatchString, 0, 0));
	}

	@Test
	public void getCellOverFromPartialStringMatchReturnsSuccessfulTest() {
		WebElement mockTable = EasyMock.createMock(WebElement.class);
		String anyMatchString = "anyValidMatchString";
		EasyMock.expect(finder.getSubElements(mockTable, By.xpath(".//tr"), 0)).andReturn(getMockAllRows());
		EasyMock.expect(finder.getSubElements(mockRow1, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1()).times(2);
		
		EasyMock.expect(actions.getText(mockCell_1_1)).andReturn("randomPreString " + anyMatchString + " randomPostString");
		
		EasyMock.replay(finder);
		EasyMock.replay(actions);
		WebElement expected = mockCell_1_2;
		WebElement actual = utils.getCellOverFromPartialStringMatch(mockTable, anyMatchString, 0, 1);
		EasyMock.verify(finder);
		EasyMock.verify(actions);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void getCellOverFromPartialStringMatchReturnsNullIfNoMatchTest() {
		WebElement mockTable = EasyMock.createMock(WebElement.class);
		String anyMatchString = "anyValidMatchString";
		EasyMock.expect(finder.getSubElements(mockTable, By.xpath(".//tr"), 0)).andReturn(getMockAllRows());
		EasyMock.expect(finder.getSubElements(mockRow1, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1());
		EasyMock.expect(finder.getSubElements(mockRow2, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow2());
		EasyMock.expect(finder.getSubElements(mockRow3, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow3());
		
		EasyMock.expect(actions.getText(mockCell_1_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_1_2)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_2_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_2_2)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_3_1)).andReturn("");
		EasyMock.expect(actions.getText(mockCell_3_2)).andReturn("");
		
		EasyMock.replay(finder);
		EasyMock.replay(actions);
		WebElement actual = utils.getCellOverFromPartialStringMatch(mockTable, anyMatchString, 0, 0);
		EasyMock.verify(finder);
		EasyMock.verify(actions);
		Assert.assertNull(actual);
	}


	@Test(expected=InvalidElementException.class)
	public void getCellOffsetsAwayFromCurrentCellThrowsErrorWhenRowIndexLessThanZeroTest() throws InvalidElementException{
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class);
		utils.getCellOffsetsAwayFromCurrentCell("matchString", -1, 0, getMockAllRows(), 0, 0);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}

	@Test(expected=InvalidElementException.class)
	public void getCellOffsetsAwayFromCurrentCellThrowsErrorWhenRowIndexGreaterThanRowSizeTest() throws InvalidElementException{
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class);
		utils.getCellOffsetsAwayFromCurrentCell("matchString", (getMockAllRows().size() + 1), 0, getMockAllRows(), 0, 0);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}


	@Test(expected=InvalidElementException.class)
	public void getCellOffsetsAwayFromCurrentCellThrowsErrorWhenColIndexLessThan0Test() throws InvalidElementException{
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class);
		EasyMock.expect(finder.getSubElements(getMockAllRows().get(0), By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1());
		utils.getCellOffsetsAwayFromCurrentCell("matchString", 0, -1, getMockAllRows(), 0, 0);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}

	@Test(expected=InvalidElementException.class)
	public void getCellOffsetsAwayFromCurrentCellThrowsErrorWhenColIndexGreaterThanAllCellsInRowTest() throws InvalidElementException{
		PowerMockito.mockStatic(ScreenshotUtility.class);
		PowerMockito.doNothing().when(ScreenshotUtility.class);
		EasyMock.expect(finder.getSubElements(getMockAllRows().get(0), By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1());
		EasyMock.replay(finder);
		utils.getCellOffsetsAwayFromCurrentCell("matchString", 0, (getMockElementsInRow1().size() + 1), getMockAllRows(), 0, 0);
		PowerMockito.verifyStatic(ScreenshotUtility.class, Mockito.times(1));
		ScreenshotUtility.takeScreenshot(Mockito.anyString(), Mockito.any(WebDriver.class));
	}

	@Test
	public void getCellOffsetsAwayFromCurrentCellSucceedsTest() throws InvalidElementException{
		EasyMock.expect(finder.getSubElements(mockRow1, By.xpath(".//td"), 0)).andReturn(getMockElementsInRow1());
		EasyMock.replay(finder);
		WebElement expected = mockCell_1_1;
		WebElement actual = utils.getCellOffsetsAwayFromCurrentCell("matchString", 0, 0, getMockAllRows(), 0, 0);
		Assert.assertEquals(expected, actual);
	}

	
	@SuppressWarnings("serial")
	public List<WebElement> getMockAllRows() {
		return new ArrayList<WebElement>() {{
		    add(mockRow1);
		    add(mockRow2);
		    add(mockRow3);
		}};
	}
	@SuppressWarnings("serial")
	public List<WebElement> getMockElementsInRow1() {
		return new ArrayList<WebElement>() {{
		    add(mockCell_1_1);
		    add(mockCell_1_2);
		}};
	}
	@SuppressWarnings("serial")
	public List<WebElement> getMockElementsInRow2() {
		return new ArrayList<WebElement>() {{
		    add(mockCell_2_1);
		    add(mockCell_2_2);
		}};
	}
	@SuppressWarnings("serial")
	public List<WebElement> getMockElementsInRow3() {
		return new ArrayList<WebElement>() {{
		    add(mockCell_3_1);
		    add(mockCell_3_2);
		}};
	}
	
}
