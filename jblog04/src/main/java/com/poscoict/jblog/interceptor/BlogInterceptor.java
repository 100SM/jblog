package com.poscoict.jblog.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.vo.BlogVo;

public class BlogInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private BlogService blogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ServletContext sc = request.getServletContext();
		BlogVo blogVo = blogService.getBlog((String) request.getAttribute("userId"));
		sc.setAttribute("blogVo", blogVo);
		return true;
	}
}