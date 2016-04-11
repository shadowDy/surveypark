package com.dy.surveypark.struts2.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Log;
import com.dy.surveypark.service.LogService;

/**
 * @author DY
 * @version 创建时间：2015年11月12日 下午3:33:16
 */
@Controller
@Scope("prototype")
public class LogAction extends BaseAction<Log> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private LogService logService;

	private List<Log> logs = new ArrayList<>();

	// 查询日志的月份数。默认为两个月
	private int monthNum = 2;

	public int getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	/**
	 * 查询全部日志
	 */
	public String findAllLogs() {
		this.logs = logService.findAllEntities();
		return "logListPage";
	}

	/**
	 * 查询最近的日志
	 */
	public String findNearestLogs() {
		this.logs = logService.findNearestLogs(monthNum);
		return "logListPage";
	}
}
