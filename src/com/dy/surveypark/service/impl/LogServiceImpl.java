package com.dy.surveypark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.id.UUIDGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.stereotype.Service;

import com.dy.surveypark.dao.BaseDao;
import com.dy.surveypark.model.Log;
import com.dy.surveypark.service.LogService;
import com.dy.surveypark.util.LogUtil;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:13:34
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

	@Resource(name = "logDao")
	@Override
	public void setDao(BaseDao<Log> dao) {
		super.setDao(dao);
	}

	/**
	 * 通过表名创建日志表
	 */
	public void createLogTable(String tableName) {
		String sql = "create table if not exists " + tableName + " like logs";
		this.executeSql(sql);
	}

	/**
	 * 重写该方法，动态插入日志记录（动态表）
	 */
	@Override
	public void saveEntity(Log t) {
		// insert into logs_2015_11()
		String sql = "insert into "
				+ LogUtil.generateLogTableName(0)
				+ "(id,operator,operName,operParams,operResult,resultMsg,opertime)"
				+ "values(?,?,?,?,?,?,?)";
		// 使用UUID 生成id. 注意，导入的是Hibernate 的 UUIDHexGenerator,而不是阿帕奇的。
		UUIDHexGenerator uuid = new UUIDHexGenerator();
		String id = (String) uuid.generate(null, null);
		System.out.println(id);
		this.executeSql(sql, id, t.getOperator(), t.getOperName(),
				t.getOperParams(), t.getOperResult(), t.getResultMsg(),
				t.getOperTime());
	}

	/**
	 * 查询最近 指定月份数 的日志
	 */
	@SuppressWarnings("unchecked")
	public List<Log> findNearestLogs(int n) {

		String tableName = LogUtil.generateLogTableName(0);
		// 查询最近日志表名称
		// 在mysql 数据库中有 information_schema,其中包括了数据库所有的库和表，在其中可以按名称查找想要的表
		String sql = "select table_name from information_schema.tables "
				+ "where table_schema = 'surveypark' "
				+ "and table_name like 'logs_%' " + "and table_name <= '"
				+ tableName + "' order by table_name desc limit 0," + n;
		List list = this.executeSqlQuery(null, sql);
		// 查询最近若干月内的日志
		String logSql = "";
		String logName = null;
		for (int i = 0; i < list.size(); i++) {
			logName = (String) list.get(i);
			if (i == list.size() - 1) {
				logSql = logSql + " select * from " + logName;
			} else {
				logSql = logSql + " select * from " + logName + " union ";
			}
		}
		// System.out.println(logSql);
		// 封装成Log 实体类
		return this.executeSqlQuery(Log.class, logSql);

	}
}
