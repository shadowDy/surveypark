package com.dy.surveypark.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Set;

import com.dy.surveypark.model.BaseEntity;
import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Role;

/**
 * 数据
 */
public class DataUtil {
	/**
	 * 使用md5算法进行加密
	 */
	public static String md5(String src) {
		try {
			StringBuffer buffer = new StringBuffer();
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F' };
			byte[] bytes = src.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);
			for (byte b : targ) {
				buffer.append(chars[(b >> 4) & 0x0F]);
				buffer.append(chars[b & 0x0F]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 深度复制，复制整个对象图，若把参数设置为Object 类型，则在编译期间不能容错，
	 * 设置为Serializable，则若对象没有实现序列化，编译不能通过。所以Serializable是首选
	 */
	public static Serializable deeplyCopy(Serializable src) {

		try {
			// 开始进行深度复制
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(src);
			oos.close();
			baos.close();

			byte[] bytes = baos.toByteArray();

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);

			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			return copy;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 抽取所有 实体的id 形成字符串
	 */
	public static String extract(Set<? extends BaseEntity> entities) {
		String temp = "";
		if (ValidateUtil.isValid(entities)) {
			for (BaseEntity e : entities) {
				temp = temp + e.getId() + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}
}
