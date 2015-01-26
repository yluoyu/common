package com.vincent.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {

	public static <T>Collection<T> toCollection(T o){
		List<T> list = new ArrayList<T>();
		if(o != null){
			list.add(o);
		}
		
		return list;
	}
	
	public static String concat(Collection<String> collection, String delimiter){
		if(collection == null) return null;
		
		StringBuilder ret = new StringBuilder();
		for(String value : collection){
			if(ret.length() > 0){
				ret.append(delimiter);
			}
			ret.append(value);
		}
		
		return ret.toString();
	}
	
	public static boolean isNullorEmpty(Collection<?> collection){
		if(collection == null || collection.isEmpty()){
			return true;
		}
		
		return false;
	}
}
