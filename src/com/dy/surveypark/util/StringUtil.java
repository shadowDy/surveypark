package com.dy.surveypark.util;

/**
 * @author DY
 * @version 创建时间：2015年10月10日 下午10:43:17
 */
/**
 * 字符串工具类
 */
public class StringUtil {
	/**
	 * 按照指定字符将字符串拆分为字符串数组，tag 为指定字符
	 */
	public static String[] str2Arr(String str, String tag) {
		if (ValidateUtil.isValid(str)) {
			return str.split(tag);
		}
		return null;
	}

	/**
	 * 判断在values 数组中，是否含有指定的value 字符串
	 */
	public static boolean contains(String[] values, String value) {
		if (ValidateUtil.isValid(values)) {
			for (String s : values) {
				if (s.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将数组变换为字符串，使用 ， 分割
	 */
	public static String arr2Str(Object[] arr) {
		String temp = "";
		if (ValidateUtil.isValid(arr)) {
			for (Object s : arr) {
				temp = temp + s + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return null;
	}

	/**
	 * 获取字符串的描述信息
	 */
	public static String getDescString(String str) {
		if (str != null && str.trim().length() > 30) {
			return str.substring(0, 30);
		}
		return str;
	}
}
