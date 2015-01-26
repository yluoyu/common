package com.vincent.common.log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	
	private Log log = null;
	
	public Logger(Class<?> clazz){
		log = LogFactory.getLog(clazz);
	}
	
	public void debug(Object message){
		debug(message, null);
	}
	
	public void debug(Object message, Throwable t){
		if(log.isDebugEnabled()){
			log.debug(message, t);
		}
	}
	
	public void info(Object message){
		info(message, null);
	}
	
	public void info(Object message, Throwable t){
		if(log.isInfoEnabled()){
			log.info(message, t);
		}
	}
	
	public void warn(Object message){
		warn(message, null);
	}
	
	public void warn(Object message, Throwable t){
		if(log.isWarnEnabled()){
			log.warn(message, t);
		}
	}
	
	public void error(Object message){
		error(message, null);
	}
	
	public void error(Object message, Throwable t){
		if(log.isErrorEnabled()){
			log.error(message, t);
		}
	}
	
	public void fetal(Object message){
		fetal(message, null);
	}
	
	public void fetal(Object message, Throwable t){
		if(log.isFatalEnabled()){
			log.fatal(message, t);
		}
	}
}
