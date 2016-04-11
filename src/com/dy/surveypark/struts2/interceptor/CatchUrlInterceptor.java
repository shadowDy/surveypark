package com.dy.surveypark.struts2.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dy.surveypark.service.RightService;
import com.dy.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 捕获URL 的拦截器
 * 
 * @author DY
 * @version 创建时间：2015年11月4日 下午4:31:03
 */
public class CatchUrlInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 获取当前代理
		ActionProxy proxy = invocation.getProxy();
		// 获取命名空间
		String namespace = proxy.getNamespace();
		// 获取actionName
		String actionName = proxy.getActionName();

		// / + / +actionName
		if (!ValidateUtil.isValid(namespace) || namespace.equals("/")) {
			namespace = "";
		}
		String url = namespace + "/" + actionName;

		// 获取application中Spring 容器
		// 方式1: ctrl + shift + t :搜索WebApplicationContext
		/*
		 * ApplicationContext ac = (ApplicationContext) invocation
		 * .getInvocationContext() .getApplication()
		 * .get(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		 */
		// 方式2：
		ServletContext sc = ServletActionContext.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		RightService rightService = (RightService) ac.getBean("rightService");

		rightService.appendRightByUrl(url);
		return invocation.invoke();
	}
}
