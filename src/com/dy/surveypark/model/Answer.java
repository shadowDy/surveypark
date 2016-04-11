package com.dy.surveypark.model;

import java.util.Date;

/**
 * @author DY
 * @version 创建时间：2015年10月13日 下午8:25:05
 */
/**
 * answer 实体
 * 
 * @author dy
 *
 */
public class Answer extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String answersIds;// 选项索引
	private String otherAnswer;
	private String uuid;// 批次
	private Date answerTime;

	private Integer questionId;// 关联字段（冗余）
	private Integer surveyId;// 关联字段（冗余）

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswersIds() {
		return answersIds;
	}

	public void setAnswersIds(String answersIds) {
		this.answersIds = answersIds;
	}

	public String getOtherAnswer() {
		return otherAnswer;
	}

	public void setOtherAnswer(String otherAnswer) {
		this.otherAnswer = otherAnswer;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

}
