package com.hwagain.org.config.entity;

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
 * @since 2018-03-14
 */
@TableName("org_version_audit")
public class OrgVersionAudit extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private String fdId;
    /**
     * 版本表org_company、org_dept
     */
	@TableField("version_form")
	private String versionForm;
    /**
     * 版本
     */
	private Integer version;
    /**
     * 状态；0：待提交审核，1：已提交审核（待审核），2：已审核并通过(待同步)，3：已审核未通过（不同步），4：已同步完成
     */
	private String status;
	
	private String company;
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
     * 创建时间
     */
	@TableField("effect_time")
	private Date effectTime;


	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getVersionForm() {
		return versionForm;
	}

	public void setVersionForm(String versionForm) {
		this.versionForm = versionForm;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
