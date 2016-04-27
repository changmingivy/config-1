package com.lb.config.framework;

import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.lb.config.framework.exception.ConfigException;
import com.lb.config.framework.listener.ChildernNodesListener;
import com.lb.config.framework.plugin.PropertyLoaderConfigurer;
import com.lb.config.framework.zk.CreateZkClient;
import com.lb.config.framework.zk.PropertyUtils;

public class ConfigFrameworkFactory {

	private static Logger log = Logger.getLogger(ConfigFrameworkFactory.class
			.getName());

	private static int CONNECTION_TIMEOUT_MS = 3000;
	private static int SESSION_TIMEOUT_MS = 1000;
	private static int SLEEP_TIME_MS = 1000;
	private static int MAX_RETRIES = 3;

	private ApplicationConfig config;
	private PropertyLoaderConfigurer configurer;

	private CuratorFramework client = null;
	private PathChildrenCache cache = null;

	public ConfigFrameworkFactory(String basePath, String applicationName,
			String applicationDescribe, String connectStrings,
			Properties properties, PropertyLoaderConfigurer configurer) {
		this.config = new ApplicationConfig(basePath, applicationName,
				applicationDescribe, connectStrings, properties);
		this.configurer = configurer;
	}

	public void build() throws ConfigException {
		// 验证配置是否正确
		this.checkConfig(config);
		// 构建应用路径
		String path = ZKPaths.PATH_SEPARATOR + config.getBasePath()
				+ ZKPaths.PATH_SEPARATOR + config.getApplicationName();
		try {
			// 建立客户端
			this.client = CreateZkClient.createWithOptions(config
					.getConnectStrings(), new ExponentialBackoffRetry(
					SLEEP_TIME_MS, MAX_RETRIES), CONNECTION_TIMEOUT_MS,
					SESSION_TIMEOUT_MS);
			// 开启客户端链接
			this.client.start();
			// 检测节点是否存在
			Stat stat = client.checkExists().forPath(path);
			if (stat == null) {
				// 创建应用节点
				client.create().creatingParentsIfNeeded()
						.withMode(CreateMode.PERSISTENT)
						.forPath(path, config.getApplicationName().getBytes());
			}
			this.cache = new PathChildrenCache(client, path, true);
			// 开启监听缓存
			cache.start();
		} catch (Exception e) {
			log.error("config connect zk init error", e);
			throw new ConfigException(e);
		}

		if (log.isDebugEnabled()) {
			log.debug("构建监听节点路径：" + path);
		}
		// 应用启动自动增加节点数据
		PropertyUtils.addNodes(client, config.getProperties(), path);
		// 应用启动自动增加节点监听
		new ChildernNodesListener(cache, configurer);

	}

	public void checkConfig(ApplicationConfig config) throws ConfigException {
		if (config == null) {
			throw new ConfigException("application config is not null");
		}
		if (config.getBasePath() == null || config.getBasePath().equals("")) {
			throw new ConfigException("application config basePath is not null");
		}
		if (config.getApplicationName() == null
				|| config.getApplicationName().equals("")) {
			throw new ConfigException(
					"application config applicationName is not null");
		}
		if (config.getConnectStrings() == null
				|| config.getConnectStrings().equals("")) {
			throw new ConfigException(
					"application config connectStrings is not null");
		}
	}

}