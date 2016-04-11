package com.dy.surveypark.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import com.dy.surveypark.util.StringUtil;

/**
 * 自定义缓存中 key 的生成器
 * 
 * @author DY
 * @version 创建时间：2015年11月15日 下午1:04:22
 */
public class SurveyparkKeyBenerator implements KeyGenerator {

	/**
	 * 1:目标对象，2：方法，3：参数列表
	 */
	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		String className = arg0.getClass().getSimpleName();
		String methodName = arg1.getName();
		String params = StringUtil.arr2Str(arg2);
		String key = className + "." + methodName + "(" + params + ")";
		System.out.println(key);
		return key;
	}
}
