package com.lb.config.framework.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.log4j.Logger;

import com.lb.config.framework.cache.CacheFactory;
import com.lb.config.framework.plugin.PropertyLoaderConfigurer;

public class ChildernNodesListener {
	private static Logger log = Logger.getLogger(ChildernNodesListener.class
			.getName());

	private PathChildrenCache cache;

	private PropertyLoaderConfigurer configurer;

	public PathChildrenCache getCache() {
		return cache;
	}

	public void setCache(PathChildrenCache cache) {
		this.cache = cache;
	}

	public PropertyLoaderConfigurer getConfigurer() {
		return configurer;
	}

	public void setConfigurer(PropertyLoaderConfigurer configurer) {
		this.configurer = configurer;
	}

	public ChildernNodesListener(PathChildrenCache cache, PropertyLoaderConfigurer configurer) {
		this.cache = cache;
		this.configurer = configurer;
		addListener(cache, configurer);
	}

	public void addListener(PathChildrenCache cache, final PropertyLoaderConfigurer configurer) {
		PathChildrenCacheListener listener = new PathChildrenCacheListener() {
			@SuppressWarnings("incomplete-switch")
			public void childEvent(CuratorFramework client,
					PathChildrenCacheEvent event) throws Exception {
				String k = ZKPaths.getNodeFromPath(event.getData().getPath());
				String v = new String(event.getData().getData());
				switch (event.getType()) {
				case CHILD_ADDED: {
					CacheFactory.CACHE.put(k, v);
					log.debug("listener Node added in cache : " + k + ", value = " + v);
					break;
				}

				case CHILD_UPDATED: {
					CacheFactory.CACHE.put(k, v);
					configurer.process();
					log.debug("listener Node updated in cache : " + k + ", value = " + v);
					break;
				}

				case CHILD_REMOVED: {
					CacheFactory.CACHE.remove(k);
					log.debug("listener Node removed in cache : " + k + ", value = " + v);
					break;
				}
				}
			}
		};
		cache.getListenable().addListener(listener);
	}

}
