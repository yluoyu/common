package com.vincent.common.convert;

import com.vincent.common.DateUtil;


public class SecondConvertor implements Convertor<Number> {

	public String convert(Number o) {
		return DateUtil.formatSecondsToHour(o.longValue());
	}

}
