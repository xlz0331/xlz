package com.hwagain.org.register.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
public class RegPersonalWorkDto extends RegPageVoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	private String fdId;
    /**
     * 人员id
     */
	private String personalId;
    /**
     * 开始时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startdate;
    /**
     * 结束时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date enddate;
    /**
     * 工作单位 (统招学历
     非同招学历)
     */
	private String company;
    /**
     * 电话
     */
	private String phone;
    /**
     * 职务
     */
	private String position;
    /**
     * 城市
     */
	private String city;
    /**
     * 年薪
     */
	private String salary;
    /**
     * 证明人姓名
     */
	private String proveName;
    /**
     * 证明人部门
     */
	private String proveDept;
    /**
     * 证明人职务
     */
	private String provePosi;
    /**
     * 证明人电话
     */
	private String provePhone;
    /**
     * 是否删除 (1:已删除)
     */
	private Integer isDelete;
    /**
     * 创建人
     */
	private String docCreateId;
    /**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date docCreateTime;
    /**
     * 最后修改人
     */
	private String docLastUpdateId;
    /**
     * 最后修改时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date docLastUpdateTime;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getProveName() {
		return proveName;
	}

	public void setProveName(String proveName) {
		this.proveName = proveName;
	}

	public String getProveDept() {
		return proveDept;
	}

	public void setProveDept(String proveDept) {
		this.proveDept = proveDept;
	}

	public String getProvePosi() {
		return provePosi;
	}

	public void setProvePosi(String provePosi) {
		this.provePosi = provePosi;
	}

	public String getProvePhone() {
		return provePhone;
	}

	public void setProvePhone(String provePhone) {
		this.provePhone = provePhone;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getDocCreateId() {
		return docCreateId;
	}

	public void setDocCreateId(String docCreateId) {
		this.docCreateId = docCreateId;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
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

}
