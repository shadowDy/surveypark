package com.dy.surveypark.service;

import java.util.List;
import java.util.Set;

import com.dy.surveypark.model.Right;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:11:44
 */
public interface RightService extends BaseService<Right> {

	/**
	 * 保存或者更新权限
	 */
	public void saveOrUpdateRight(Right right);

	/**
	 * 按照URL 追加权限
	 */
	public void appendRightByUrl(String url);

	/**
	 * 批量更新权限
	 */
	public void batchUpdateRights(List<Right> allRights);

	/**
	 * 查询在指定范围内的权限
	 */
	public List<Right> findRightsInRange(Integer[] ownRightIds);

	/**
	 * 查询不再指定范围内的权限
	 */
	public List<Right> findRightsNotInRange(Set<Right> rights);

	/**
	 * 查询最大权限位
	 */
	public int getMaxRightPos();

}