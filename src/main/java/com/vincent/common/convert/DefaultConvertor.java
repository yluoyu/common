package com.vincent.common.convert;

public class DefaultConvertor implements Convertor<Object> {

	public String convert(Object o) {
		if(o == null) return "";
		return o.toString();
	}

}
