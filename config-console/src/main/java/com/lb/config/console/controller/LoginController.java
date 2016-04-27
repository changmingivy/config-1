package com.lb.config.console.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lb.config.console.bean.TestBean;
import com.lb.config.console.consant.Constant;
import com.lb.config.framework.cache.CacheFactory;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	private static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired
	private TestBean testBean;
	
	@Value("#{configProperties['console.admin']}")
	private String userName;
	
	@Value("#{configProperties['console.password']}")
	private String password;

	@RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public ModelAndView login(HttpServletRequest request) {
		System.out.println("Spring console.password: " + password);
		System.out.println("CacheFactory console.password: " + CacheFactory.CACHE.get("console.password"));
		request.removeAttribute("error");
		return new ModelAndView("login", new HashMap<String, Object>());
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public ModelAndView login(String username, String password,
			HttpServletRequest request) {
		log.debug("config username = " + this.userName + ", password = " + this.password);
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("error", "支持POST方法提交！");
				return new ModelAndView("login", new HashMap<String, Object>());
			}
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				request.setAttribute("error", "用户名或密码不能为空！");
				return new ModelAndView("login", new HashMap<String, Object>());
			}
			if (username.equals(this.userName) && password.equals(this.password)) {
				request.getSession().setAttribute(Constant.SESSION_USER_ID, "admin");
			} else {
				request.setAttribute("error", "用户名或密码错误！");
				request.getSession().removeAttribute(Constant.SESSION_USER_ID);
				return new ModelAndView("login", new HashMap<String, Object>());
			}
			request.removeAttribute("error");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			request.setAttribute("error", "登录异常，请联系管理员！");
			return new ModelAndView("login", new HashMap<String, Object>());
		}
		return new ModelAndView("index", new HashMap<String, Object>());
	}


}
