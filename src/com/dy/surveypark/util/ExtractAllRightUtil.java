package com.dy.surveypark.util;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dy.surveypark.model.Right;
import com.dy.surveypark.service.BaseService;
import com.dy.surveypark.service.RightService;

/**
 * 提取所有权限的工具类
 * 
 * @author DY
 * @version 创建时间：2015年11月4日 上午10:37:23
 */
public class ExtractAllRightUtil {

	public static void main(String[] args) throws Exception {

		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		RightService rs = (RightService) ac.getBean("rightService");

		ClassLoader loader = ExtractAllRightUtil.class.getClassLoader();
		URL url = loader.getResource("com/dy/surveypark/struts2/action");
		// System.out.println(url.toString());
		File dir = new File(url.toURI());
		File[] files = dir.listFiles();
		String fname = "";
		for (File f : files) {
			fname = f.getName();
			if (fname.endsWith(".class") && !fname.equals("BaseAction.class")) {
				// System.out.println(fname);
				processAction(fname, rs);
			}
		}

	}

	/**
	 * 处理Action 类，捕获所有URL 地址，形成权限
	 */
	private static void processAction(String fname, RightService rs) {

		String pkgName = "com.dy.surveypark.struts2.action.";
		String simpleClsssName = fname.substring(0, fname.indexOf(".class"));
		String className = pkgName + simpleClsssName;
		// 得到具体类
		try {
			Class clazz = Class.forName(className);
			Method[] methods = clazz.getDeclaredMethods();
			// 返回值类型，方法名称，参数
			Class reType = null;
			String methodName = null;
			Class[] paramType = null;
			String url = null;
			for (Method m : methods) {
				reType = m.getReturnType();
				methodName = m.getName();
				paramType = m.getParameterTypes();
				// Modifier.isPublic(m.getModifiers():获取方法的类型，是否是公有，java底层对权限的设定也是通过
				// 二进制数1 不断的向左移位，getModifiers()方法 ：所有方法权限取'或'运算，获得所有权限值，
				// isPublic()方法：讲public 的权限与所拥有的权限取'与'运算，判断是否是public 类型方法
				if (reType == String.class && !ValidateUtil.isValid(paramType)
						&& Modifier.isPublic(m.getModifiers())) {
					if (methodName.equals("execute")) {
						url = "/" + simpleClsssName;
					} else {
						url = "/" + simpleClsssName + "_" + methodName;
					}
					// System.out.println(url);
					// 按照URL 追加权限
					rs.appendRightByUrl(url);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
