package com.hwagain.org.dept.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hwagain.framework.core.dto.BaseDto;
import com.hwagain.org.job.dto.OrgJobProDto;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-16
 */
public class OrgDeptProDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID 
     */
	private String fdId;
    /**
     * 生效时间
     */
	private Date effdt;
    /**
     * 部门编号
     */
	private String deptid;
    /**
     * 部门全称
     */
	private String descr;
    /**
     * 部门简称
     */
	private String descrshort;
    /**
     * 所属公司ID
     */
	private String company;
    /**
     * 最大人数（适用于岗位）
     */
	private Integer maxPeople;
    /**
     * 部门层级ID
     */
	private String hierarchyId;
    /**
     * 上级部门ID
     */
	private String parentId;
    /**
     * 上级部门编号
     */
	private String parentDEPTID;
    /**
     * 当前版本
     */
	private Integer version;
    /**
     * 是否删除
     */
	private Integer isDelete;
    /**
     * 修改者id
     */
	private String docLastUpdateId;
    /**
     * 修改时间
     */
	private Date docLastUpdateTime;
    /**
     * 创建时间
     */
	private Date docCreateTime;
    /**
     * 创建者ID
     */
	private String docCreatorId;
    /**
     * 类型ID对应org_type表
     */
	
	private String typeCode;
	
	private List<OrgJobProDto> jobs = new ArrayList<>();
	
	private List<OrgDeptProDto> children = new ArrayList<>();
	


	public List<OrgJobProDto> getJobs() {
		return jobs;
	}

	public void setJobs(List<OrgJobProDto> jobs) {
		this.jobs = jobs;
	}

	public List<OrgDeptProDto> getChildren() {
		return children;
	}

	public void setChildren(List<OrgDeptProDto> children) {
		this.children = children;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public Date getEffdt() {
		return effdt;
	}

	public void setEffdt(Date effdt) {
		this.effdt = effdt;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescrshort() {
		return descrshort;
	}

	public void setDescrshort(String descrshort) {
		this.descrshort = descrshort;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentDEPTID() {
		return parentDEPTID;
	}

	public void setParentDEPTID(String parentDEPTID) {
		this.parentDEPTID = parentDEPTID;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getDocLastUpdateId() {
		return docLastUpdateId;
	}

	public void setDocLastUpdateId(String docLastUpdateId) {
		this.docLastUpdateId = docLastUpdateId;
	}

	public Date getDocLastUpdateTime() {
		return docLastUpdateTime;
	}

	public void setDocLastUpdateTime(Date docLastUpdateTime) {
		this.docLastUpdateTime = docLastUpdateTime;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
}
