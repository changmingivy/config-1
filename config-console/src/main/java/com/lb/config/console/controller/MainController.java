package com.lb.config.console.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

	private static Logger log = Logger.getLogger(MainController.class.getName());
	

	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public ModelAndView index(HttpServletRequest request) {
		log.info("/main/index");
		return new ModelAndView("index", new HashMap<String, Object>());
	}

}
