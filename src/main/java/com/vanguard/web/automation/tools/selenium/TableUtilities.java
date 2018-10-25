package com.vanguard.web.automation.tools.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vanguard.web.selenium.exceptions.InvalidElementException;
import com.vanguard.web.selenium.utils.StringHelperUtil;




public class TableUtilities {
	
	private static final String SUB_TD_LOCATOR = ".//td";
	private static final String SUB_TR_LOCATOR = ".//tr";
	private static final String SUB_TABLE_LOCATOR = ".//table";
	private static final String TABLE_LOCATOR = "//table";
	private WebDriver driver;
	private SeleniumElementFinder finder;
	private SeleniumActionMethods actions;
	
	public TableUtilities(WebDriver driver, SeleniumElementFinder finder, SeleniumActionMethods actions) {
		super();
		this.driver = driver;
		this.finder = finder;
		this.actions = actions;
	}
	
	
	public List<WebElement> getAllTables() {
		List<WebElement> allTables = finder.getElements(By.xpath(TABLE_LOCATOR));
		return allTables;
	}

	public List<WebElement> getAllTablesWithinElement(By parentLocator) {
		return finder.getSubElements(parentLocator, By.xpath(SUB_TABLE_LOCATOR));
	}
	

	/**
	 * Returns the cell ([td] WebELement) the Offsets away from where we first found an exact match to the string.<br>
	 * Exact match is case-sensitive.<p>
	 * 
	 * For example, if we had table:<br>
	 * __________________________________________<br>
	 * Name   |   id   |   email    |  Address<br>
	 * Billy  |  2345  | B@test.com | 123 Main St<br>
	 * Jane   |  5467  | J@test.com | 456 Main St<br>
	 * Fred   |  8765  | F@test.com | 987 Main St<br>
	 * Mary   |  3847  | M@test.com | 123 Main St<br>
	 * ------------------------------------------<br>
	 * Params of (this table, "Billy", 0, 0) => [td] containing "Billy"<br>
	 * Params of (this table, "Billy", 2, 1) => [td] containing "8765"<br>
	 * Params of (this table, "F@test.com", -1, -2) => [td] containing "Jane"<br>
	 * Params of (this table, "456 Main St", 0, -3) => [td] containing "Jane"<p>
	 * 
	 * ERROR CAESES will all return null:<br>
	 * Params of (this table, "Bob", 1, 0) => null (because Bob is nowhere in the table)<br>
	 * Params of (this table, "Billy", 4, 1) => throw new InvalidElementException() (because there is no row 4 down from "Billy")<br>
	 * Params of (this table, "2345", 0, -2) => throw new InvalidElementException(]); (because there is no column 2 left of the id 2345)<br>
	 * Params of (null or an element with no <tr> children, {any}, {any}, {any}) => null (because table is invalid)<br>
	 * Params of (this table, null or "", {any}, {any}) => null (because exactMatchToLookFor is invalid)<p>
	 * 
	 * POTENTIAL UNEXPECTED CASE: matching to multiple will return based on the first match [from top to bottom, left to right]<br>
	 * Params of (this table, "123 Main St", 0, -3) => [td] containing "Billy". Note: NOT "Mary"<p>
	 * 
	 * @param table - The WebElement matching to the table you want to search [ex: By.xpath("//table")]<br>
	 * @param exactMatchToLookFor - The String that must exactly match one of the [td] Element's getText() commands<br>
	 * @param rowOffset - The offset from the match you want to return.  Positive numbers down.  Negative numbers up.<br>
	 * @param columnOffset - The offset from the match you want to return.  Positive numbers for columns over to right.  Negative for columns over to left.<br>
	 * @return - The [td] WebElement the offsets away from the match
	 */
	public WebElement getCellOverFromExactStringMatch(By tableLocator, String exactMatchToLookFor, int rowOffset, int columnOffset) throws InvalidElementException{
		return getCellOverFromExactStringMatch(finder.getElement(tableLocator), exactMatchToLookFor, rowOffset, columnOffset);
	}
	
