package com.dy.surveypark.service;

import java.util.List;
import java.util.Set;

import com.dy.surveypark.model.Role;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:11:44
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 保存或更新角色
	 */
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds);

	/**
	 * 查询不在指定范围中的角色
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles);

	/**
	 * 查询在指定范围中的角色
	 */
	public List<Role> findRolesInRange(Integer[] ownRoleIds);

}