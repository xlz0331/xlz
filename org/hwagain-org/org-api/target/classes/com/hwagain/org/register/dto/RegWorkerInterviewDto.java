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
public class RegWorkerInterviewDto extends RegPageVoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * fd_id
	 */
	private String fdId;
	/**
	 * 公司
	 */
	private String companyId;
	/**
	 * 照片编号
	 */
	private String picNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 身份证号码
	 */
	private String nid;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 面试官
	 */
	private String douser;
	/**
	 * 面试日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dodate;
	/**
	 * 面试时间String类型
	 */
	private String dodateStr;
	/**
	 * 体检结果 (合格 不合格)
	 */
	private String doresult;
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
	/**
	 * 面试结果
	 */
	private String isPass;

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getDodateStr() {
		return dodateStr;
	}

	public void setDodateStr(String dodateStr) {
		this.dodateStr = dodateStr;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPicNo() {
		return picNo;
	}

	public void setPicNo(String picNo) {
		this.picNo = picNo;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDouser() {
		return douser;
	}

	public void setDouser(String douser) {
		this.douser = douser;
	}

	public Date getDodate() {
		return dodate;
	}

	public void setDodate(Date dodate) {
		this.dodate = dodate;
	}

	public String getDoresult() {
		return doresult;
	}

	public void setDoresult(String doresult) {
		this.doresult = doresult;
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

	/**
	 * 人员Id
	 */
	private String personalId;
	/**
	 * 1:已传PS
	 */
	private Integer isTops;
	/**
	 * 提交OA批次
	 */
	private String oaBatch;

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public Integer getIsTops() {
		return isTops;
	}

	public void setIsTops(Integer isTops) {
		this.isTops = isTops;
	}

	public String getOaBatch() {
		return oaBatch;
	}

	public void setOaBatch(String oaBatch) {
		this.oaBatch = oaBatch;
	}

	private String sexText;

	public String getSexText() {
		if (null != this.sex && !this.sex.isEmpty()) {
			if (this.sex.equals("M"))
				sexText = "男";
			else if (this.sex.equals("F"))
				sexText = "女";
		}
		return sexText;
	}

	public void setSexText(String sexText) {
		this.sexText = sexText;
	}

	private Integer canSelect;

	public Integer getCanSelect() {
		if (null != this.isTops && this.isTops == 1 && null != this.oaBatch && !this.oaBatch.isEmpty())
			this.canSelect = 0; // 不允许选择
		else
			this.canSelect = 1;

		return canSelect;
	}

	public void setCanSelect(Integer canSelect) {
		this.canSelect = canSelect;
	}
}
