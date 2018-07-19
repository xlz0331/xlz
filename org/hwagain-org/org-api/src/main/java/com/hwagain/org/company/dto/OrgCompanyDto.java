package com.hwagain.org.company.dto;

import java.util.Date;

import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public class OrgCompanyDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 全称
     */
	private String descr;
    /**
     * 简称
     */
	private String descrshort;
    /**
     * 公司ID
     */
	private String company;
    /**
     * 类型CODE对应type表
     */
	private String type;
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


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
