package com.dy.surveypark.service;

import java.util.List;

import com.dy.surveypark.model.Log;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:11:44
 */
public interface LogService extends BaseService<Log> {
	/**
	 * 通过表名创建日志表
	 */
	public void createLogTable(String tableName);

	/**
	 * 查询最近 指定月份数 的日志
	 */
	public List<Log> findNearestLogs(int i);

}