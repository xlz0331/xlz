package com.hwagain.org.task.entity;

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
 * @since 2018-05-29
 */
@TableName("org_sync_ps")
public class OrgSyncPs extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private String fdId;
    /**
     * 操作类型save、delete、update
     */
	private String type;
    /**
     * 数据类型dept（部门数据）、position（职位数据）
     */
	private String formType;
    /**
     * 0:待同步，1：已同步，2：废除
     */
	private String dataType;
    /**
     * 数据ID
     */
	private String dataId;
	
	private Integer version;
    /**
     * 数据json
     */
	private String json;
	/**
     * 返回xml数据
     */
	private String requestXml;
	/**
     * 返回xml数据
     */
	private String resultXml;
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

	public OrgSyncPs() {
		super();
	}

	public OrgSyncPs(String type, String formType, String dataId, String json) {
		super();
		this.type = type;
		this.formType = formType;
		this.dataId = dataId;
		this.json = json;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getResultXml() {
		return resultXml;
	}

	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

}
