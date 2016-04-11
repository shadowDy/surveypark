package com.dy.surveypark.model;

/**
 * 权限
 * 
 * @author DY
 * @version 创建时间：2015年10月31日 下午4:25:42
 */
public class Right extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String rightName = "未命名";
	private String rightUrl;
	private String rightDesc;
	private long rightCode;// 权限码:1<<n
	private int rightPos;// 权限位，相当于对权限进行分组，从0开始
	// 是否是公共资源
	private boolean common;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}

	public String getRightDesc() {
		return rightDesc;
	}

	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}

	public long getRightCode() {
		return rightCode;
	}

	public void setRightCode(long rightCode) {
		this.rightCode = rightCode;
	}

	public int getRightPos() {
		return rightPos;
	}

	public void setRightPos(int rightPos) {
		this.rightPos = rightPos;
	}

	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

}
