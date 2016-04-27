package com.lb.config.framework;

import java.util.Properties;

/**
 * @author liuzhenhua
 */
public class ApplicationConfig {

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
	private String connectStrings;
	/**
	 * 配置文件KV集合
	 */
	private Properties properties;

	public ApplicationConfig(String basePath, String applicationName,
			String applicationDescribe, String connectStrings,
			Properties properties) {
		this.basePath = basePath;
		this.applicationName = applicationName;
		this.applicationDescribe = applicationDescribe;
		this.connectStrings = connectStrings;
		this.properties = properties;
	}

	/**
	 * client application name
	 * 
	 * @return client application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * set applicationName
	 * 
	 * @param applicationName
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * server connect address
	 * 
	 * @return server connect address
	 */
	public String getConnectStrings() {
		return connectStrings;
	}

	/**
	 * set connect address
	 * 
	 * @param connectStrings
	 */
	public void setConnectStrings(String connectStrings) {
		this.connectStrings = connectStrings;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getApplicationDescribe() {
		return applicationDescribe;
	}

	public void setApplicationDescribe(String applicationDescribe) {
		this.applicationDescribe = applicationDescribe;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "ApplicationConfig [basePath=" + basePath + ", applicationName="
				+ applicationName + ", applicationDescribe="
				+ applicationDescribe + ", connectStrings=" + connectStrings
				+ ", properties=" + properties + "]";
	}

	
	
	
}
