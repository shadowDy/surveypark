package com.dy.surveypark.struts2.action;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.statistics.OptionStatisticsModel;
import com.dy.surveypark.model.statistics.QuestionStatisticsModel;
import com.dy.surveypark.service.StatisticsService;

/**
 * MatrixStatisticAction
 * 
 * @author DY
 * @version 创建时间：2015年10月28日 下午4:12:31
 */
@Controller
@Scope("prototype")
public class MatrixStatisticsAction extends BaseAction<Question> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private StatisticsService ss;

	private Integer qid;
	private QuestionStatisticsModel qsm;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public QuestionStatisticsModel getQsm() {
		return qsm;
	}

	public void setQsm(QuestionStatisticsModel qsm) {
		this.qsm = qsm;
	}

	public String execute() {
		// 先进行统计
		this.qsm = ss.statistics(qid);
		return qsm.getQuestion().getQuestionType() + "";
	}

	/**
	 * 计算每个选项的统计结果
	 */
	public String getScale(int rowIndex, int colIndex) {
		// 问题回答人数
		int qcount = qsm.getCount();
		// 选项回答人数
		int ocount = 0;
		for (OptionStatisticsModel osm : qsm.getOsms()) {
			if (osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex) {
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if (qcount != 0) {
			scale = (float) ocount / qcount * 100;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,###.00");
		return "" + ocount + "(" + df.format(scale) + "%)";

	}

	/**
	 * 计算每个选项的统计结果
	 */
	// 颜色数组 rgb
	private String[] colors = { "#ff0000", "#00ff00", "#0000ff", "#ffff00",
			"#ff00ff", "#00ffff" };

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	/**
	 * 获得百分比的整数部分，作为选项的显示长度
	 */
	public int getPercent(int rowIndex, int colIndex, int optIndex) {
		// 问题回答人数
		int qcount = qsm.getCount();
		// 选项回答人数
		int ocount = 0;
		for (OptionStatisticsModel osm : qsm.getOsms()) {
			if (osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex
					&& osm.getMatrixSelectIndex() == optIndex) {
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if (qcount != 0) {
			scale = (float) ocount / qcount * 100;
		}
		return (int) scale;
	}

	/**
	 * 矩阵式多选，查看每个选项的人数
	 */
	public String getScale(int rowIndex, int colIndex, int optIndex) {
		// 问题回答人数
		int qcount = qsm.getCount();
		// 选项回答人数
		int ocount = 0;
		for (OptionStatisticsModel osm : qsm.getOsms()) {
			if (osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex
					&& osm.getMatrixSelectIndex() == optIndex) {
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if (qcount != 0) {
			scale = (float) ocount / qcount * 100;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,###.00");
		return "" + ocount + "(" + df.format(scale) + "%)";

	}

}
