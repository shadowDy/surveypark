package com.dy.surveypark.model.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dy.surveypark.model.Question;

/**
 * 问题统计模型
 * 
 * @author DY
 * @version 创建时间：2015年10月26日 下午8:32:26
 */

public class QuestionStatisticsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Question question;
	private int count;// 回答该问题的人数

	private List<OptionStatisticsModel> osms = new ArrayList<>();

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OptionStatisticsModel> getOsms() {
		return osms;
	}

	public void setOsms(List<OptionStatisticsModel> osms) {
		this.osms = osms;
	}

}
