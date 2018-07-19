package com.hwagain.org.register.entity;

import com.hwagain.framework.sys.comons.model.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@TableName("reg_worker_interview")
public class RegWorkerInterview extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	@TableId("fd_id")
	private String fdId;
    /**
     * 公司
     */
	@TableField("company_id")
	private String companyId;
    /**
     * 照片编号
     */
	@TableField("pic_no")
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
	private Date dodate;
    /**
     * 体检结果 (合格
     不合格)
     */
	private String doresult;
    /**
     * 是否删除 (1:已删除)
     */
	@TableField("is_delete")
	private Integer isDelete;
    /**
     * 创建人
     */
	@TableField("doc_create_id")
	private String docCreateId;
    /**
     * 创建时间
     */
	@TableField("doc_create_time")
	private Date docCreateTime;
    /**
     * 最后修改人
     */
	@TableField("doc_last_update_id")
	private String docLastUpdateId;
    /**
     * 最后修改时间
     */
	@TableField("doc_last_update_time")
	private Date docLastUpdateTime;

	@TableField("is_pass")
	private String isPass;
	
	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
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
	@TableField("personal_id")
	private String personalId;
    /**
     * 1:已传PS
     */
	@TableField("is_tops")
	private Integer isTops;
    /**
     * 提交OA批次
     */
	@TableField("oa_batch")
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

}
