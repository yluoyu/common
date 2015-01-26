package com.vincent.common.convert;

import java.util.Date;

import com.vincent.common.DateUtil;


public class DateConvertor implements Convertor<Date> {

	public String convert(Date o) {
		return DateUtil.getDate(o);
	}

}
