package com.dy.surveypark.model.statistics;

import java.io.Serializable;

/**
 * 选项统计模型
 * 
 * @author DY
 * @version 创建时间：2015年10月26日 下午8:33:16
 */
public class OptionStatisticsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// 选项标签
	private String optionLable;
	// 选项索引
	private int optionIndex;

	private String matrixRowLable;
	private int matrixRowIndex;

	private String matrixColLable;
	private int matrixColIndex;

	private String matrixSelectLabel;
	private int matrixSelectIndex;
	// 选项的回答人数
	private int count;

	public String getOptionLable() {
		return optionLable;
	}

	public void setOptionLable(String optionLable) {
		this.optionLable = optionLable;
	}

	public int getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}

	public String getMatrixRowLable() {
		return matrixRowLable;
	}

	public void setMatrixRowLable(String matrixRowLable) {
		this.matrixRowLable = matrixRowLable;
	}

	public int getMatrixRowIndex() {
		return matrixRowIndex;
	}

	public void setMatrixRowIndex(int matrixRowIndex) {
		this.matrixRowIndex = matrixRowIndex;
	}

	public String getMatrixColLable() {
		return matrixColLable;
	}

	public void setMatrixColLable(String matrixColLable) {
		this.matrixColLable = matrixColLable;
	}

	public int getMatrixColIndex() {
		return matrixColIndex;
	}

	public void setMatrixColIndex(int matrixColIndex) {
		this.matrixColIndex = matrixColIndex;
	}

	public String getMatrixSelectLabel() {
		return matrixSelectLabel;
	}

	public void setMatrixSelectLabel(String matrixSelectLabel) {
		this.matrixSelectLabel = matrixSelectLabel;
	}

	public int getMatrixSelectIndex() {
		return matrixSelectIndex;
	}

	public void setMatrixSelectIndex(int matrixSelectIndex) {
		this.matrixSelectIndex = matrixSelectIndex;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
