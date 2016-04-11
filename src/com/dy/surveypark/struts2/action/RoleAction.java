package com.dy.surveypark.struts2.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.model.Role;
import com.dy.surveypark.service.RightService;
import com.dy.surveypark.service.RoleService;

/**
 * @author DY
 * @version 创建时间：2015年11月5日 下午4:07:14
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleService roleSurvice;
	@Autowired
	private RightService rightService;

	private List<Role> allRoles = null;
	private List<Right> noOwnRights;
	private Integer roleId;
	private Integer[] ownRightIds;

	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer[] getOwnRightIds() {
		return ownRightIds;
	}

	public void setOwnRightIds(Integer[] ownRightIds) {
		this.ownRightIds = ownRightIds;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}

	/**
	 * 查询所有角色
	 */
	public String findAllRoles() {
		this.allRoles = roleSurvice.findAllEntities();
		return "roleListPage";
	}

	/**
	 * 添加角色
	 */
	public String toAddRolePage() {
		this.noOwnRights = rightService.findAllEntities();
		return "addRolePage";
	}

	/**
	 * 保存更新角色
	 */
	public String saveOrUpdateRole() {
		roleSurvice.saveOrUpdateRole(model, ownRightIds);
		return "findAllRolesAction";
	}

	/**
	 * 编辑角色
	 */
	public String editRole() {
		this.model = roleSurvice.getEntity(roleId);
		this.noOwnRights = rightService.findRightsNotInRange(model.getRights());
		return "editRolePage";
	}

	/**
	 * 删除角色
	 */
	public String deleteRole() {
		Role r = new Role();
		r.setId(roleId);
		roleSurvice.deleteEntity(r);
		return "findAllRolesAction";
	}
}
