package com.hwagain.org.log.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class OrgClassJobLogDto {

	private String parentName;
	
	private String typeCode;
	
	private List logs = new ArrayList<>();

	public List getLogs() {
		return logs;
	}

	public void setLogs(List logs) {
		this.logs = logs;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
