package com.hwagain.org.user.dto;

import java.io.Serializable;

public class OrgUserJobDeptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
	private String emplid;
    /**
     * 名称
     */
	private String name;
    /**
     * 性别
     */
	private String sex;
    /**
     * 身份证
     */
	private String nid;
	
	/**
     * 邮箱
     */
	private String email;
    /**
     * 账号
     */
	private String account;
    /**
     * 电话
     */
	private String phone;
	/**
     * 人员所在部门编码
     */
	private String deptId;
	/**
     * 人员所在部门简称
     */
	private String deptNameShort;
	/**
     * 职位编号
     */
	private String positionCode;
	/**
     * 职位名称
     */
	private String positionName;
	
	public String getEmplid() {
		return emplid;
	}
	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNameShort() {
		return deptNameShort;
	}
	public void setDeptNameShort(String deptNameShort) {
		this.deptNameShort = deptNameShort;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