	/**
	 * Returns the cell ([td] WebELement) the Offsets away from where we first found an exact match to the string.<br>
	 * Exact match is case-sensitive.<p>
	 * 
	 * For example, if we had table:<br>
	 * __________________________________________<br>
	 * Name   |   id   |   email    |  Address<br>
	 * Billy  |  2345  | B@test.com | 123 Main St<br>
	 * Jane   |  5467  | J@test.com | 456 Main St<br>
	 * Fred   |  8765  | F@test.com | 987 Main St<br>
	 * Mary   |  3847  | M@test.com | 123 Main St<br>
	 * ------------------------------------------<br>
	 * Params of (this table, "Billy", 0, 0) => [td] containing "Billy"<br>
	 * Params of (this table, "Billy", 2, 1) => [td] containing "8765"<br>
	 * Params of (this table, "F@test.com", -1, -2) => [td] containing "Jane"<br>
	 * Params of (this table, "456 Main St", 0, -3) => [td] containing "Jane"<p>
	 * 
	 * ERROR CAESES will all return null:<br>
	 * Params of (this table, "Bob", 1, 0) => null (because Bob is nowhere in the table)<br>
	 * Params of (this table, "Billy", 4, 1) => throw new InvalidElementException() (because there is no row 4 down from "Billy")<br>
	 * Params of (this table, "2345", 0, -2) => throw new InvalidElementException(]); (because there is no column 2 left of the id 2345)<br>
	 * Params of (null or an element with no <tr> children, {any}, {any}, {any}) => null (because table is invalid)<br>
	 * Params of (this table, null or "", {any}, {any}) => null (because exactMatchToLookFor is invalid)<p>
	 * 
	 * POTENTIAL UNEXPECTED CASE: matching to multiple will return based on the first match [from top to bottom, left to right]<br>
	 * Params of (this table, "123 Main St", 0, -3) => [td] containing "Billy". Note: NOT "Mary"<p>
	 * 
	 * @param table - The WebElement matching to the table you want to search [ex: By.xpath("//table")]<br>
	 * @param exactMatchToLookFor - The String that must exactly match one of the [td] Element's getText() commands<br>
	 * @param rowOffset - The offset from the match you want to return.  Positive numbers down.  Negative numbers up.<br>
	 * @param columnOffset - The offset from the match you want to return.  Positive numbers for columns over to right.  Negative for columns over to left.<br>
	 * @return - The [td] WebElement the offsets away from the match
	 */
	public WebElement getCellOverFromExactStringMatch(WebElement table, String exactMatchToLookFor, int rowOffset, int columnOffset) throws InvalidElementException{
		if(StringHelperUtil.isNullOrEmpty(exactMatchToLookFor) || table == null) {
			return null;
		}
		List<WebElement> allRows = finder.getSubElements(table, By.xpath(SUB_TR_LOCATOR), 0);
		for(int rowIndex = 0; rowIndex < allRows.size(); rowIndex ++) {
			WebElement rowElement = allRows.get(rowIndex);
			List<WebElement> allCellsInRow = finder.getSubElements(rowElement, By.xpath(SUB_TD_LOCATOR), 0);
			for(int columnIndex = 0; columnIndex < allCellsInRow.size(); columnIndex++) {
				WebElement cellElement = allCellsInRow.get(columnIndex);
				if(exactMatchToLookFor.equals(actions.getText(cellElement))) {
					return getCellOffsetsAwayFromCurrentCell(exactMatchToLookFor, rowOffset, columnOffset, allRows,
							rowIndex, columnIndex);
				}
			}
		}
		return null; //Exact match not found
	}


