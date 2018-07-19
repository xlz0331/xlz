package com.hwagain.org.dept.dto;

import java.io.Serializable;

public class OrgDeptJobDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userCode;
	
	private String userName;

	private String nodeCode;
	
	private String nodeName;
	
	private String longNodeName;
	
	private String jobCode;
	
	private String jobName;
	
	private Boolean isManage;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLongNodeName() {
		return longNodeName;
	}

	public void setLongNodeName(String longNodeName) {
		this.longNodeName = longNodeName;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Boolean getIsManage() {
		return isManage;
	}

	public void setIsManage(Boolean isManage) {
		this.isManage = isManage;
	}
}
