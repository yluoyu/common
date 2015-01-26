package com.vincent.common.convert;

import java.sql.Date;

public interface Convertor<T> {
	
	public String convert(T o);
	
	public class ConvertorFactory {
		
		@SuppressWarnings("rawtypes")
		public static Class<? extends Convertor> getConvertor(Class<?> type){
			if(Date.class.isAssignableFrom(type)){
				return DateConvertor.class;
			}else if(java.util.Date.class.isAssignableFrom(type)){
				return TimestampConvertor.class;
			}else{
				return DefaultConvertor.class;
			}
		}
		
	}
}
