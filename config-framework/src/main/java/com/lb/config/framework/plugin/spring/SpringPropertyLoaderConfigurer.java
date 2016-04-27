package com.lb.config.framework.plugin.spring;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.lb.config.framework.ConfigFrameworkFactory;
import com.lb.config.framework.exception.ConfigException;
import com.lb.config.framework.plugin.PropertyLoaderConfigurer;

public class SpringPropertyLoaderConfigurer extends
		PropertyPlaceholderConfigurer implements PropertyLoaderConfigurer{
	
	private static Logger log = Logger.getLogger(SpringPropertyLoaderConfigurer.class.getName());

	private String basePath;
	private String applicationName;
	private String applicationDescribe;
	private String connectStrings;
	
	private ConfigurableListableBeanFactory beanFactory;
	
	public ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setConnectStrings(String connectStrings) {
		this.connectStrings = connectStrings;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setApplicationDescribe(String applicationDescribe) {
		this.applicationDescribe = applicationDescribe;
	}

	public SpringPropertyLoaderConfigurer() {
		super();
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		super.postProcessBeanFactory(beanFactory);
        try {
			Properties mergedProps = mergeProperties();
			// Convert the merged properties, if necessary.
			convertProperties(mergedProps);
			
			// Let the subclass process the properties.
			processProperties(beanFactory, mergedProps);
			
			this.beanFactory = beanFactory;
			
			ConfigFrameworkFactory factory = new ConfigFrameworkFactory(
					basePath, applicationName, applicationDescribe,
					connectStrings, mergedProps, this);
			try {
				factory.build();
			} catch (ConfigException e) {
				log.error("ConfigFrameworkFactory build ：", e);
				throw new IOException(e);
			}
			
		} catch (IOException ex) {
			throw new BeanInitializationException("Could not load properties",
					ex);
		}
	}

	public void process() {
		log.debug("SpringPropertyLoaderConfigurer process run");
		Properties props = new Properties();
		// Convert the merged properties, if necessary.
		props.setProperty("console.password", "654321");
		// Let the subclass process the properties.
		this.processProperties(beanFactory, props);
	}

	
}
