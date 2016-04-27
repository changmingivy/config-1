package com.lb.config.framework.zk;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.utils.ZKPaths;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import com.lb.config.framework.cache.CacheFactory;
import com.lb.config.framework.exception.ConfigException;

public class PropertyUtils {

	private static Logger log = Logger.getLogger(PropertyUtils.class.getName());

	public static void addNodes(CuratorFramework client, Properties p,
			String path) throws ConfigException {
		try {
			Enumeration<?> propertyNames = p.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String k = (String) propertyNames.nextElement();
				String v = p.getProperty(k);
				String pt = path + ZKPaths.PATH_SEPARATOR + k;
				Stat stat = client.checkExists().forPath(pt);
				if (stat == null) {
					PathChildrenCacheUtils.create(client, path, k, v);
					log.debug("zk node add key " + k + ", value = " + v);
				}
			}
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	public static void loadCache(PathChildrenCache cache)
			throws ConfigException {
		List<ChildData> list = PathChildrenCacheUtils.list(cache);
		if (list == null) {
			return;
		}
		for (ChildData data : list) {
			log.debug("zk add to cache key " + ZKPaths.getNodeFromPath(data.getPath()) + ", value = " + new String(data.getData()));
			CacheFactory.CACHE.put(ZKPaths.getNodeFromPath(data.getPath()), new String(data.getData()));
		}
	}

}
