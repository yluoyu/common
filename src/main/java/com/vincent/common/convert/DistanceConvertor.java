package com.vincent.common.convert;

import java.text.NumberFormat;

public class DistanceConvertor implements Convertor<Number> {

	public String convert(Number o) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		return format.format(o.doubleValue() / 1000).replaceAll("^-(?=0(.0*)?$)", "");
	}

}
