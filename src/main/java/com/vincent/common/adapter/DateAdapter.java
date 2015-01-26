package com.vincent.common.adapter;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.vincent.common.DateUtil;
import com.vincent.common.StringUtil;

public class DateAdapter extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String v) throws Exception {
		if(StringUtil.isNullOrEmpty(v)) return null;
		
		return DateUtil.parse(v, DateUtil.CLIENT_DATE_FORMAT);
	}

	@Override
	public String marshal(Date v) throws Exception {
		if(v==null) return null;
		
		return DateUtil.getDate(v);
	}

}
