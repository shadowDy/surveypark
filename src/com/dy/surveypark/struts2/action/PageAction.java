package com.dy.surveypark.struts2.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.service.SurveyService;

/**
 * @author DY
 * @version 创建时间：2015年9月29日 下午7:58:27
 */
@Controller
@Scope("prototype")
public class PageAction extends BaseAction<Page> {

	private static final long serialVersionUID = 1L;
	// 注入
	@Autowired
	private SurveyService surveyService;

	private Integer sid;
	private Integer pid;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	/**
	 * 到达添加page 的页面
	 */
	public String toAddPage() {

		return "addPagePage";
	}

	/**
	 * 保存/更新页面
	 */
	public String saveOrUpdatePage() {

		Survey s = new Survey();
		s.setId(sid);
		model.setSurvey(s);
		surveyService.saveOrUpdatePage(model);
		return "designSurveyAction";
	}

	public String editPage() {
		this.model = surveyService.getPage(pid);
		return "editPagePage";
	}
	/**
	 * 删除页面
	 * @return
	 */
	public String deletePage(){
		surveyService.deletePage(pid);
		return "designSurveyAction";
	}

}
