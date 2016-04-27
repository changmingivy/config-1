package com.lb.config.framework.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheFactory {

	public static Map<String, String> CACHE = new ConcurrentHashMap<String, String>();
	
	public void putCacheMap(String k, String v){
		CACHE.put(k, v);
	}
	
	public String getCacheValue(String k){
		return CACHE.get(k);
	}
	
	
}
