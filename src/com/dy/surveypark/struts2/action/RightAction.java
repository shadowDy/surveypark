package com.dy.surveypark.struts2.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.service.RightService;

/**
 * RightAction
 * 
 * @author DY
 * @version 创建时间：2015年11月2日 下午12:09:53
 */
@Controller
@Scope("prototype")
public class RightAction extends BaseAction<Right> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RightService rightService;

	private List<Right> allRights;
	private Integer rightId;

	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}

	/**
	 * 查询所有权限
	 */
	public String findAllRights() {
		this.allRights = rightService.findAllEntities();
		return "rightListPage";
	}

	/**
	 * 添加权限
	 */
	public String toAddRightPage() {
		return "addRightPage";
	}

	/**
	 * 保存或更新调查
	 */
	public String saveOrUpdateRight() {
		// 插入，处理权限位和权限码
		rightService.saveOrUpdateRight(model);
		return "findAllRightAction";
	}

	/**
	 * 编辑权限
	 */
	public String editRight() {
		this.model = rightService.getEntity(rightId);
		return "editRightPage";
	}

	/**
	 * 删除权限
	 */
	public String deleteRight() {
		Right right = rightService.getEntity(rightId);
		rightService.deleteEntity(right);
		return "findAllRightAction";

	}
	/**
	 * 批量更新权限
	 */
	public String batchUpdateRights() {
		rightService.batchUpdateRights(allRights);
		return "findAllRightAction";
	}
}
