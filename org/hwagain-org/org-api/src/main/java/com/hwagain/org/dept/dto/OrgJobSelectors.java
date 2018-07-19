package com.hwagain.org.dept.dto;

public class OrgJobSelectors {

	private String id;
	
	private String name;
	
	private String shortName;
	
	private String parentId;
	
	private String code;
	
	private String dataType;
	
	private String reamrk;
	
	public OrgJobSelectors() {
		super();
	}

	public OrgJobSelectors(String id, String name, String shortName, String parentId, String code, String dataType,
			String reamrk) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.parentId = parentId;
		this.code = code;
		this.dataType = dataType;
		this.reamrk = reamrk;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
}
