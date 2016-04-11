package com.dy.surveypark.struts2.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Survey;
import com.dy.surveypark.model.User;
import com.dy.surveypark.service.SurveyService;
import com.dy.surveypark.struts2.UserAware;
import com.dy.surveypark.util.StringUtil;
import com.dy.surveypark.util.ValidateUtil;

/**
 * SurveyAction
 */
@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey> implements UserAware,
		ServletContextAware {

	private static final long serialVersionUID = 2438909978838628762L;

	// 注入SurveyService
	@Resource
	private SurveyService surveyService;

	// 调查集合
	private List<Survey> mySurveys;

	// 接受sid参数
	private Integer sid;

	// 动态错误页指定
	private String inputPage;

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
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

	/**
	 * 查询我的调查列表
	 */
	public String mySurveys() {
		// User user = (User) sessionMap.get("user");
		this.mySurveys = surveyService.findMySurveys(user);
		return "mySurveyListPage";
	}

	/**
	 * 新建调查
	 */
	public String newSurvey() {
		// User user = (User) sessionMap.get("user");
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}

	/**
	 * 设计调查
	 */
	public String designSurvey() {
		this.model = surveyService.getSurveyWithChildren(sid);
		return "designSurveyPage";
	}

	/**
	 * 编辑调查
	 */
	public String editSurvey() {
		this.model = surveyService.getSurvey(sid);
		return "editSurveyPage";
	}

	/**
	 * 更新调查
	 */
	public String updateSurvey() {

		// 获取当前调查的id 值，因为新的action 中sid 的值为空
		this.sid = model.getId();
		// 保持survey 和user 的关联关系，因为编辑调查时，是一个全新的模型对象，并没有给user 属性赋值，所以需要关联user
		model.setUser(user);
		surveyService.updateSurvey(model);
		return "designSurveyAction";
	}

	/**
	 * 该方法只在updateSurvey运行，调用updateSurvey时出错进入相应的页面
	 */
	public void prepareUpdateSurvey() {
		inputPage = "/editSurvey.jsp";
	}

	/**
	 * 删除调查
	 */
	public String deleteSurvey() {
		surveyService.deleteSurvey(sid);
		return "findMySurveysAction";
	}

	/**
	 * 清除调查答案
	 */
	public String clearAnswers() {
		surveyService.clearAnswer(sid);
		return "findMySurveysAction";
	}

	/**
	 * 打开/关闭调查
	 */
	public String toggleStatus() {
		surveyService.toggleStatus(sid);
		return "findMySurveysAction";
	}

	/**
	 * 到达增加logo的页面
	 */
	public String toAddLogoPage() {
		return "addLogoPage";
	}

	// 上传文件
	private File logoPhoto;
	private String logoPhotoFileName;

	public File getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}

	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}

	/**
	 * 实现Logo的上传
	 */
	public String doAddLogo() {
		// 1.实现上传
		if (ValidateUtil.isValid(logoPhotoFileName)) {
			// upload 文件夹真实路径
			String dir = sc.getRealPath("/upload");
			// 扩展名
			String ext = logoPhotoFileName.substring(logoPhotoFileName
					.lastIndexOf("."));
			// 文件名称:纳秒时间作为文件名
			long l = System.nanoTime();
			File newFile = new File(dir, l + ext);
			// 文件另存为
			logoPhoto.renameTo(newFile);
			// 2.更新路径
			surveyService.updateLogoPhotoPath(sid, "/upload/" + l + ext);
		}
		return "designSurveyAction";
	}

	public void prepareDoAddLogo() {
		inputPage = "/addLogo.jsp";
	}

	/**
	 * 图片是否存在
	 */
	public boolean photoExists() {
		String path = model.getLogoPhotoPath();
		if (ValidateUtil.isValid(path)) {
			// 文件的绝对路径
			String absPath = sc.getRealPath(path);
			File file = new File(absPath);
			return file.exists();
		}
		return false;
	}

	/**
	 * 分析调查
	 */
	public String analyzeSurvey() {
		this.model = surveyService.getSurveyWithChildren(sid);
		return "analyzeSurveyListPage";
	}

	// 接收session
	// private Map<String, Object> sessionMap;

	// 接收用户对象,使用UserAware 注入User，不用再从sessionMap 中获取User

	/*
	 * @Override public void setSession(Map<String, Object> arg0) { sessionMap
	 * =arg0; }
	 */
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	// 接受ServletContext对象
	private ServletContext sc;

	/**
	 * 注入ServletContext对象
	 */
	@Override
	public void setServletContext(ServletContext arg0) {
		this.sc = arg0;
	}

	/**
	 * 该方法只在designSurvey之前调用
	 */
	// public void prepareDesignSurvey() {
	// this.model = surveyService.getSurveyWithChildren(sid);
	// }

}
