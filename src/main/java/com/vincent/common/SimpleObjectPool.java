package com.vincent.common;

import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleObjectPool {
	
	public static final SimpleObjectPool getInstance() {
		return INSTANCE;
	}
	
	public void putObject(String group, String name, Object value) {
		ConcurrentHashMap<String, ValueWrapper> map = null;
		if (this.groups.containsKey(group)) {
			map = this.groups.get(group);
		} else {
			map = new ConcurrentHashMap<String, ValueWrapper>();
			this.groups.put(group, map);
		}
		map.put(name, new ValueWrapper(value, new Date()));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T getObject(String group, String name) {
		T value = null;
		if (this.groups.containsKey(group)) {
			ConcurrentHashMap<String, ValueWrapper> map = this.groups.get(group);
			if (map.containsKey(name)) {
				ValueWrapper wrapper = map.get(name);
				if (wrapper != null) {
					value = (T) wrapper.getValue();
					wrapper.setTime(new Date());
				}
			}
		}
		return value;
	}
	
	private final class ValueWrapper {
		
		private static final long period = 1000 * 60 * 5;
		
		private Object value;
		
		private long timestamp;

		public ValueWrapper(Object value, Date time) {
			this.value = value;
			this.timestamp = time.getTime();
		}

		public Object getValue() {
			return value;
		}

		public long getTimeStamp() {
			return timestamp;
		}

		public void setTime(Date time) {
			this.timestamp = time.getTime() + period;
		}
		
	}
	
	private ConcurrentHashMap<String, ConcurrentHashMap<String, ValueWrapper>> groups = new ConcurrentHashMap<String, ConcurrentHashMap<String, ValueWrapper>>();
	
	private static final SimpleObjectPool INSTANCE = new SimpleObjectPool();
	
	private static final long interval = 1000 * 60 * 5;
	
	private SimpleObjectPool() {
		Thread thread = new Thread("") {

			@Override
			public void run() {
				while (true) {
					try {
						long now = new Date().getTime();
						SimpleObjectPool thisPool = SimpleObjectPool.this;
						Enumeration<ConcurrentHashMap<String, ValueWrapper>> enumeration = thisPool.groups.elements();
						while (enumeration.hasMoreElements()) {
							ConcurrentHashMap<String, ValueWrapper> map = enumeration.nextElement();
							Iterator<Entry<String, ValueWrapper>> it = map.entrySet().iterator();
							while (it.hasNext()) {
								Entry<String, ValueWrapper> entry = it.next();
								if (entry.getValue().getTimeStamp() > now) {
									it.remove();
									map.remove(entry.getKey());
								}
							}
						}
						Thread.sleep(interval);
					} catch (InterruptedException e) {
					}
				}
			}
			
		};
		thread.start();
	}

}
