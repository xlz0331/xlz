package com.hwagain.org.job.entity;

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
 * @since 2018-04-27
 */
@TableName("org_job_pro")
public class OrgJobPro extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private String fdId;
    /**
     * 部门ID
     */
	@TableField("dept_id")
	private String deptId;
    /**
     * 部门编号
     */
	@TableField("dept_code")
	private String deptCode;
    /**
     * 部门版本
     */
	@TableField("dept_version")
	private Integer deptVersion;
    /**
     * 职务名称
     */
	@TableField("job_name")
	private String jobName;
    /**
     * 职务编码，后台生成
     */
	@TableField("job_code")
	private String jobCode;
    /**
     * 职务类型，0：普通职务，1：主要职务，一个部门职能有一个主要职务
     */
	@TableField("job_type")
	private String jobType;
    /**
     * 直接领导，上级职务ID
     */
	@TableField("parent_id")
	private String parentId;
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
    /**
     * 管理部门/工厂ids
     */
	@TableField("manag_depts")
	private String managDepts;
    /**
     * 所属公司
     */
	private String company;
    /**
     * 是否管理岗位
     */
	@TableField("is_manage")
	private Integer isManage;
    /**
     * 备注
     */
	private String remark;
    /**
     * 最大编制
     */
	@TableField("max_compile")
	private Integer maxCompile;
    /**
     * 最大人数
     */
	@TableField("max_people")
	private Integer maxPeople;
	
	/**
     * 序号
     */
	@TableField("max_people")
	private Integer orderValue;


	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public String getManagDepts() {
		return managDepts;
	}

	public void setManagDepts(String managDepts) {
		this.managDepts = managDepts;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

}
