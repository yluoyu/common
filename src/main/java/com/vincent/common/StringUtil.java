package com.vincent.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	private static String SITE_KKCX = "(A[1-3])|(B[1-2])|(C[1-5])|(D|E|F|M|N|P)";
	
	public static final boolean in(String tocheck, String... values){
		if(tocheck==null || values==null || values.length==0) return false;
		for(String value : values){
			if(value.equals(tocheck)){
				return true;
			}
		}
		return false;
	}

	public static final boolean isNullOrEmpty(String s) {
		return isNullOrEmpty(s, true);
	}

	public static final boolean isNullOrEmpty(String s, boolean doTrim) {
		if (s == null)
			return true;
		
		if (doTrim){
			//过滤中文全角，及半角空格
			s = s.replaceAll("　| ", "");
		}
			
		if (s.length() == 0)
			return true;
		return false;
	}

	public static String trimString(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}

	public static String convertToLogString(String s) {
		if (s == null) {
			return "";
		} else {
			if (s.length() < 50) {
				return s;
			} else {
				return s.substring(0, 50) + "...";
			}
		}
	}

	public static String getStringFromEmpty(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		} else {
			return s;
		}
	}

	public static String joinStringWithEmpty(String splitter, String... s) {
		StringBuilder sBuilder = new StringBuilder();
		if (s.length > 0) {
			for (String ele : s) {
				sBuilder = sBuilder.append(getStringFromEmpty(ele));
				if(splitter!=null){
					sBuilder = sBuilder.append(splitter);
				}
			}
			String toString = sBuilder.toString();
			if(splitter!=null){
				int index = toString.lastIndexOf(splitter);
				if(index != -1){
					toString = toString.substring(0, index);
				}
			}
			return toString;
		} else {
			return null;
		}

	}
	/**
	 * "yyyy-MM-dd HH:mm:SS"
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String formateDateString(Date date, String formate) {
		SimpleDateFormat format = new SimpleDateFormat(formate);
		return format.format(date);
	}
	
	public static String leftTrim(String s, char c){
		if(s == null) return null;
		
		int index = -1;
		int len = s.length();
		for(int i=0;i<len;i++){
			if(s.charAt(i) == c){
				index = i;
			}else{
				break;
			}
		}
		
		if(len == index+1){
			return "";
		}else{
			return s.substring(index+1);
		}
	}
	
	public static String leftPadding(String s, char c, int length){
		if(s == null){
			s = "";
		}
		StringBuilder ret = new StringBuilder();
		for(int i=0, n=(length-s.length());i<n;i++){
			ret.append(c);
		}
		ret.append(s);
		return ret.toString();
	}
	
	public static String file2String(File file, String encoding) { 
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		try {
			if (encoding == null || "".equals(encoding.trim())) {
				reader = new InputStreamReader(new FileInputStream(file),
						encoding);
			} else {
				reader = new InputStreamReader(new FileInputStream(file));
			}
			char[] buffer = new char[4096];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return writer.toString();
	}
	 
	public static boolean string2File(String text, File distFile) { 
		if (!distFile.getParentFile().exists())
			distFile.getParentFile().mkdirs();
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean flag = true;
		try {
			br = new BufferedReader(new StringReader(text));
			bw = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024 * 64];
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			flag = false;
		}
		return flag;
	} 
	
	public static String nullToEmpty(Object s){
		return s==null?"":s.toString();
	}

	public static String capitalize(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}
	
	/**option map: <value, text>
	 * @param selected TODO*/
	public static String getOptionWithValueandText(Map<String, String> maps, String selected){
		if(maps == null || maps.size() ==0){
			return "<option></option>";
		}
		StringBuilder sbuilder = new StringBuilder();
		Set<String> keysSet = maps.keySet();
		Iterator<String> iterator = keysSet.iterator();
		while(iterator.hasNext()) {
			String value = iterator.next();
			String text = maps.get(value);
			if (value.equalsIgnoreCase(selected)) {
				sbuilder.append("<option name=\"").append(value).append("\" selected>").append(text).append("</option>");
			} else {
				sbuilder.append("<option name=\"").append(value).append("\">").append(text).append("</option>");
			}
		}
		return sbuilder.toString();
	}
	
	/**
	 * 手机或邮箱地址 替换为***
	 */
	
	public static String stringRepaceWithStar(String phone){
		int i = phone.length();
		StringBuffer sb = new StringBuffer();
		for(int j=0 ;j<i;j++){
			if(j<i-4 && j>i-9){
				sb.append("*");
			}else{
				sb.append(phone.charAt(j));
			}
		}
		return sb.toString();
	}
	/**
	 * 比较数组一中是否含有不属于数组二的元素
	 */
	public static boolean hasElementNotInArray(String[] array, String[] desArray){
		if(array == null || array.length ==0 || desArray == null || desArray.length == 0){
			return false;
		}
		List<String> sArray = new ArrayList<String>();
		sArray.addAll(Arrays.asList(array));
		List<String> dArray = new ArrayList<String>();
		dArray.addAll(Arrays.asList(desArray));
		try {
			sArray.removeAll(dArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sArray.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 考场车型更改为逗号分隔
	 */
	public static String getExamSiteKkcx(String kkcx){
		if(StringUtil.isNullOrEmpty(kkcx) || kkcx.indexOf(",")!=-1){
			return kkcx;
		}
		Pattern pattern = Pattern.compile(SITE_KKCX);
		Matcher match = pattern.matcher(kkcx);
		StringBuffer sb = new StringBuffer();
		while(match.find()){
			if(!sb.toString().equals("")){
				sb.append(",");
			}
			sb.append(match.group());
		}
		return sb.toString();
	}
	
}
