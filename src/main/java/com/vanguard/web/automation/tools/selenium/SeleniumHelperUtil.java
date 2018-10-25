package com.vanguard.web.automation.tools.selenium;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;

/**
 * This class should only be used by the framework itself.
 * It's used for the updateXPathIfNeededForSubELements method.
 * 
 */
class SeleniumHelperUtil {
	
	public static enum BY_PREFIX {
		CLASS_NAME("By.className: "),
		CSS_SELECTOR("By.CssSelector: "),
		ID("By.id: "),
		LINK_TEXT("By.linkText: "),
		NAME("By.name: "),
		PARTIAL_LINK_TEXT("By.PartialLinkText: "),
		TAG_NAME("By.TagName: "),
		XPATH("By.xpath: "); 
		
		private String description;
		BY_PREFIX(String description){
			this.description = description;
		}
		public String description(){
			return description;
		}
	}
	
	/**
	 * Most people always start an xpath with "//" even for subElements.  This framework is nice and will automatically correct
	 * that xpath for subElements to ".//" meaning anything starting with the parent (instead of anything starting at root for a subElement).
	 * @param subLocation
	 * @return
	 */
	public static By updateXPathIfNeededForSubElements(By subLocation) {
		if(subLocation instanceof ByXPath){
			String xpathExpr = getByExpression(subLocation);
			if(xpathExpr.startsWith("//")){
				subLocation = By.xpath("." + xpathExpr);
			}
		}
		return subLocation;
	}
	
	public static String getByExpression(By location){
		String byExpression = "";
		if(location instanceof ByClassName){
			byExpression = location.toString().substring(BY_PREFIX.CLASS_NAME.description.length());
		}
		else if(location instanceof ByCssSelector){
			byExpression = location.toString().substring(BY_PREFIX.CSS_SELECTOR.description.length());
		}
		else if(location instanceof ById){
			byExpression = location.toString().substring(BY_PREFIX.ID.description.length());
		}
		else if(location instanceof ByLinkText){
			byExpression = location.toString().substring(BY_PREFIX.LINK_TEXT.description.length());
		}
		else if(location instanceof ByName){
			byExpression = location.toString().substring(BY_PREFIX.NAME.description.length());
		}
		else if(location instanceof ByPartialLinkText){
			byExpression = location.toString().substring(BY_PREFIX.PARTIAL_LINK_TEXT.description.length());
		}
		else if(location instanceof ByTagName){
			byExpression = location.toString().substring(BY_PREFIX.TAG_NAME.description.length());
		}
		else if(location instanceof ByXPath){
			byExpression = location.toString().substring(BY_PREFIX.XPATH.description.length());
		}
		return byExpression;
	}
	
	public static BY_PREFIX getByPrefix(By location){
		BY_PREFIX byPrefix = null;
		if(location instanceof ByClassName){
			byPrefix = BY_PREFIX.CLASS_NAME;
		}
		else if(location instanceof ByCssSelector){
			byPrefix = BY_PREFIX.CSS_SELECTOR;
		}
		else if(location instanceof ById){
			byPrefix = BY_PREFIX.ID;
		}
		else if(location instanceof ByLinkText){
			byPrefix = BY_PREFIX.LINK_TEXT;
		}
		else if(location instanceof ByName){
			byPrefix = BY_PREFIX.NAME;
		}
		else if(location instanceof ByPartialLinkText){
			byPrefix = BY_PREFIX.PARTIAL_LINK_TEXT;
		}
		else if(location instanceof ByTagName){
			byPrefix = BY_PREFIX.TAG_NAME;
		}
		else if(location instanceof ByXPath){
			byPrefix = BY_PREFIX.XPATH;
		}
		return byPrefix;
	}

	public static By getByLocation(BY_PREFIX prefix, String expression){
		switch (prefix) {
		case CLASS_NAME:
			return By.className(expression);
		case CSS_SELECTOR:
			return By.cssSelector(expression);
		case ID:
			return By.id(expression);
		case LINK_TEXT:
			return By.linkText(expression);
		case NAME:
			return By.name(expression);
		case PARTIAL_LINK_TEXT:
			return By.partialLinkText(expression);
		case TAG_NAME:
			return By.tagName(expression);
		case XPATH:
			return By.xpath(expression);
		default:
			Logger.getAnonymousLogger().severe("Could not determine By locator based on prefix of: " + prefix);
			return null;
		}
	}
}
