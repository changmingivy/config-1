package com.lb.config.framework.plugin.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lb.config.framework.ConfigFrameworkFactory;
import com.lb.config.framework.exception.ConfigException;
import com.lb.config.framework.plugin.PropertyLoaderConfigurer;

public class ContextPropertyLoaderConfigurer implements
		ServletContextListener,PropertyLoaderConfigurer {

	private static Logger log = Logger
			.getLogger(ContextPropertyLoaderConfigurer.class.getName());

	/**
	 * 应用集合总线路径
	 */
	private String basePath;
	/**
	 * 客户端应用名称
	 */
	private String applicationName;
	/**
	 * 客户端应用描述
	 */
	private String applicationDescribe;
	/**
	 * 服务器端链接字符串
	 */
	protected String connectStrings;
	/**
	 * 配置文件KV集合
	 */
	private Properties properties;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDescribe() {
		return applicationDescribe;
	}

	public void setApplicationDescribe(String applicationDescribe) {
		this.applicationDescribe = applicationDescribe;
	}

	public String getConnectStrings() {
		return connectStrings;
	}

	public void setConnectStrings(String connectStrings) {
		this.connectStrings = connectStrings;
	}

	public ContextPropertyLoaderConfigurer() {
		super();
	}

	public void contextInitialized(ServletContextEvent sce) {
		log.debug("contextInitialized");
		ConfigFrameworkFactory factory = new ConfigFrameworkFactory(basePath,
				applicationName, applicationDescribe, connectStrings,
				properties, this);
		try {
			factory.build();
		} catch (ConfigException e) {
			log.error("ConfigFrameworkFactory build ：", e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		log.debug("contextDestroyed");
	}

	public void process() {
		log.debug("ContextPropertyLoaderConfigurer process run");
	}
	
}
