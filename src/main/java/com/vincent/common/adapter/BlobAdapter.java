package com.vincent.common.adapter;

import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.security.crypto.codec.Base64;

public class BlobAdapter extends XmlAdapter<String, Blob> {

	@Override
	public Blob unmarshal(String v) throws Exception {
		return  new SerialBlob(Base64.decode(v.getBytes()));
	}

	@Override
	public String marshal(Blob v) throws Exception {
		return new String(Base64.encode(v.getBytes(0, (int)v.length())));
	}

}
