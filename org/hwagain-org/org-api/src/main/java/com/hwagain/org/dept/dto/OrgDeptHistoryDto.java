package com.hwagain.org.dept.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hwagain.framework.core.dto.BaseDto;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-16
 */
public class OrgDeptHistoryDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
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
	private String typeId;
    /**
     * 最大人数（适用于岗位）
     */
	private Integer maxPeople;
    /**
     * 序号
     */
	private Integer orderValue;
	
	private String typeCode;
	
	private String log;
	
	private List<OrgDeptHistoryDto> children = new ArrayList<>();


	public List<OrgDeptHistoryDto> getChildren() {
		return children;
	}

	public void setChildren(List<OrgDeptHistoryDto> children) {
		this.children = children;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
