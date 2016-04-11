package com.dy.surveypark.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 页面类
 */
public class Page extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title = "未命名";
	private String description;

	// 简历从Page到Survey之间多对一关联关系

	// transient:临时的，虚拟机不会进行深度复制
	private transient Survey survey;

	// 简历从Page到Question之间一对多关联关系
	private Set<Question> questions = new HashSet<>();

	// 页序
	private float orderno;

	public float getOrderno() {
		return orderno;
	}

	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}

	public Integer getId() {
		return id;
	}

	// 在默认情况下，使orderno 和id 保持一致
	public void setId(Integer id) {
		this.id = id;
		if (id != null) {
			this.orderno = id;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

}
