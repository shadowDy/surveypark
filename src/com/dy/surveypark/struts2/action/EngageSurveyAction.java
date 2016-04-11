package com.dy.surveypark.struts2.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.datasource.SurveyparkToken;
import com.dy.surveypark.model.Answer;
import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.service.SurveyService;
import com.dy.surveypark.util.StringUtil;
import com.dy.surveypark.util.ValidateUtil;

/**
 * @author DY
 * @version 创建时间：2015年10月21日 下午5:45:31
 */
/**
 * 参与调查Action
 */
@Controller
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements
		ServletContextAware, SessionAware, ParameterAware {

	private static final long serialVersionUID = 1L;
	// 当前调查的key
	private static final String CURRENT_SURVEY = "current_survey";
	// 所有参数的map
	private static final String ALL_PARAMS_MAP = "all_params_map";

	@Resource
	private SurveyService surveyService;

	private List<Survey> surveys;
	private Integer sid;
	// 当前页面
	private Page currPage;
	// 当前页面的ID
	private Integer currPid;

	public Integer getCurrPid() {
		return currPid;
	}

	public void setCurrPid(Integer currPid) {
		this.currPid = currPid;
	}

	public Page getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Page currPage) {
		this.currPage = currPage;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	/**
	 * 查询所有可用的调查
	 */
	public String findAllAvailableSurveys() {

		this.surveys = surveyService.findAllAvailableSurveys();
		return "engageSurveyListPage";
	}

	/**
	 * 获得图片的URL地址
	 */
	public String getImageUrl(String path) {
		if (ValidateUtil.isValid(path)) {
			String absPath = sc.getRealPath(path);
			File file = new File(absPath);
			if (file.exists()) {
				// surveypark + /upload/xxx.jpg
				return sc.getContextPath() + path;
			}
		}
		return sc.getContextPath() + "/question.bmp";
	}

	/**
	 * 首次进入参与调查
	 */
	public String entry() {
		// 查询调查首页
		this.currPage = surveyService.getfirstPage(sid);
		// 存放当前调查存放在session中,因为以后会经常访问当前调查的对象，所以将其写成常量
		sessionMap.put(CURRENT_SURVEY, currPage.getSurvey());
		// 将存放所有参数的大Map，存放到session中，用于保存用户答案和回显
		sessionMap.put(ALL_PARAMS_MAP,
				new HashMap<Integer, Map<String, String[]>>());
		return "engageSurveyPage";
	}

	/**
	 * 处理参与调查
	 */
	public String doEngageSurvey() {
		// 获取提交按钮的名称
		String submitName = getSubmitName();
		// 上一步
		if (submitName.endsWith("pre")) {
			mergeParamsIntoSession();
			this.currPage = surveyService.getPrePage(currPid);
			return "engageSurveyPage";
		}
		// 下一步
		else if (submitName.endsWith("next")) {
			mergeParamsIntoSession();
			this.currPage = surveyService.getNextPage(currPid);
			return "engageSurveyPage";
		}
		// 完成
		else if (submitName.endsWith("done")) {
			mergeParamsIntoSession();
			// to do 答案入库
			// 答案入库前，绑定token 到当前线程
			SurveyparkToken token = new SurveyparkToken();
			token.setSurvey(getCurrentSurvey());
			SurveyparkToken.bindToken(token);// 绑定令牌

			surveyService.saveAnswers(processAnswers());
			
			clearSessionDate();
			return "engageSurveyAction";
		}
		// 取消
		else if (submitName.endsWith("exit")) {
			clearSessionDate();
			return "engageSurveyAction";
		}
		return "";
	}

	/**
	 * 处理答案
	 */
	private List<Answer> processAnswers() {
		// 矩阵式单选按钮
		Map<Integer, String> matrixRadioMap = new HashMap<Integer, String>();
		// 所有答案的集合
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = null;
		String key = null;
		String[] value = null;
		Map<Integer, Map<String, String[]>> allMap = getAllParamsMap();
		for (Map<String, String[]> map : allMap.values()) {
			for (Entry<String, String[]> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				// 处理所有 q 开头的参数
				if (key.startsWith("q")) {
					// 常规参数
					if (!key.contains("other") && !key.contains("_")) {
						answer = new Answer();
						answer.setAnswersIds(StringUtil.arr2Str(value));
						answer.setQuestionId(getQid(key));// qid
						answer.setSurveyId(getCurrentSurvey().getId());// sid
						answer.setOtherAnswer(StringUtil.arr2Str(map.get(key
								+ "other")));// otherAnswer
						answers.add(answer);
					}
					// 矩阵式单选按钮
					else if (key.contains("_")) {
						Integer radioQid = getMatrixRadioQid(key);
						String oldValue = matrixRadioMap.get(radioQid);
						if (oldValue == null) {
							matrixRadioMap.put(radioQid,
									StringUtil.arr2Str(value));
						} else {
							matrixRadioMap.put(radioQid, oldValue + ","
									+ StringUtil.arr2Str(value));
						}
					}
				}
			}
		}
		// 单独处理矩阵式单选按钮
		processMatrixRadioMap(matrixRadioMap, answers);
		return answers;
	}

	/**
	 * 单独处理矩阵式单选按钮
	 */
	private void processMatrixRadioMap(Map<Integer, String> matrixRadioMap,
			List<Answer> answers) {
		Answer answer = null;
		Integer key = null;
		String value = null;
		for (Entry<Integer, String> entry : matrixRadioMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			answer = new Answer();
			answer.setAnswersIds(value);
			answer.setQuestionId(key);// qid
			answer.setSurveyId(getCurrentSurvey().getId());
			answers.add(answer);
		}
	}

	/**
	 * 获取矩阵式单选问题的id ------->q12_0----->12
	 */
	private Integer getMatrixRadioQid(String key) {
		return Integer.parseInt(key.substring(1, key.lastIndexOf("_")));
	}

	/**
	 * 获取当前调查对象
	 */
	private Survey getCurrentSurvey() {
		return (Survey) sessionMap.get(CURRENT_SURVEY);
	}

	/**
	 * 提取问题id-----> q12 -->12
	 */
	private Integer getQid(String string) {
		return Integer.parseInt(string.substring(1));
	}

	/**
	 * 清除session 数据
	 */
	private void clearSessionDate() {
		sessionMap.remove(CURRENT_SURVEY);
		sessionMap.remove(ALL_PARAMS_MAP);
	}

	/**
	 * 合并参数到session 中
	 */
	private void mergeParamsIntoSession() {
		Map<Integer, Map<String, String[]>> allParamsMap = getAllParamsMap();
		allParamsMap.put(currPid, paramsMap);
	}

	/**
	 * 获取session 中存放 所有参数的map
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, String[]>> getAllParamsMap() {
		return (Map<Integer, Map<String, String[]>>) sessionMap
				.get(ALL_PARAMS_MAP);
	}

	/**
	 * 获得提交按钮名称
	 */
	private String getSubmitName() {
		for (String key : paramsMap.keySet()) {
			if (key.startsWith("submit_")) {
				return key;
			}
		}
		return null;
	}

	/**
	 * 设置标记，用于答案的回显,主要用于radio、checkbox、select 的选中标记
	 */
	public String setTag(String name, String value, String selTag) {
		// 此时的currPage 已经是上一页/下一页的页面了
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);
		if (StringUtil.contains(values, value)) {
			return selTag;
		}
		return "";
	}

	/**
	 * 文本框答案的回显
	 */
	public String setText(String name) {
		// 此时的currPage 已经是上一页/下一页的页面了
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);

		return "value = '" + values[0] + "'";
	}

	// 接受ServletContext
	private ServletContext sc;

	// 注入ServletContext对象
	@Override
	public void setServletContext(ServletContext context) {
		this.sc = context;
	}

	// 接收sessionMap
	private Map<String, Object> sessionMap;

	// 注入sessionMap
	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

	// 接受当前表单的所有参数的map
	private Map<String, String[]> paramsMap;

	// 注入提交的所有参数的map
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.paramsMap = parameters;
	}

}
