package com.hwagain.org.log.dto;

import java.util.Date;

import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-05-03
 */
public class OrgLogDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 操作类型：save：新增，update：修改，delete：删除，merge：合并,split：拆分
     */
	private String type;
    /**
     * 操作表
     */
	private String operationTbl;
    /**
     * 操作版本
     */
	private String version;
    /**
     * 操作链接
     */
	private String operationUrl;
    /**
     * 操作前数据ID
     */
	private String operationBeforeId;
    /**
     * 操作后数据ID
     */
	private String operationAfterId;
    /**
     * 操作前内容
     */
	private String operationBefore;
    /**
     * 操作后内容
     */
	private String operationAfter;
    /**
     * 备注（系统自用）
     */
	private String remark;
    /**
     * 状态；0：待提交审核，1：已提交审核（待审核），2：已审核并通过(待同步)，3：已审核未通过（不同步），4：已同步完成(已生效)
     */
	private String status;
    /**
     * 创建时间
     */
	private Date docCreateTime;
    /**
     * 创建者ID
     */
	private String docCreatorId;
    /**
     * 公司编号
     */
	private String company;
    /**
     * 数据类型，对应org_type表
     */
	private String orgCode;
    /**
     * json串
     */
	private String json;
    /**
     * 调整说明
     */
	private String state;


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

	public String getOperationTbl() {
		return operationTbl;
	}

	public void setOperationTbl(String operationTbl) {
		this.operationTbl = operationTbl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOperationUrl() {
		return operationUrl;
	}

	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}

	public String getOperationBeforeId() {
		return operationBeforeId;
	}

	public void setOperationBeforeId(String operationBeforeId) {
		this.operationBeforeId = operationBeforeId;
	}

	public String getOperationAfterId() {
		return operationAfterId;
	}

	public void setOperationAfterId(String operationAfterId) {
		this.operationAfterId = operationAfterId;
	}

	public String getOperationBefore() {
		return operationBefore;
	}

	public void setOperationBefore(String operationBefore) {
		this.operationBefore = operationBefore;
	}

	public String getOperationAfter() {
		return operationAfter;
	}

	public void setOperationAfter(String operationAfter) {
		this.operationAfter = operationAfter;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
