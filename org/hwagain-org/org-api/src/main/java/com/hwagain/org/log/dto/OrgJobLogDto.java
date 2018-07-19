package com.hwagain.org.log.dto;

public class OrgJobLogDto {

	private String fdId;
	
	private String type;
	
	private String typeCode;
	
	private String beforName;
	
	private String afterName;
	
	private String deptName;
	
	private String beforSuper;
	
	private String afterSuper;
	
	public OrgJobLogDto() {
		super();
	}

	public OrgJobLogDto(String fdId, String type, String typeCode, String beforName, String afterName, String deptName,
			String beforSuper, String afterSuper) {
		super();
		this.fdId = fdId;
		this.type = type;
		this.typeCode = typeCode;
		this.beforName = beforName;
		this.afterName = afterName;
		this.deptName = deptName;
		this.beforSuper = beforSuper;
		this.afterSuper = afterSuper;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getBeforName() {
		return beforName;
	}

	public void setBeforName(String beforName) {
		this.beforName = beforName;
	}

	public String getAfterName() {
		return afterName;
	}

	public void setAfterName(String afterName) {
		this.afterName = afterName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBeforSuper() {
		return beforSuper;
	}

	public void setBeforSuper(String beforSuper) {
		this.beforSuper = beforSuper;
	}

	public String getAfterSuper() {
		return afterSuper;
	}

	public void setAfterSuper(String afterSuper) {
		this.afterSuper = afterSuper;
	}
}
