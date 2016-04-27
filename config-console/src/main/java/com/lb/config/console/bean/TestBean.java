package com.lb.config.console.bean;

import org.springframework.stereotype.Service;

import com.lb.config.framework.cache.CacheFactory;

@Service
public class TestBean {

	private String admin;

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public void test() {
		System.out.println("TestBean admin = " + admin);
		System.out.println(CacheFactory.CACHE.get("console.admin"));
	}

}
