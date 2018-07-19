package com.hwagain.org.job.dto;

import java.io.Serializable;
import java.util.Date;

import com.hwagain.framework.core.dto.BaseDto;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
public class OrgJobProDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 部门ID
     */
	private String deptId;
    /**
     * 部门版本
     */
	private Integer deptVersion;
    /**
     * 职务名称
     */
	private String jobName;
    /**
     * 职务编码，后台生成
     */
	private String jobCode;
    /**
     * 职务类型，0：普通职务，1：主要职务，一个部门职能有一个主要职务
     */
	private String jobType;
    /**
     * 直接领导，上级职务ID
     */
	private String parentId;
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
	
	private String managDepts;
	
	/**
     * 是否管理岗位
     */
	private Integer isManage;
    /**
     * 备注
     */
	private String remark;
	
	/**
     * 序号
     */
	private Integer orderValue;
	
    /**
     * 最大编制
     */
	private Integer maxCompile;
    /**
     * 最大人数
     */
	private Integer maxPeople;

	/**
	 * 直接上级名称
	 */
	private String parentName;
	
	/**
	 * 岗位当前人数
	 */
	private Integer currentNum;

	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getMaxCompile() {
		return maxCompile;
	}

	public void setMaxCompile(Integer maxCompile) {
		this.maxCompile = maxCompile;
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


	public Integer getIsManage() {
		return isManage;
	}

	public void setIsManage(Integer isManage) {
		this.isManage = isManage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getManagDepts() {
		return managDepts;
	}

	public void setManagDepts(String managDepts) {
		this.managDepts = managDepts;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptVersion() {
		return deptVersion;
	}

	public void setDeptVersion(Integer deptVersion) {
		this.deptVersion = deptVersion;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
