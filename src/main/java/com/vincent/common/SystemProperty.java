package com.vincent.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemProperty {
	private static final Log log = LogFactory.getLog(SystemProperty.class);
	private Properties propeties = null;
	
	public SystemProperty(File propFile){
		try{
			propeties = new Properties();
			
			if(propFile.exists()){
				if(propFile.isFile()){
					InputStream in = new FileInputStream(propFile);
					propeties = Util.loadProperties(in, true);
				}else{
					log.warn(Util.getFilePath(propFile) + " is not a file");
				}
			}
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public synchronized String getProperty(String key) throws IOException{
		return propeties.getProperty(key);
	}

}
