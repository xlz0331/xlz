package com.hwagain.org.log.entity;

import com.hwagain.framework.sys.comons.model.BaseModel;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;
import com.hwagain.framework.security.common.util.UserUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
@TableName("org_log")
public class OrgLog extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private String fdId;
    /**
     * 操作类型：save：新增，update：修改，delete：删除，merge：合并
     */
	private String type;
    /**
     * 操作表
     */
	@TableField("operation_tbl")
	private String operationTbl;
    /**
     * 操作版本
     */
	private String version;
    /**
     * 操作链接
     */
	@TableField("operation_url")
	private String operationUrl;
    /**
     * 操作前内容
     */
	@TableField("operation_before")
	private String operationBefore;
    /**
     * 操作后内容
     */
	@TableField("operation_after")
	private String operationAfter;
    /**
     * 操作说明
     */
	private String remark;
    /**
     * 数据状态：processed：已批准，not_process
     */
	private String status;
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
     * 操作前数据ID
     */
	@TableField("operation_before_id")
	private String operationBeforeId;
    /**
     * 操作后数据ID
     */
	@TableField("operation_after_id")
	private String operationAfterId;
	 /**
     * 所属公司
     */
	private String company;
	 /**
     * 数据类型，对应org_type表
     */
	@TableField("org_code")
	private String orgCode;
	
	private String json;
	
	private String state;

	public OrgLog() {
		super();
	}

	public OrgLog(String type, String operationTbl,String version, String operationUrl,
			String operationBefore, String operationAfter, String remark,
			String operationBeforeId,String operationAfterId,String orgCode,
			String company,String json,String state) {
		super();
		this.type = type;
		this.operationTbl = operationTbl;
		this.version = version;
		this.operationUrl = operationUrl;
		this.operationBefore = operationBefore;
		this.operationAfter = operationAfter;
		this.remark = remark;
		this.status = "0";
		this.docCreateTime = new Date();
		this.docCreatorId = UserUtils.getUserId();
		this.operationBeforeId = operationBeforeId;
		this.operationAfterId = operationAfterId;
		this.orgCode = orgCode;
		this.company = company;
		this.json = json;
		this.state = state;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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
