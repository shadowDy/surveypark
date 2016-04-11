package com.dy.surveypark.struts2.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.model.User;
import com.dy.surveypark.service.SurveyService;
import com.dy.surveypark.struts2.UserAware;

/**
 * 移动复制页面Action
 * 
 * @author DY
 * @version 创建时间：2015年10月16日 下午5:08:18
 */
@Controller
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SurveyService surveyService;
	// 源页面id
	private Integer srcPid;
	// 目标页面id
	private Integer targPid;
	// 位置：0-之前，1-之后
	private int pos;
	// 目标调查id
	private Integer sid;
	private List<Survey> mySurveys;
	// 接收用户
	private User user;

	public Integer getTargPid() {
		return targPid;
	}

	public void setTargPid(Integer targPid) {
		this.targPid = targPid;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public Integer getSrcPid() {
		return srcPid;
	}

	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}

	/**
	 * 到达移动/复制列表页面
	 */
	public String toSelectTargetPage() {

		this.mySurveys = surveyService.getSurverWithPages(user);
		return "moveOrCopyPageListPage";
	}

	/**
	 * 进行页面移动或者复制
	 */
	public String doMoveOrCopyPage() {

		surveyService.moveOrCopyPage(srcPid, targPid, pos);
		return "designSurveyAction";
	}

	/**
	 * 注入用户
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

}
