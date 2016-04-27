package com.lb.config.console.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lb.config.console.consant.Constant;

public class CheckLoginFilter implements Filter {

	private static Logger log = Logger.getLogger(CheckLoginFilter.class.getName());

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			HttpSession session = servletRequest.getSession();
			
			String uri = servletRequest.getRequestURI();
			log.debug("request uri : " + uri);
			
			String path = servletRequest.getContextPath();
			log.debug("servlet path : " + path);
			
			request.setAttribute(Constant.APP_CONTEXT_PATH, servletRequest.getContextPath());
			
			String userName = (String) session.getAttribute(Constant.SESSION_USER_ID);
			if(StringUtils.isEmpty(userName)) {
				String url = servletRequest.getServletPath().replaceAll("/+", "/");
				if(url.indexOf("/login.shtml") == -1 || url.indexOf("login.shtml") == -1) {
					log.debug("redirect to login.shtml ,current url = " + url);
					servletResponse.sendRedirect(path + "/login.shtml");
				}
			} 
			chain.doFilter(request, response);
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(e.getMessage());
			out.close();
			return;
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}