	/**
	 * Returns the cell ([td] WebELement) the Offsets away from where we first found a partial match to the string.<br>
	 * Partial match is NOT case-sensitive.<p>
	 * 
	 * For example, if we had table:<br>
	 * __________________________________________<br>
	 * Name   |   id   |   email    |  Address<br>
	 * Billy  |  2345  | B@test.com | 123 Main St<br>
	 * Jane   |  5467  | J@test.com | 456 Main St<br>
	 * Fred   |  8765  | F@test.com | 987 Main St<br>
	 * Mary   |  3847  | M@test.com | 123 Main St<br>
	 * ------------------------------------------<br>
	 * Params of (this table, "Billy", 0, 0) => [td] containing "Billy"<br>
	 * Params of (this table, "Billy", 2, 1) => [td] containing "8765"<br>
	 * Params of (this table, "F@test.com", -1, -2) => [td] containing "Jane"<br>
	 * Params of (this table, "456 Main St", 0, -3) => [td] containing "Jane"<p>
	 * 
	 * ERROR CAESES will all return null:<br>
	 * Params of (this table, "Bob", 1, 0) => null (because Bob is nowhere in the table)<br>
	 * Params of (this table, "Billy", 4, 1) => throw new InvalidElementException() (because there is no row 4 down from "Billy")<br>
	 * Params of (this table, "2345", 0, -2) => throw new InvalidElementException(]); (because there is no column 2 left of the id 2345)<br>
	 * Params of (null or an element with no <tr> children, {any}, {any}, {any}) => null (because table is invalid)<br>
	 * Params of (this table, null or "", {any}, {any}) => null (because exactMatchToLookFor is invalid)<p>
	 * 
	 * POTENTIAL UNEXPECTED CASE: matching to multiple will return based on the first match [from top to bottom, left to right]<br>
	 * Params of (this table, "123 Main St", 0, -3) => [td] containing "Billy". Note: NOT "Mary"<br>
	 * Params of (this table, "@test.com", 0, 0) => [td] containing "B@test.com".  (the first email, since they all partial match)<p>
	 * 
	 * @param table - The WebElement matching to the table you want to search [ex: By.xpath("//table")]<br>
	 * @param partialMatchToLookFor - The String that must be contained in one of the [td] Element's getText() commands<br>
	 * @param rowOffset - The offset from the match you want to return.  Positive numbers down.  Negative numbers up.<br>
	 * @param columnOffset - The offset from the match you want to return.  Positive numbers for columns over to right.  Negative for columns over to left.<br>
	 * @return - The [td] WebElement the offsets away from the match
	 */
	public WebElement getCellOverFromPartialStringMatch(By tableLocator, String partialMatchToLookFor, int rowOffset, int columnOffset) throws InvalidElementException{
		return getCellOverFromPartialStringMatch(finder.getElement(tableLocator), partialMatchToLookFor, rowOffset, columnOffset);
	}
		
