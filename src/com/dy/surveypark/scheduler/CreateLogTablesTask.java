package com.dy.surveypark.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dy.surveypark.service.LogService;
import com.dy.surveypark.util.LogUtil;

/**
 * 创建日志表的石英任务
 * 
 * @author DY
 * @version 创建时间：2015年11月12日 下午6:17:29
 */
public class CreateLogTablesTask extends QuartzJobBean {

	// 需要手动配置LogService
	@Autowired
	private LogService logService;

	// 注入LogService
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * 生成日志表
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// 下一月
		String tableName = LogUtil.generateLogTableName(1);
		logService.createLogTable(tableName);
		// 下两月
		tableName = LogUtil.generateLogTableName(2);
		logService.createLogTable(tableName);
		// 下三月
		tableName = LogUtil.generateLogTableName(3);
		logService.createLogTable(tableName);
	}

}
