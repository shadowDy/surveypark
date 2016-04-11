package com.dy.surveypark.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.Survey;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:14:13
 */
public class App {

	/*
	 * public static void main(String[] args) throws Exception { StringBuffer
	 * buffer = new StringBuffer(); char[] chars =
	 * {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; String
	 * src = "123456" ; byte[] bytes = src.getBytes(); MessageDigest md =
	 * MessageDigest.getInstance("MD5"); byte[] targ = md.digest(bytes);
	 * for(byte b: targ){ buffer.append(chars[(b >> 4) & 0x0F]);
	 * buffer.append(chars[b & 0x0F]); } System.out.println(buffer.toString());
	 * }
	 */

	public static void main(String[] args) throws Exception {
		Survey s1 = new Survey();
		s1.setTitle("s1");

		Page p1 = new Page();
		p1.setTitle("p1");

		Question q1 = new Question();
		q1.setTitle("q1");

		Question q2 = new Question();
		q2.setTitle("q1");

		p1.setSurvey(s1);
		p1.getQuestions().add(q1);
		p1.getQuestions().add(q2);
		// ------------------浅拷贝----------------------------
		Page newPage = new Page();
		newPage.setSurvey(p1.getSurvey());
		newPage.setQuestions(p1.getQuestions());
		newPage.setTitle(p1.getTitle());

		System.out.println("kkk");

		// 开始进行深度复制
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);

		oos.writeObject(p1);
		oos.close();
		baos.close();

		byte[] bytes = baos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);

		Page copy = (Page) ois.readObject();
		ois.close();
		bais.close();

		System.out.println(copy);

	}

}
