package com.dy.surveypark.struts2.interceptor;

import javax.servlet.FilterChain;

import com.dy.surveypark.model.User;
import com.dy.surveypark.struts2.UserAware;
import com.dy.surveypark.struts2.action.BaseAction;
import com.dy.surveypark.struts2.action.LoginAction;
import com.dy.surveypark.struts2.action.RegAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sun.net.httpserver.Filter;

/**
 * 登录拦截器
 * @author DY
 * @version 创建时间：2015年9月23日 下午4:27:54
 */
public class LoginInterceptor implements Interceptor {
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
	public String intercept(ActionInvocation arg0) throws Exception {
		BaseAction action = (BaseAction) arg0.getAction();
		if(action instanceof LoginAction || action instanceof RegAction){
			//放行
			return arg0.invoke();
		}else{
			User user = (User) arg0.getInvocationContext().getSession().get("user");
			if(user == null){
				//去登陆
				return "login";
			}else{
				if(action instanceof UserAware){
					//注入user 给 action
					((UserAware) action).setUser(user);
				}
				//放行
				return arg0.invoke();
			}
		}
	}

}
