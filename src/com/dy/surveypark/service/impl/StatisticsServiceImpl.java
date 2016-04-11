package com.dy.surveypark.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dy.surveypark.dao.BaseDao;
import com.dy.surveypark.model.Answer;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.statistics.OptionStatisticsModel;
import com.dy.surveypark.model.statistics.QuestionStatisticsModel;
import com.dy.surveypark.service.StatisticsService;

/**
 * 统计服务实现类
 * 
 * @author DY
 * @version 创建时间：2015年10月26日 下午8:45:23
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Resource(name = "questionDao")
	private BaseDao<Question> questionDao;
	@Resource(name = "answerDao")
	private BaseDao<Answer> answerDao;

	@Override
	public QuestionStatisticsModel statistics(Integer qid) {
		Question question = questionDao.getEntity(qid);
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		qsm.setQuestion(question);

		// 回答问题的总人数
		String qhql = "select count(*) from Answer a where a.questionId = ? ";
		Long qcount = (long) answerDao.uniqueResult(qhql, qid);
		qsm.setCount(qcount.intValue());

		// 选择 某个答案的人的个数
		String ohql = "select count(*) from Answer a where a.questionId = ? "
				+ "and concat (',',a.answersIds,',') like ? ";
		// 答案人数
		Long ocount = null;

		// 统计每个选项的情况
		int qt = question.getQuestionType();
		switch (qt) {
		// 非矩阵
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			String[] arr = question.getOptionArr();
			OptionStatisticsModel osm = null;
			for (int i = 0; i < arr.length; i++) {
				osm = new OptionStatisticsModel();
				osm.setOptionIndex(i);
				osm.setOptionLable(arr[i]);
				ocount = (Long) answerDao.uniqueResult(ohql, qid, "%," + i
						+ ",%");
				osm.setCount(ocount.intValue());
				qsm.getOsms().add(osm);
			}
			// other 选项
			if (question.isOther()) {
				osm = new OptionStatisticsModel();
				osm.setOptionLable("其他");

				ocount = (Long) answerDao.uniqueResult(ohql, qid, "%other%");
				osm.setCount(ocount.intValue());
				qsm.getOsms().add(osm);
			}
			break;
		// 矩阵式
		case 6:
		case 7:
		case 8:
			String[] rows = question.getMatrixRowTitleArr();
			String[] cols = question.getMatrixRowTitleArr();
			String[] opts = question.getMatrixSelectOptionArr();
			for (int i = 0; i < rows.length; i++) {
				for (int j = 0; j < cols.length; j++) {
					// 矩阵式单选或者复选按钮
					if (qt != 8) {
						osm = new OptionStatisticsModel();
						osm.setMatrixRowIndex(i);
						osm.setMatrixRowLable(rows[i]);
						osm.setMatrixColIndex(j);
						osm.setMatrixColLable(cols[j]);
						ocount = (Long) answerDao.uniqueResult(ohql, qid, "%,"
								+ i + "_" + j + ",%");
						osm.setCount(ocount.intValue());
						qsm.getOsms().add(osm);
					}
					// 下拉列表
					else {
						for (int k = 0; k < opts.length; k++) {
							osm = new OptionStatisticsModel();
							osm.setMatrixRowIndex(i);
							osm.setMatrixRowLable(rows[i]);
							osm.setMatrixColIndex(j);
							osm.setMatrixColLable(cols[j]);
							osm.setMatrixSelectIndex(k);
							osm.setMatrixSelectLabel(opts[k]);
							ocount = (Long) answerDao.uniqueResult(ohql, qid,
									"%," + i + "_" + j + "_" + k + ",%");
							osm.setCount(ocount.intValue());
							qsm.getOsms().add(osm);
						}
					}
				}
			}
			break;
		}
		return qsm;
	}
}
