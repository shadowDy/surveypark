package com.dy.surveypark.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dy.surveypark.dao.BaseDao;
import com.dy.surveypark.model.Answer;
import com.dy.surveypark.model.Page;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.Role;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.model.User;
import com.dy.surveypark.service.SurveyService;
import com.dy.surveypark.util.DataUtil;

/**
 * SurveyService实现
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Resource(name = "surveyDao")
	private BaseDao<Survey> surveyDao;

	@Resource(name = "pageDao")
	private BaseDao<Page> pageDao;

	@Resource(name = "questionDao")
	private BaseDao<Question> questionDao;

	@Resource(name = "answerDao")
	private BaseDao<Answer> answerDao;

	/**
	 * 查询调查集合
	 */
	public List<Survey> findMySurveys(User user) {
		String hql = "from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}

	/**
	 * 新建调查
	 */
	public Survey newSurvey(User user) {
		Survey s = new Survey();
		Page p = new Page();

		// 设置关联关系
		s.setUser(user);
		p.setSurvey(s);
		s.getPages().add(p);

		surveyDao.saveEntity(s);
		pageDao.saveEntity(p);
		return s;
	}

	/**
	 * 按照id查询Survey
	 */
	public Survey getSurvey(Integer sid) {
		return surveyDao.getEntity(sid);
	}

	/**
	 * 按照id查询Survey,同时携带所有的孩子
	 */
	public Survey getSurveyWithChildren(Integer sid) {
		Survey s = this.getSurvey(sid);
		// 强行初始化pages和questions集合
		for (Page p : s.getPages()) {
			p.getQuestions().size();
		}
		return s;
	}

	/**
	 * 更新调查
	 */
	@Override
	public void updateSurvey(Survey s) {
		surveyDao.updateEntity(s);

	}

	/**
	 * 保存/更新页面
	 */
	@Override
	public void saveOrUpdatePage(Page page) {
		pageDao.saveOrUpdateEntity(page);
	}

	/**
	 * 按照ID 查询页面
	 */
	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	/**
	 * 保存/更新问题
	 * 
	 * @param quesion
	 */
	@Override
	public void saveOrUpdateQuestion(Question question) {
		questionDao.saveOrUpdateEntity(question);

	}

	/**
	 * 删除问题,同时删除答案
	 * 
	 * @param qId
	 */
	@Override
	public void deleteQuestion(Integer qid) {
		// 1.删除answers
		String hql = "delete from Answer a where a.questionId = ?";
		answerDao.batchEntityByHQL(hql, qid);
		// 2.删除问题
		hql = "delete from Question q where q.id = ?";
		questionDao.batchEntityByHQL(hql, qid);
	}

	/**
	 * 删除页面，同时删除问题和答案
	 * 
	 * @param pid
	 */
	@Override
	public void deletePage(Integer pid) {
		// 删除answer
		String hql = "delete from Answer a where a.questionId in(select q.id from Question q where q.page.id =?)";
		answerDao.batchEntityByHQL(hql, pid);
		// 删除问题
		hql = "delete from Question q where q.page.id = ?";
		questionDao.batchEntityByHQL(hql, pid);
		// 删除页面
		hql = "delete from Page p where p.id = ?";
		pageDao.batchEntityByHQL(hql, pid);
	}

	/**
	 * 删除调查，同时删除页面，问题，答案
	 * 
	 * @param sid
	 */
	@Override
	public void deleteSurvey(Integer sid) {
		// 删除answer
		String hql = "delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHQL(hql, sid);
		// 删除问题
		// hibernete 在写操作当中，不允许两级以上的链接
		/* hql = "delete from Question q where q.page.survey.id = ?"; */
		hql = "delete from Question q where q.page.id in(select p.id from Page p where p.survey.id=?)";
		questionDao.batchEntityByHQL(hql, sid);
		// 删除页面
		hql = "delete from Page p where p.survey.id = ?";
		pageDao.batchEntityByHQL(hql, sid);
		// 删除调查
		hql = "delete from Survey s where s.id = ?";
		surveyDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * 编辑问题
	 */
	public Question getQuestion(Integer qid) {

		return questionDao.getEntity(qid);
	}

	/**
	 * 清除调查中的所有答案
	 */
	public void clearAnswer(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ? ";
		surveyDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * 打开关闭调查
	 */
	public void toggleStatus(Integer sid) {
		// hql 不支持 取反 操作
		// String hql =
		// "update Survey s set s.closed = !s.closed where s.id = ?";
		// surveyDao.batchEntityByHQL(hql, sid);
		Survey s = this.getSurvey(sid);
		String hql = "update Survey s set s.closed = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, !s.isClosed(), sid);
	}

	/**
	 * 更新Logo的路径
	 */
	public void updateLogoPhotoPath(Integer sid, String path) {
		String hql = "update Survey s set s.logoPhotoPath = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, path, sid);
	}

	/**
	 * 查询调查集合， 携带pages
	 */
	public List<Survey> getSurverWithPages(User user) {
		String hql = "from Survey s where s.user.id = ?";
		List<Survey> list = surveyDao.findEntityByHQL(hql, user.getId());
		for (Survey s : list) {
			s.getPages().size();
		}
		return list;
	}

	/**
	 * 进行页面移动/复制
	 */
	public void moveOrCopyPage(Integer srcPid, Integer targPid, int pos) {
		// 获得源调查和目标调查
		Page srcPage = this.getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targPage = this.getPage(targPid);
		Survey targSurvey = targPage.getSurvey();
		// 判断移动/复制
		if (srcSurvey.getId().equals(targSurvey.getId())) {
			setOrderno(srcPage, targPage, pos);
		} else {
			// 强行初始化问题集合，负责深度复制页面对象没有问题集合
			srcPage.getQuestions().size();
			// 深度复制
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			// 设置页面和目标调查关联
			copyPage.setSurvey(targSurvey);
			// 保存页面
			pageDao.saveEntity(copyPage);
			// 保存问题集合
			for (Question q : copyPage.getQuestions()) {
				questionDao.saveEntity(q);
			}
			setOrderno(copyPage, targPage, pos);
		}
	}

	/**
	 * 设置页序
	 */
	private void setOrderno(Page srcPage, Page targPage, int pos) {
		// 之前
		if (pos == 0) {
			// 目标页是否首页
			if (isFirstPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() - 0.01f);
			} else {
				// 取得目标页的前一页
				Page prePage = getPrePage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + prePage
						.getOrderno()) / 2);
			}
		}
		// 之后
		else {
			// 目标页是否尾页
			if (isLastPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() + 0.01f);
			} else {
				// 取得目标页的前一页
				Page nextPage = getNextPage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + nextPage
						.getOrderno()) / 2);
			}
		}
	}

	/**
	 * 查询指定页面的下一页
	 */
	private Page getNextPage(Page targPage) {
		// 升序排列，使前一页在返回集合的第一位
		String hql = "from Page p where p.survey.id = ? and p.orderno > ? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getSurvey()
				.getId(), targPage.getOrderno());

		return list.get(0);
	}

	/**
	 * 判断指定页面是否是所在调查的尾页
	 */
	private boolean isLastPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno > ?";
		Long count = (Long) pageDao.uniqueResult(hql, targPage.getSurvey()
				.getId(), targPage.getOrderno());
		return count == 0;
	}

	/**
	 * 查询指定页面的上一页
	 */
	private Page getPrePage(Page targPage) {
		// 降序排列，使前一页在返回集合的第一位
		String hql = "from Page p where p.survey.id = ? and p.orderno < ? order by p.orderno desc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getSurvey()
				.getId(), targPage.getOrderno());

		return list.get(0);
	}

	/**
	 * 判断指定页面是否是所在调查的首页
	 */
	private boolean isFirstPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno < ?";
		Long count = (Long) pageDao.uniqueResult(hql, targPage.getSurvey()
				.getId(), targPage.getOrderno());
		return count == 0;
	}

	/**
	 * 查询所有可见的调查
	 */
	public List<Survey> findAllAvailableSurveys() {
		String hql = "from Survey s where s.closed = ?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	/**
	 * 查询调查的首页
	 */
	public Page getfirstPage(Integer sid) {
		String hql = "from Page p where p.survey.id = ? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, sid);
		Page p = list.get(0);
		p.getQuestions().size();// 初始化问题，防止懒加载
		p.getSurvey().getTitle();// 初始化调查对象，防止懒加载
		return p;
	}

	/**
	 * 查询当前页的上一页
	 */
	public Page getPrePage(Integer currPid) {
		Page p = this.getPage(currPid);
		p = this.getPrePage(p);
		// 防止懒加载
		p.getQuestions().size();
		return p;
	}

	/**
	 * 查询当前页的下一页
	 */
	public Page getNextPage(Integer currPid) {
		Page p = this.getPage(currPid);
		p = this.getNextPage(p);
		// 防止懒加载
		p.getQuestions().size();
		return p;
	}

	/**
	 * 批量保存答案
	 */
	public void saveAnswers(List<Answer> list) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
		for (Answer a : list) {
			a.setUuid(uuid);
			a.setAnswerTime(date);
			answerDao.saveEntity(a);
		}
	}

	/**
	 * 查询指定调查的所有问题
	 */
	public List<Question> getQuestions(Integer sid) {
		String hql = "from Question q where q.page.survey.id = ?";
		return questionDao.findEntityByHQL(hql, sid);
	}

	/**
	 * 查询指定调查的的所有答案
	 */
	public List<Answer> getAnswers(Integer sid) {
		String hql = "from Answer a where a.surveyId = ? order by a.uuid asc";
		return answerDao.findEntityByHQL(hql, sid);
	}

}
