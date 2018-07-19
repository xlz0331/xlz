package com.hwagain.org.log.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class OrgLogAuditDto {
	
	private String name;
	
	private String typeCode;

	private List gcjoblogs;
	
	private List sbgyjoblogs;
	
	private List cjlogs;
	
	private List<OrgCjJobLogDto> cjjoblos = new ArrayList<>();
	
	private List<OrgClassLogDto> bjlogs = new ArrayList<>();
	
	private List<OrgClassJobLogDto> bjjoblogs = new ArrayList<>();
	
	private List ywzlogs = new ArrayList<>();
	
	private List<OrgYwzJobLogDto> ywzjoblogs = new ArrayList<>();
	
	private List<OrgGdLogDto> gdlogs = new ArrayList<>();
	
	private List<OrgGdJobLogDto> gdjoblogs = new ArrayList<>();

	public List<OrgCjJobLogDto> getCjjoblos() {
		return cjjoblos;
	}

	public void setCjjoblos(List<OrgCjJobLogDto> cjjoblos) {
		this.cjjoblos = cjjoblos;
	}

	public List<OrgClassJobLogDto> getBjjoblogs() {
		return bjjoblogs;
	}

	public void setBjjoblogs(List<OrgClassJobLogDto> bjjoblogs) {
		this.bjjoblogs = bjjoblogs;
	}

	public List<OrgYwzJobLogDto> getYwzjoblogs() {
		return ywzjoblogs;
	}

	public void setYwzjoblogs(List<OrgYwzJobLogDto> ywzjoblogs) {
		this.ywzjoblogs = ywzjoblogs;
	}

	public List<OrgGdJobLogDto> getGdjoblogs() {
		return gdjoblogs;
	}

	public void setGdjoblogs(List<OrgGdJobLogDto> gdjoblogs) {
		this.gdjoblogs = gdjoblogs;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public List getGcjoblogs() {
		return gcjoblogs;
	}

	public void setGcjoblogs(List gcjoblogs) {
		this.gcjoblogs = gcjoblogs;
	}

	public List getSbgyjoblogs() {
		return sbgyjoblogs;
	}

	public void setSbgyjoblogs(List sbgyjoblogs) {
		this.sbgyjoblogs = sbgyjoblogs;
	}

	public List getCjlogs() {
		return cjlogs;
	}

	public void setCjlogs(List cjlogs) {
		this.cjlogs = cjlogs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrgClassLogDto> getBjlogs() {
		return bjlogs;
	}

	public void setBjlogs(List<OrgClassLogDto> bjlogs) {
		this.bjlogs = bjlogs;
	}

	public List getYwzlogs() {
		return ywzlogs;
	}

	public void setYwzlogs(List ywzlogs) {
		this.ywzlogs = ywzlogs;
	}

	public List<OrgGdLogDto> getGdlogs() {
		return gdlogs;
	}

	public void setGdlogs(List<OrgGdLogDto> gdlogs) {
		this.gdlogs = gdlogs;
	}
}
