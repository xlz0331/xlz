package com.hwagain.org.dept.dto;

import java.io.Serializable;

public class OrgRegroup implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String parentId;
	
	private String typeCode;
	
	private String deptIds;
	
	private String deptNames;
	
	private String josName;
	
	public OrgRegroup() {
		super();
	}

	public OrgRegroup(String id, String name, String parentId,String typeCode) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.typeCode = typeCode;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public String getJosName() {
		return josName;
	}

	public void setJosName(String josName) {
		this.josName = josName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
