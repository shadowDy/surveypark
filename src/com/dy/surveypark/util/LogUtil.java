package com.dy.surveypark.util;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 日志工具类
 * 
 * @author DY
 * @version 创建时间：2015年11月12日 下午8:20:08
 */
public class LogUtil {

	/**
	 * 生成日志表名称 ：logs_2015_9 offset: 偏移量。0为当前月。
	 */
	public static String generateLogTableName(int offset) {
		// 单例的
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		// 默认月份范围：0-11
		int month = c.get(Calendar.MONTH) + 1;
		// 通过偏移量获取要想得到的月份
		month = month + offset;
		if (month > 12) {
			year++;
			month = month - 12;
		}
		if (month < 1) {
			year--;
			month = month + 12;
		}
		// 格式化输出 月份。9月变为 09 月，11，12 不变
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00");
		return "logs_" + year + "_" + df.format(month);
	}

}
