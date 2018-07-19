package com.hwagain.org.register.entity;

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
 * @author guoym
 * @since 2018-06-07
 */
@TableName("reg_personal_credentials")
public class RegPersonalCredentials extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	@TableId("fd_id")
	private String fdId;
    /**
     * 人员id
     */
	@TableField("personal_id")
	private String personalId;
    /**
     * 编码
     */
	private String code;
    /**
     * 名称
     */
	private String name;
    /**
     * 已选择 (1:已选择)
     */
	private Integer checked;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
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
