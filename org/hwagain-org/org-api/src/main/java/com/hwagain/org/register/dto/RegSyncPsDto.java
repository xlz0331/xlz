package com.hwagain.org.register.dto;

import java.util.Date;

import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author guoym
 * @since 2018-07-02
 */
public class RegSyncPsDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 操作类型save、delete、update
     */
	private String type;
	private String formType;
    /**
     * 数据ID
     */
	private String dataId;
    /**
     * 数据json
     */
	private String json;
    /**
     * 发送xml数据
     */
	private String sentXml;
    /**
     * 返回xml数据
     */
	private String resultXml;
    /**
     * 版本号
     */
	private String resultStatus;
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

	public String getSentXml() {
		return sentXml;
	}

	public void setSentXml(String sentXml) {
		this.sentXml = sentXml;
	}

	public String getResultXml() {
		return resultXml;
	}

	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
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
