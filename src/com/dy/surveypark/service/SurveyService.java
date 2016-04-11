package com.dy.surveypark.service;

import java.util.List;

import com.dy.surveypark.model.Answer;
import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.model.User;

/**
 * @author DY
 * @version 创建时间：2015年9月21日 下午9:10:40
 */
public interface SurveyService {

	/**
	 * 查询调查列表
	 */
	public List<Survey> findMySurveys(User user);

	/**
	 * 新建调查
	 */
	public Survey newSurvey(User user);

	/**
	 * 按照id查询Survey
	 */
	public Survey getSurvey(Integer sid);

	/**
	 * 按照id查询Survey,同时携带所有的孩子
	 */
	public Survey getSurveyWithChildren(Integer sid);

	/**
	 * 更新调查
	 * 
	 * @param s
	 */
	public void updateSurvey(Survey s);

	/**
	 * 保存/更新页面
	 */
	public void saveOrUpdatePage(Page page);

	/**
	 * 按照ID 查询页面
	 */
	public Page getPage(Integer pid);

	/**
	 * 保存/更新问题
	 * 
	 * @param quesion
	 */
	public void saveOrUpdateQuestion(Question question);

	/**
	 * 删除问题,同时删除答案
	 * 
	 * @param qId
	 */
	public void deleteQuestion(Integer qid);

	/**
	 * 删除页面，同时删除问题和答案
	 * 
	 * @param pid
	 */
	public void deletePage(Integer pid);

	/**
	 * 删除调查，同时删除页面，问题，答案
	 * 
	 * @param sid
	 */
	public void deleteSurvey(Integer sid);

	/**
	 * 编辑问题
	 * 
	 * @param qid
	 * @return
	 */
	public Question getQuestion(Integer qid);

	/**
	 * 清除调查中的所有答案
	 */
	public void clearAnswer(Integer sid);

	/**
	 * 打开关闭调查
	 */
	public void toggleStatus(Integer sid);

	/**
	 * 更新Logo的路径
	 */
	public void updateLogoPhotoPath(Integer sid, String path);

	/**
	 * 查询调查集合， 携带pages
	 */
	public List<Survey> getSurverWithPages(User user);

	/**
	 * 进行页面移动/复制
	 */
	public void moveOrCopyPage(Integer srcPid, Integer targPid, int pos);

	/**
	 * 查询所有可见的调查
	 */
	public List<Survey> findAllAvailableSurveys();

	/**
	 * 查询调查的首页
	 */
	public Page getfirstPage(Integer sid);

	/**
	 * 查询当前页的上一页
	 */
	public Page getPrePage(Integer currPid);

	/**
	 * 查询当前页的下一页
	 */
	public Page getNextPage(Integer currPid);

	/**
	 * 批量保存答案
	 */
	public void saveAnswers(List<Answer> list);

	/**
	 * 查询指定调查的所有问题
	 */
	public List<Question> getQuestions(Integer sid);

	/**
	 * 查询指定调查的的所有答案
	 */
	public List<Answer> getAnswers(Integer sid);

}
