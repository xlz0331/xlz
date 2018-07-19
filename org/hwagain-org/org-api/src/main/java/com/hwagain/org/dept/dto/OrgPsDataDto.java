package com.hwagain.org.dept.dto;

import java.io.Serializable;

public class OrgPsDataDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String nodeCode;
	
	private String deptCode;
	
	private String nodeCodeEnd;
	
	private String parentNodeCode;
	
	private String parentDeptCode;
	
	public OrgPsDataDto() {
		super();
	}

	public OrgPsDataDto(String nodeCode, String deptCode, String nodeCodeEnd, String parentNodeCode,
			String parentDeptCode) {
		super();
		this.nodeCode = nodeCode;
		this.deptCode = deptCode;
		this.nodeCodeEnd = nodeCodeEnd;
		this.parentNodeCode = parentNodeCode;
		this.parentDeptCode = parentDeptCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getNodeCodeEnd() {
		return nodeCodeEnd;
	}

	public void setNodeCodeEnd(String nodeCodeEnd) {
		this.nodeCodeEnd = nodeCodeEnd;
	}

	public String getParentNodeCode() {
		return parentNodeCode;
	}

	public void setParentNodeCode(String parentNodeCode) {
		this.parentNodeCode = parentNodeCode;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}
}
