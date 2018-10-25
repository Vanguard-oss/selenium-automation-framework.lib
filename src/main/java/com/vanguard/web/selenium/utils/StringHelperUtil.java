package com.vanguard.web.selenium.utils;

import java.util.ArrayList;
import java.util.List;

public class StringHelperUtil {
	
	public static final String DEFAULT_CAMELCASE_RESULT = "BlankInputToCameCaseFunction";
	
	//TODO: don't pass a boolean into a method
	public static String camelCase(String input, boolean shouldCapsFirstLetter) {
		boolean shouldCapsNextLetter = shouldCapsFirstLetter;
		char[] chars = input.toCharArray();
		List<String> returnChars = new ArrayList<String>();
		String nextLetter = "";
		for (char c : chars) {
			if(Character.isLetter(c)){
				if(shouldCapsNextLetter){
					shouldCapsNextLetter = false;
					nextLetter = String.valueOf(c).toUpperCase();
				} else {
					nextLetter = String.valueOf(c).toLowerCase();
				}
				returnChars.add(nextLetter);
			} else if(Character.isDigit(c)) {
				nextLetter = String.valueOf(c);
				returnChars.add(nextLetter);
			} else {
				//Not a letter or Number, skip this char and capitalize the next letter.
				shouldCapsNextLetter = true;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (String letter : returnChars) {
			sb.append(letter);
		}
		String returnVal = sb.toString();
		if(returnVal.length() == 0){
			return DEFAULT_CAMELCASE_RESULT;
		}
		return returnVal;
	}

	public static boolean isNullOrEmpty(String input){
		return (input == null || input.equals(""));	
	}

}
