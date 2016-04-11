package com.dy.surveypark.util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.model.User;
import com.dy.surveypark.struts2.UserAware;
import com.dy.surveypark.struts2.action.BaseAction;

/**
 * 校验工具类
 */
public class ValidateUtil {

	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(String src) {
		if (src == null || "".equals(src.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断集合的有效性
	 */
	public static boolean isValid(Collection col) {
		if (col == null || col.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断数组的有效性
	 */
	public static boolean isValid(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否有权限
	 */
	public static boolean hasRight(HttpServletRequest request,
			String namespace, String actionName, BaseAction action) {
		if (ValidateUtil.isValid(namespace) || "/".equals(namespace)) {
			namespace = "";
		}
		String url = namespace + "/" + actionName;
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		Map<String, Right> map = (Map<String, Right>) sc
				.getAttribute("all_rights_map");
		Right right = map.get(url);
		if (right == null || right.isCommon()) {
			return true;
		} else {
			User user = (User) session.getAttribute("user");
			// 是否登录
			if (user == null) {
				return false;
			} else {
				// userAware 处理
				if (action != null && action instanceof UserAware) {
					((UserAware) action).setUser(user);
				}
				// 是超级管理员吗？
				if (user.isSuperAdmin()) {
					return true;
				} else {
					// 有权限
					if (user.hasRight(right)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}
}
