## Selenium Automation Framework

Wrapper around Selenium-Java plus some helper utilities around using tables.

## Why was this library created?

The goal was to remove flakiness from large corporate Selenium test suites (as much as possible).
Utilizing this framework makes the tests easy to read and create, makes them maintainable, and able to run in a stable and repeatable manner.

## Other big benefits

1) Some of the most common errors like NoSuchElementException and StaleElementReferenceException should no longer appear in your selenium tests when using this framework!

2) Nearly all exceptions that get thrown from this framework will atuomatically take a screenshot to give the user more info in order to properly and easily debug failing tests.

3) All Selenium commands are encapsolated within this framework, so no individual tests (or Page Objects) should have any Selenium commands in them any longer (only calls to this framework).  This means when new Selenium versions come out, only changes to this framework are required to utilize the latest selenium version, with NO changes needed to any Page Object or Test code throughout your entire test suite.

4) One place to implement truly shared functionality like the TableHelpers can enhance the existing selenium-java functionality, and automatically give all people using this framework access to that new functionality.


5) NOTE: This is a future feature (not yet implemented): You have the ability to run custom validation rules on every command to help ensure good standards are followed.  Ex: Can not use any locator other than By.id(), or can not use By.xpath() with more than 3 "/"'s, etc.


## Using this framework
Simply add a maven dependency to your project
```
<dependency>
	<groupId>com.vanguard.cto</groupId>
	<artifactId>selenium-automation-framework.lib</artifactId>
	<version>1.0.0</version>
</dependency>
```

Create PageObject classes that extend SeleniumPageObject.
Create Test cases that extend SeleniumBaseTest
NOTE: You may want to create your own CorporateBsePage and/or CorporateBaseTest that all pages and tests respectively extend within your suite.
More details can be found in the blog post and/or video at the end of this ReadMe. 


## Framework Properties
There are a few key properties that will alter the way this framework behaves.  They can be set in the file: src/test/resources/seleniumFramework.properties
They are:
⋅⋅*failIfElementNotFound=true|false - true will fail a test as soon as it encounters an element it can't find when trying to perform any action on said element, false will log an error in the console for failing to complete the desired action on the element, but will then continue the test execution.  FRAMEWORK DEFAULT: false.
⋅⋅*geckoDriverLocation=/path/to/geckodriver/exe - Specify the location of your geckodriver executable.  This is required to utilize the SeleniumBaseTest which is configured to launch a Firefox browser at the beginning of each test, and close it at the end.  FRAMEWORK DEFAULT: None.  If this property or the entire file is missing, the SeleniumBaseTest class will not work as no Firefox browser will be able to be launched.
⋅⋅*SELENIUM_MAX_WAIT=60 - the maximum time in seconds you want any explicit wait anywhere in your suite to wait for.  FRAMEWORK DEFAULT: 90 seconds.


## Keys to the framework's success

Utilize Explicit waits on any/every command that might need to wait for something.  Then hiding the implementation of the relatively complicated explicit waits within the framework implementation.  This leaves the pageObjec and test code that's extremely easy to read and maintain.

Page Object Example:

	public class MyFormPage extends SeleniumBasePage{
		private static final By mainform = By.id("myForm");
		private static final By nameField = By.id("myForm:name");
		private static final By addressLine1Field = By.id("myForm:addr1");
		private static final By addressLine2Field = By.id("myForm:addr2");
		private static final By phoneField = By.id("myForm:phone");
		private static final By submitButton = By.id("myForm:submit");
		private static final By errorField = By.cssClass("highlight-error");
		public MyFormPage(WebDriver driver) {
			super(driver);
		}
		public boolean isLoaded() {
			return actions.isDisplayed(mainform, 5); //Wait up to 5 seconds for the mainform to display in the browser
		}
		public void fillOutForm(String name, String addr1, String addr2, String phone) {
			actions.type(name, nameField, 3); //Wait up to 3 seconds for the nameField to become enabled
			//Once the nameField is enabled, we know the other fields will be too, so we don't need to add waits for those type actions.
			actions.type(addr1, addressLine1Field);
			actions.type(addr2, addressLine2Field);
			actions.type(phone, phoneField);
		}
		public NextPage submitForm() {
			actions.click(submitButton, 2); //Wait up to 2 seconds for the submitButton to become enabled
			return new NextPage(driver);
		}
		public void submitInvalidForm() {
			actions.click(submitButton);
		}
		public boolean hasAnyFieldsCurrentlyInError() {
			List<WebElement> allErrorElements = finder.getElements(errorField, 3);
			return (allErrorElements.size() > 0);
		}
	}


