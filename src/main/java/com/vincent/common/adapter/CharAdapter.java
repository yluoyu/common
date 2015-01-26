package com.vincent.common.adapter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CharAdapter extends XmlAdapter<String, Character> {

	@Override
	public String marshal(Character v) throws Exception {
		return v.toString();
	}

	@Override
	public Character unmarshal(String v) throws Exception {
		if (v.length() > 0)
			return v.charAt(0);
		else
			return null;
	}

}