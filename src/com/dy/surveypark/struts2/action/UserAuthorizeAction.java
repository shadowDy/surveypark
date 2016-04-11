package com.dy.surveypark.struts2.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Role;
import com.dy.surveypark.model.User;
import com.dy.surveypark.service.RoleService;
import com.dy.surveypark.service.UserService;

/**
 * 用户授权action
 * 
 * @author DY
 * @version 创建时间：2015年11月8日 下午3:43:14
 */
@Controller
@Scope("prototype")
public class UserAuthorizeAction extends BaseAction<User> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	private List<User> allUsers = new ArrayList<>();
	private Integer userId;
	private List<Role> noOwnRoles;
	private Integer[] ownRoleIds;

	public Integer[] getOwnRoleIds() {
		return ownRoleIds;
	}

	public void setOwnRoleIds(Integer[] ownRoleIds) {
		this.ownRoleIds = ownRoleIds;
	}

	public List<Role> getNoOwnRoles() {
		return noOwnRoles;
	}

	public void setNoOwnRoles(List<Role> noOwnRoles) {
		this.noOwnRoles = noOwnRoles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	/**
	 * 查询所有用户
	 */
	public String findAllUsers() {
		this.allUsers = userService.findAllEntities();
		return "userAuthorizeListPage";
	}

	/**
	 * 修改授权
	 */
	public String editAuthorize() {
		this.model = userService.getEntity(userId);
		this.noOwnRoles = roleService.findRolesNotInRange(model.getRoles());
		return "editAuthorizePage";
	}

	/**
	 * 更新授权
	 */
	public String updateAuthorize() {
		userService.updateAuthorize(model, ownRoleIds);
		return "findAllRolesAction";
	}
	
	/**
	 * 清除授权
	 */
	public String clearAuthorize(){
		userService.clearAuthorize(userId);
		return "findAllRolesAction";
		
	}
}
