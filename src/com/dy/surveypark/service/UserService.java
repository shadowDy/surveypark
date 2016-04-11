package com.dy.surveypark.service;

import com.dy.surveypark.model.User;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:11:44
 */
public interface UserService extends BaseService<User> {

	/**
	 *  判断email是否占用
	 */
	public boolean isRegisted(String email);

	/**
	 * 验证登陆信息
	 */
	public User validateLoginInfo(String email, String md5);
	/**
	 * 更新授权,只能更新角色设置
	 */
	public void updateAuthorize(User model, Integer[] ownRoleIds);
	/**
	 * 清除授权
	 */
	public void clearAuthorize(Integer userId);
}