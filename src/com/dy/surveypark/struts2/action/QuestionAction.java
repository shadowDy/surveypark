package com.dy.surveypark.struts2.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.service.SurveyService;

/**
 * @author DY
 * @version 创建时间：2015年10月10日 下午9:33:11
 */
@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SurveyService surveyService;

	private Integer sid;
	private Integer pid;
	private Integer qid;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	/**
	 * 到达选择页面
	 */
	public String toSelectQuestionType() {
		return "selectQuestionTypePage";
	}

	/**
	 * 到达设计问题页面
	 */
	public String toDesignQuestionPage() {

		return "" + model.getQuestionType();
	}

	/**
	 * 保存/更新问题
	 */
	public String saveOrUpdateQuestion() {
		// 保持关联关系
		Page p = new Page();
		p.setId(pid);
		model.setPage(p);
		surveyService.saveOrUpdateQuestion(model);
		return "designSurveyAction";
	}

	/**
	 * 删除问题
	 */

	public String deleteQuestion() {
		surveyService.deleteQuestion(qid);
		return "designSurveyAction";
	}

	/**
	 * 编辑问题
	 */
	public String editQuestion() {
		this.model = surveyService.getQuestion(qid);
		return "" + model.getQuestionType();
	}
}
