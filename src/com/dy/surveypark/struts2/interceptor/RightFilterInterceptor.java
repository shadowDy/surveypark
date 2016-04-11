package com.dy.surveypark.struts2.interceptor;

import org.apache.struts2.ServletActionContext;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.model.User;
import com.dy.surveypark.struts2.action.BaseAction;
import com.dy.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author DY
 * @version 创建时间：2015年11月9日 下午4:55:18
 */
public class RightFilterInterceptor implements Interceptor {

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
		BaseAction action = (BaseAction) invocation.getAction();
		ActionProxy proxy = invocation.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		// 将超链接的参数部分过滤掉
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		if (ValidateUtil.hasRight(ServletActionContext.getRequest(), ns,
				actionName, action)) {
			return invocation.invoke();
		}
		return "login";
	}
}

/*
 * if (ValidateUtil.isValid(ns) || "/".equals(ns)) { ns = ""; } String url = ns
 * + "/" + actionName; // 从application 中查询权限 Map<String, Right> map =
 * (Map<String, Right>) invocation
 * .getInvocationContext().getApplication().get("all_rights_map"); Right r =
 * map.get(url); if (r == null || r.isCommon()) { return invocation.invoke(); }
 * else { User user = (User) invocation.getInvocationContext().getSession()
 * .get("user"); // 登录？ if (user == null) { return "login"; } else { // 超级管理员 if
 * (user.isSuperAdmin()) { return invocation.invoke(); } else { // 有权限 if
 * (user.hasRight(r)) { return invocation.invoke(); } else { return
 * "error_no_right"; } } } }
 */

/*
 * Right r = null;// 通过url 查询Right 对象 if (r == null || r.isCommon()) { return
 * invocation.invoke(); } else { User user = (User)
 * invocation.getInvocationContext().getSession() .get("user"); // 登录？ if (user
 * == null) { return "login"; } else { // 超级管理员 if (user.isSuperAdmin()) {
 * return invocation.invoke(); } else { // 有权限 if (user.hasRight(r)) { return
 * invocation.invoke(); } else { return "error_no_right"; } } } } }
 */