	/**
	 * Returns the cell ([td] WebELement) the Offsets away from where we first found a partial match to the string.<br>
	 * Partial match is NOT case-sensitive.<p>
	 * 
	 * For example, if we had table:<br>
	 * __________________________________________<br>
	 * Name   |   id   |   email    |  Address<br>
	 * Billy  |  2345  | B@test.com | 123 Main St<br>
	 * Jane   |  5467  | J@test.com | 456 Main St<br>
	 * Fred   |  8765  | F@test.com | 987 Main St<br>
	 * Mary   |  3847  | M@test.com | 123 Main St<br>
	 * ------------------------------------------<br>
	 * Params of (this table, "Billy", 0, 0) => [td] containing "Billy"<br>
	 * Params of (this table, "Billy", 2, 1) => [td] containing "8765"<br>
	 * Params of (this table, "F@test.com", -1, -2) => [td] containing "Jane"<br>
	 * Params of (this table, "456 Main St", 0, -3) => [td] containing "Jane"<p>
	 * 
	 * ERROR CAESES will all return null:<br>
	 * Params of (this table, "Bob", 1, 0) => null (because Bob is nowhere in the table)<br>
	 * Params of (this table, "Billy", 4, 1) => throw new InvalidElementException() (because there is no row 4 down from "Billy")<br>
	 * Params of (this table, "2345", 0, -2) => throw new InvalidElementException(]); (because there is no column 2 left of the id 2345)<br>
	 * Params of (null or an element with no <tr> children, {any}, {any}, {any}) => null (because table is invalid)<br>
	 * Params of (this table, null or "", {any}, {any}) => null (because exactMatchToLookFor is invalid)<p>
	 * 
	 * POTENTIAL UNEXPECTED CASE: matching to multiple will return based on the first match [from top to bottom, left to right]<br>
	 * Params of (this table, "123 Main St", 0, -3) => [td] containing "Billy". Note: NOT "Mary"<br>
	 * Params of (this table, "@test.com", 0, 0) => [td] containing "B@test.com".  (the first email, since they all partial match)<p>
	 * 
	 * @param table - The WebElement matching to the table you want to search [ex: By.xpath("//table")]<br>
	 * @param partialMatchToLookFor - The String that must be contained in one of the [td] Element's getText() commands<br>
	 * @param rowOffset - The offset from the match you want to return.  Positive numbers down.  Negative numbers up.<br>
	 * @param columnOffset - The offset from the match you want to return.  Positive numbers for columns over to right.  Negative for columns over to left.<br>
	 * @return - The [td] WebElement the offsets away from the match
	 */
	public WebElement getCellOverFromPartialStringMatch(WebElement table, String partialMatchToLookFor, int rowOffset, int columnOffset) throws InvalidElementException{
		if(StringHelperUtil.isNullOrEmpty(partialMatchToLookFor) || table == null) {
			return null;
		}
		List<WebElement> allRows = finder.getSubElements(table, By.xpath(SUB_TR_LOCATOR), 0);
		for(int rowIndex = 0; rowIndex < allRows.size(); rowIndex ++) {
			WebElement rowElement = allRows.get(rowIndex);
			List<WebElement> allCellsInRow = finder.getSubElements(rowElement, By.xpath(SUB_TD_LOCATOR), 0);
			for(int columnIndex = 0; columnIndex < allCellsInRow.size(); columnIndex++) {
				WebElement cellElement = allCellsInRow.get(columnIndex);
				String curCellText = actions.getText(cellElement);
				if(!StringHelperUtil.isNullOrEmpty(curCellText)) {
					if(curCellText.toLowerCase().contains(partialMatchToLookFor.toLowerCase())) {
						return getCellOffsetsAwayFromCurrentCell(partialMatchToLookFor, rowOffset, columnOffset, allRows,
								rowIndex, columnIndex);
					}
				}
			}
		}
		return null; //Exact match not found
	}


	WebElement getCellOffsetsAwayFromCurrentCell(String matchToLookFor, int rowOffset, int columnOffset, 
			List<WebElement> allRows, int rowIndex, int columnIndex) throws InvalidElementException{
		int rowIndexToReturn = rowIndex + rowOffset;
		if(rowIndexToReturn < 0 || rowIndexToReturn > allRows.size()) {
			String message = "Invalid Row in getting cell from table.\n" + "When matching on: " + matchToLookFor + 
					", which was found in row: " + rowIndex + ", trying to use rowOffset:" + rowOffset + " is invalid";
			throw new InvalidElementException(message, driver);
		}
		WebElement rowToReturn = allRows.get(rowIndex + rowOffset);
		List<WebElement> allCellsInRowToReturn = finder.getSubElements(rowToReturn, By.xpath(SUB_TD_LOCATOR), 0);
		int columnIndexToReturn = columnIndex + columnOffset;
		if(columnIndexToReturn < 0 || columnIndexToReturn > allCellsInRowToReturn.size()) {
			String message = "Invalid Column in getting cell from table.\n" +
				"When matching on: " + matchToLookFor + ", which was found in row:" + rowIndex + 
				", in column: " + columnIndex + ", trying to use columnOffset:" + columnOffset + " is invalid";
			throw new InvalidElementException(message, driver);
		}
		WebElement cellToReturn = allCellsInRowToReturn.get(columnIndexToReturn);
		return cellToReturn;
	}


	
	
}
