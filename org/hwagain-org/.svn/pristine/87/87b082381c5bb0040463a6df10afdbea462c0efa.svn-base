package com.hwagain.org.dept.entity;

import com.hwagain.framework.sys.comons.model.BaseModel;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-16
 */
@TableName("org_dept_pro")
public class OrgDeptPro extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID 
     */
	@TableId("fd_id")
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
	@TableField("max_people")
	private Integer maxPeople;
    /**
     * 部门层级ID
     */
	@TableField("hierarchy_id")
	private String hierarchyId;
    /**
     * 上级部门ID
     */
	@TableField("parent_id")
	private String parentId;
    /**
     * 上级部门编号
     */
	@TableField("parent_DEPTID")
	private String parentDEPTID;
    /**
     * 当前版本
     */
	private Integer version;
    /**
     * 是否删除
     */
	@TableField("is_delete")
	private Integer isDelete;
    /**
     * 修改者id
     */
	@TableField("doc_last_update_id")
	private String docLastUpdateId;
    /**
     * 修改时间
     */
	@TableField("doc_last_update_time")
	private Date docLastUpdateTime;
    /**
     * 创建时间
     */
	@TableField("doc_create_time")
	private Date docCreateTime;
    /**
     * 创建者ID
     */
	@TableField("doc_creator_id")
	private String docCreatorId;
	
	@TableField("type_code")
	private String typeCode;
	
    /**
     * 序号
     */
	@TableField("order_value")
	private Integer orderValue;


	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
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
