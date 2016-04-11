package com.dy.surveypark.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author DY
 * @version 创建时间：2015年11月8日 下午7:52:30
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract Integer getId();

	public abstract void setId(Integer id);

	@Override
	public String toString() {
		try {
			StringBuffer buffer = new StringBuffer();
			Class clazz = this.getClass();
			String simpleName = clazz.getSimpleName();
			buffer.append(simpleName);
			buffer.append("{");

			Field[] fs = clazz.getDeclaredFields();
			Class ftype = null;
			String fname = null;
			Object fvalue = null;
			for (Field f : fs) {
				f.setAccessible(true);
				ftype = f.getType();
				fname = f.getName();
				fvalue = f.get(this);
				// 是否是基本数据类型
				if ((ftype.isPrimitive() || ftype == Integer.class
						|| ftype == Long.class || ftype == Short.class
						|| ftype == Boolean.class || ftype == Double.class
						|| ftype == Float.class || ftype == Character.class || ftype == String.class)
						&& !Modifier.isStatic(f.getModifiers())) {
					buffer.append(fname);
					buffer.append(":");
					buffer.append(fvalue);
					buffer.append(",");
				}
			}
			buffer.append("}");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