Test Examples:

	@Test
	public void testValidFormSubmission() {
		MyFormPage myFormPage = ...//Get to the page somehow
		myFormPage.fillOutForm("Bob", "123 Main St", "Apt. 12", "123-4567");
		NextPage nextPage = myFormPage.submitForm();
		...//Continue Test
	}
	@Test
	public void testInvalidFormSubmission() {
		MyFormPage myFormPage = ...//Get to the page somehow
		myFormPage.fillOutForm("Bob", "123 Main St", "Apt. 12", "abc-defg");
		myFormPage.submitInvalidForm();
		Assert.assertTrue(myFormPage.hasAnyFieldsCurrentlyInError());
	}


## TableHelper Example

Let's pretend we have the following table on our page, where "EditLink" was actually a link to edit that person's info.
Note: Since we probably generate these EditLink's dynamically on our page, using ID's or other standard selenium locators may not work best:

	| First Name | Last Name    | Email         	| Link  |
	|:--------- |:------------- |:---------------- |:------:|
	| Fred	    | Jones         | FJones@test.com   |EditLink|
	| Mary      | Smith         | MSmith@test.com   |EditLink|
	| John      | Adams         | JAdams@test.com   |EditLink|
	| James     | Peters        | JPeters@test.com  |EditLink|
	| Pat       | Johnson       | PJohnson@test.com |EditLink|


We can utilize our TableHelper methods to get the the things we want.  Lets assume we have a locator or WebElement (these methods are overloaded to take either) called 'tableObject' representing the table above, and a PageObject already defined called 'myReadMePage'.


	WebElement firstEmailCell = myReadMePage.tableHelper.getCellOverFromExactStringMatch(tableObject, "Email", 1, 0);
	System.print.out("The first entry has an 'Email' of: " + myReadMePage.actions.getText(firstEmailCell);
	//Prints out "The first entry has an 'Email' of: FJones@test.com"
	OR
	//Click John Adams's Edit Link
	WebElement editLink = myReadMePage.tableHelper.getCellOverFromExactStringMatch(tableObject, "JAdams@test.com", 0, 1);
	myReadMePage.actions.click(editLink);
	OR
	//Who's First Name has "son" in their Last Name?
	WebElement firstNameCell = myReadMePage.tableHelper.getCellOverFromPartialStringMatch(tableObject, "son", 0, -1);
	System.out.println("The person's First Name who contains 'son' in their last name is: " + myReadMePage.actions.getText(firstNameCell);
	//Prints out "The person's First Name who contains 'son' in their last name is: Pat"
	

## SeleniumBaseTest.java
This class is included in this framework as an example.  Most users of this framework will likely want to customize their own BaseTest class.  The important things for a SeleniumBaseTest class to perform are:
1) Set up the desired environment at the beginning of each test, like launching the browser.  Note: The browser type of Firefox, IE, Chrome, Edge, or even RemoteDriver, etc. may be configured via settings or other customized code specific to your test suite.
2) Tear down the environment at the end of each test.
This helps to ensure your test cases are Autonomous, and not heavily dependent on each other.  If you have tests where you wish the environment would stay up for the next test, you're likely violating best practices and should look to refactor the tests to be more independent for better reliability.

## Learn more about how to use this framework

Blog Post: [Flakiness of Corporate Selenium Suites and how to get rid of it](http://www.ocpsoft.org/opensource/flakiness-of-corporate-selenium-suites-and-how-to-get-rid-of-it/)

Or watch this video recorded at SauceCon2016: [SAY GOODBYE TO THE "F" WORD - FLAKY NO MORE!](https://www.youtube.com/watch?v=2K2M7s_Ups0)