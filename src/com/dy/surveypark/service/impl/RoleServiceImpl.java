package com.dy.surveypark.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.surveypark.dao.BaseDao;
import com.dy.surveypark.model.Right;
import com.dy.surveypark.model.Role;
import com.dy.surveypark.service.RightService;
import com.dy.surveypark.service.RoleService;
import com.dy.surveypark.util.DataUtil;
import com.dy.surveypark.util.StringUtil;
import com.dy.surveypark.util.ValidateUtil;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:13:34
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements
		RoleService {

	@Autowired
	private RightService rightService;

	@Resource(name = "roleDao")
	public void setDao(BaseDao<Role> dao) {
		super.setDao(dao);
	}
	
	/**
	 * 保存或者更新角色
	 */
	@Override
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds) {
		if (!ValidateUtil.isValid(ownRightIds)) {
			role.getRights().clear();
		} else {
			List<Right> rights = rightService.findRightsInRange(ownRightIds);
			role.setRights(new HashSet<Right>(rights));
		}
		this.saveOrUpdateEntity(role);
	}

	/**
	 * 查询不在指定范围中的角色
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if (!ValidateUtil.isValid(roles)) {
			return this.findAllEntities();
		} else {
			String hql = "from Role r where r.id not in ("
					+ DataUtil.extract(roles) + ")";
			return this.findEntityByHQL(hql);
		}
	}

	/**
	 * 查询在指定范围中的角色
	 */
	public List<Role> findRolesInRange(Integer[] ownRoleIds) {
		if (ValidateUtil.isValid(ownRoleIds)) {
			String hql = "from Role r where r.id in ("
					+ StringUtil.arr2Str(ownRoleIds) + ")";
			return this.findEntityByHQL(hql);
		}
		return null;
	}

}
