package com.dy.surveypark.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.dy.surveypark.service.LogService;
import com.dy.surveypark.util.LogUtil;

/**
 * 初始化日志表监听
 * 
 * @author DY
 * @version 创建时间：2015年11月13日 下午5:14:09
 */
@SuppressWarnings("rawtypes")
@Component
public class IniLogTablesListener implements ApplicationListener {

	@Resource
	private LogService logService;

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// 上下文刷新事件
		if (arg0 instanceof ContextRefreshedEvent) {

			String tableName = LogUtil.generateLogTableName(0);
			System.out.println(tableName);
			logService.createLogTable(tableName);

			tableName = LogUtil.generateLogTableName(1);
			logService.createLogTable(tableName);

			tableName = LogUtil.generateLogTableName(2);
			logService.createLogTable(tableName);
			System.out.println("创建当前初始化的数据表");
		}

	}

}
