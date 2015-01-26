package com.vincent.common;

import java.util.regex.Pattern;

public class ValidateUtil {
	
	private static String ATOM = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	public static final String REGEX_SFZMMC = "(\\B)|[A-M]";
	
	public static final String REGEX_EMAIL = 
			"^" + ATOM + "+(\\." + ATOM + "+)*@"
					+ DOMAIN
					+ "|"
					+ IP_DOMAIN
					+ ")$";
	
	public static final String REGXEX_USERNAME = "^[A-Za-z0-9]{1}[A-Za-z0-9_@\\.]{5,}$";
	public static final String REGEX_MOBILE = "^(([0\\+]?86)?[0 -]?1[34578]\\d{9})$";
	public static final String REGEX_TELEPHONE = "^((\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,8}(-\\d{1,4})?)$";
	
	private static final String REGEX_IP = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])" +
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])" +
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])" +
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static boolean validatePort(int port){
		if(port<0 || port>65535) return false;
		return true;
	}

	public static boolean validateIp(String ip){
		return Pattern.matches(REGEX_IP, ip);
	}
	
	public static boolean validateUsername(String username){
		return Pattern.matches(REGXEX_USERNAME, username);
	}
	
	public static boolean validateMobile(String mobile){
		return Pattern.matches(REGEX_MOBILE, mobile);
	}
	
	public static boolean validateTelephone(String telephone){
		return Pattern.matches(REGEX_TELEPHONE, telephone);
	}
	
	public static boolean validateEmail(String email){
		return Pattern.matches(REGEX_EMAIL, email);
	}
	
	public static boolean validCardType(String cardType){
		return Pattern.matches(REGEX_SFZMMC, cardType);
	}
}
