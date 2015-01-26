package com.vincent.common;

import java.util.Date;
import java.util.HashMap;

public class CheckUtil {
	
	public static void main(String[] args){
		System.out.println(checkC8Sn("3010312000015"));
	}
	
	public static boolean checkC8Sn(String s){
		if(s==null || s.length()!=13) return false;
		 
		Integer month = NumberUtil.getInteger(StringUtil.leftTrim(s.substring(3, 5), '0'));
		Integer year = NumberUtil.getInteger(StringUtil.leftTrim(s.substring(5, 7), '0'));
		Integer curYear = NumberUtil.getInteger(StringUtil.leftTrim(DateUtil.getDateTime(new Date(), "yy"), '0'));
		if(month == null || year==null || month<1 || month>12 || year<10 || year>curYear){
			return false;
		}
		
		int sum = 0;
		for(int i=0;i<12;i++){
			Integer tmp = NumberUtil.getInteger(s.charAt(i)+"");
			if(tmp == null) return false;
			
			if(i%2 == 0){
				sum += tmp * 1;
			}else{
				sum += tmp * 3;
			}
		}
		
		Integer checkNum = NumberUtil.getInteger(s.charAt(12)+"");
		if(checkNum!=null && (10-sum%10)%10 ==checkNum){
			return true;
		}
		
		return false;
	}

	public static boolean checkPhone(String s){
		return ValidateUtil.validateTelephone(s);
	}
	
	public static boolean checkMobile(String s){
		return ValidateUtil.validateMobile(s);
	}
	
	public static boolean checkVIN(String vin) {
		return checkVIN(vin, false);
	}
	
	public static boolean checkVIN(String vin, boolean checkLen) {
		vin = vin.toUpperCase();
		if (vin.trim().length() != 17) {
			if(checkLen == false) {
				return true;
			}else {
				return false;
			}
		}
		
		String number = vin.substring(11);
		try {
			Long.parseLong(number);
		} catch (Exception e) {
			return false;
		}
		
		if (vin.indexOf('I') >= 0 || vin.indexOf('O') >= 0 || vin.indexOf('Q') >= 0) {
			return false;
		}
		
		int value = 0;
		for (int idx = 0; idx < 17; idx++) {
			char c = vin.charAt(idx);
			if (!characterValue.containsKey(c)) {
				return false;
			}
			value += (characterValue.get(c).intValue() * weightValue[idx]);
		}
		char jyw = (char) ('0' + (value % 11));
		if ((value % 11) == 10) {
			jyw = 'X';
		}
		if (jyw != vin.charAt(8)) {
			return false;
		}
		
		return true;
	}
	
	private static HashMap<Character, Integer> characterValue = new HashMap<Character, Integer>();
	private static int[] weightValue = new int[17];
	
	static {
		characterValue.put('0', 0);
		characterValue.put('1', 1);
		characterValue.put('2', 2);
		characterValue.put('3', 3);
		characterValue.put('4', 4);
		characterValue.put('5', 5);
		characterValue.put('6', 6);
		characterValue.put('7', 7);
		characterValue.put('8', 8);
		characterValue.put('9', 9);
		characterValue.put('A', 1);
		characterValue.put('B', 2);
		characterValue.put('C', 3);
		characterValue.put('D', 4);
		characterValue.put('E', 5);
		characterValue.put('F', 6);
		characterValue.put('G', 7);
		characterValue.put('H', 8);
		characterValue.put('J', 1);
		characterValue.put('K', 2);
		characterValue.put('L', 3);
		characterValue.put('M', 4);
		characterValue.put('N', 5);
		characterValue.put('P', 7);
		characterValue.put('R', 9);
		characterValue.put('S', 2);
		characterValue.put('T', 3);
		characterValue.put('U', 4);
		characterValue.put('V', 5);
		characterValue.put('W', 6);
		characterValue.put('X', 7);
		characterValue.put('Y', 8);
		characterValue.put('Z', 9);

		weightValue[0] = 8;
		weightValue[1] = 7;
		weightValue[2] = 6;
		weightValue[3] = 5;
		weightValue[4] = 4;
		weightValue[5] = 3;
		weightValue[6] = 2;
		weightValue[7] = 10;
		weightValue[8] = 0;
		weightValue[9] = 9;
		weightValue[10] = 8;
		weightValue[11] = 7;
		weightValue[12] = 6;
		weightValue[13] = 5;
		weightValue[14] = 4;
		weightValue[15] = 3;
		weightValue[16] = 2;
	}
	
}
