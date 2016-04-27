package com.lb.config.framework.zk;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import com.lb.config.framework.exception.ConfigException;

public class PathChildrenCacheUtils {

	public static List<ChildData> list(PathChildrenCache cache) {
		if (cache.getCurrentData().size() == 0) {
			return null;
		}
		return cache.getCurrentData();
	}

	public static void remove(CuratorFramework client, String path,
			String child) throws ConfigException {
		String doRemovePath = ZKPaths.makePath(path, child);
		try {
			client.delete().forPath(doRemovePath);
		} catch (KeeperException.NoNodeException e) {
			// 忽略
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	public static void create(CuratorFramework client, String path,
			String child, String data) throws ConfigException {
		String childPath = ZKPaths.makePath(path, child);
		try {
			client.create().creatingParentContainersIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.forPath(childPath, data.getBytes());
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	public static void update(CuratorFramework client, String path,
			String child, String data) throws ConfigException {
		String childPath = ZKPaths.makePath(path, child);
		byte[] bytes = data.getBytes();
		try {
			client.setData().forPath(childPath, bytes);
		} catch (KeeperException.NoNodeException e) {
			try {
				client.create().creatingParentContainersIfNeeded()
						.withMode(CreateMode.PERSISTENT)
						.forPath(childPath, bytes);
			} catch (Exception e1) {
				throw new ConfigException(e);
			}
		} catch (Exception e) {
			throw new ConfigException(e);
		}

	}
}
