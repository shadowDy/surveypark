package com.dy.surveypark.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.dy.surveypark.model.Survey;

/**
 * 自定义数据源路由器（用于分布式数据库）
 * 
 * @author DY
 * @version 创建时间：2015年11月14日 下午5:49:32
 */
public class SurveyparkDataSourceRouter extends AbstractRoutingDataSource {

	/**
	 * 检测当前bean
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		// 得到当前的令牌
		SurveyparkToken token = SurveyparkToken.getCurrentToken();
		if (token != null) {
			Survey s = token.getSurvey();
			Integer id = s.getId();
			//解除令牌的绑定
			SurveyparkToken.unbindToken();
			return (id % 2) == 0 ? "even" : "odd";
		}
		return null;
	}

}
