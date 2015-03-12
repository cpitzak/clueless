package com.jhu.clueless.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines a Validator.
 *
 * @author Clint Pitzak
 *
 */
public class Validator {

	private static final String PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * Check if the Ip Address is Valid.
	 *
	 * @param ipAddress the ip address to check
	 *
	 * @return true if valid, false if not
	 */
	public static boolean isIpAddressValid(String ipAddress) {
		if (ipAddress ==  null) {
			return false;
		}
		if (ipAddress.equals("localhost")) {
			return true;
		}
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

}
