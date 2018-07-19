package com.hwagain.org.dept.dto;

import java.util.ArrayList;
import java.util.List;

import com.hwagain.org.user.dto.OrgUserJobDto;

@SuppressWarnings("rawtypes")
public class OrgExcel implements Cloneable{

	private String id;
	
	private String dataid;
	
	private String label;
	
	private String job;
	
	private String jobId;
	
	private String json;
	
	private String parentId;
	
	private String parentCode;
	
	private String typeCode;
	
	private String remark;
	
	private String maxCompile;
	
	private String maxPeople;
	
	private String code;
	
	private String companyCode;
	
	private String type;
	
	private List logs;
	
	private List<OrgUserJobDto> user = new ArrayList<>();
	
	private List<OrgExcel> children = new ArrayList<>();
	
	public List getLogs() {
		return logs;
	}

	public void setLogs(List logs) {
		this.logs = logs;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public OrgExcel() {
		super();
	}

	public OrgExcel(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	
	public OrgExcel(String id, String label, String parentId) {
		super();
		this.id = id;
		this.label = label;
		this.parentId = parentId;
	}
	
	public OrgExcel(String id, String label, String parentId,String companyCode) {
		super();
		this.id = id;
		this.label = label;
		this.parentId = parentId;
		this.companyCode = companyCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public List<OrgExcel> getChildren() {
		return children;
	}

	public void setChildren(List<OrgExcel> children) {
		this.children = children;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<OrgUserJobDto> getUser() {
		return user;
	}

	public void setUser(List<OrgUserJobDto> user) {
		this.user = user;
	}

	public String getMaxCompile() {
		return maxCompile;
	}

	public void setMaxCompile(String maxCompile) {
		this.maxCompile = maxCompile;
	}

	public String getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(String maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		OrgExcel orgExcel = (OrgExcel)super.clone();
		return orgExcel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
