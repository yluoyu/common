package com.vincent.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

	/**
	 * 
	 * @param s
	 * @return null if exception occured
	 */
	public static Integer getInteger(String s){
		return getInteger(s, null);
	}
	
	public static Integer getInteger(String s, Integer defaultValue){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static Integer getInteger(Integer i){
		return (i == null ? 0 : i);
	}
	
	public static Short getShort(Short i){
		return (i == null ? 0 : i);
	}
	
	public static Long getLong(Object number, Long defaultValue){
		if(number == null){
			return defaultValue;
		}else{
			return new BigDecimal(number.toString()).longValue();
		}
	}
	
	public static BigDecimal getBigDecimal(String s) {
		try{
			return new BigDecimal(s);
		}catch(Exception e){
			return null;
		}
	}
	
	public static Integer add(Integer i1, Integer i2){
		if(i1 == null){
			i1 = 0;
		}
		if(i2 == null){
			i2 = 0;
		}
		
		return i1+i2;
	}
	
	public static BigDecimal add(BigDecimal bd1, BigDecimal bd2){
		if(bd1 == null){
			bd1 = BigDecimal.ZERO;
		}
		if(bd2 == null){
			bd2 = BigDecimal.ZERO;
		}
		
		return bd1.add(bd2);
	}

	public static BigDecimal minus(BigDecimal bd1, BigDecimal bd2) {
		if(bd1 == null){
			bd1 = BigDecimal.ZERO;
		}
		if(bd2 == null){
			bd2 = BigDecimal.ZERO;
		}
		
		return bd1.subtract(bd2);
	}
	
	public static String leftPaddingZero(int number, int len){
		return StringUtil.leftPadding(number + "", '0', len);
	}
	
	public static String leftPaddingZero(long number, int len){
		return StringUtil.leftPadding(number + "", '0', len);
	}
	
	public static String formatMoney(double money){
		return new DecimalFormat("#.00").format(money);
	}
	
	public static BigDecimal formatMoney(BigDecimal money){
		return money.setScale(2, BigDecimal.ROUND_UP);
	}

	public static Integer[] toIntegerArray(int[] array) {
		if(array==null){
			return null;
		}
		
		Integer[] ret = new Integer[array.length];
		for(int i=0;i<array.length;i++){
			ret[i] = array[i];
		}
		
		return ret;
	}

	public static List<Integer> toIntegerList(int[] array) {
		if(array==null){
			return null;
		}
		
		List<Integer> ret = new ArrayList<Integer>();
		for(int i=0;i<array.length;i++){
			ret.add(array[i]);
		}
		
		return ret;
	}

	 public static boolean isPositiveNumber(String number){
		   String regex="^[1-9]+[0-9]*$";
		   Pattern p=Pattern.compile(regex);
		   Matcher m=p.matcher(number);
		   if(m.find()){
			   return true;
		   }else{
			   return false;
		   }
		  }
}
