package com.dy.surveypark.service;

import com.dy.surveypark.model.statistics.QuestionStatisticsModel;

/**
 * 统计服务
 * @author DY
 * @version 创建时间：2015年10月26日 下午8:42:55
 */
public interface StatisticsService {

	public QuestionStatisticsModel statistics(Integer qid);
	
}
