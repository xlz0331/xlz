package com.hwagain.org.job.dto;


import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author huangyj
 * @since 2018-06-13
 */
public class OrgJobDesignDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 所属公司
     */
	private String company;
    /**
     * 定额定编版本号
     */
	private Integer version;
    /**
     * 职位名称
     */
	private String jobName;
    /**
     * 类型对应编码
     */
	private String typeCode;
	private Integer maxCompileBefore;
	private Integer maxPeopleBefore;
    /**
     * 最大编制
     */
	private Integer maxCompileNew;
    /**
     * 最大人数
     */
	private Integer maxPeopleNew;
    /**
     * 审核状态：0，未提交审核；1，正在审核；2，审核通过；3，审核未通过
     */
	private String status;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getMaxCompileBefore() {
		return maxCompileBefore;
	}

	public void setMaxCompileBefore(Integer maxCompileBefore) {
		this.maxCompileBefore = maxCompileBefore;
	}

	public Integer getMaxPeopleBefore() {
		return maxPeopleBefore;
	}

	public void setMaxPeopleBefore(Integer maxPeopleBefore) {
		this.maxPeopleBefore = maxPeopleBefore;
	}

	public Integer getMaxCompileNew() {
		return maxCompileNew;
	}

	public void setMaxCompileNew(Integer maxCompileNew) {
		this.maxCompileNew = maxCompileNew;
	}

	public Integer getMaxPeopleNew() {
		return maxPeopleNew;
	}

	public void setMaxPeopleNew(Integer maxPeopleNew) {
		this.maxPeopleNew = maxPeopleNew;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
