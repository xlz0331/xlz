package com.hwagain.org.register.dto;


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
public class RegBaseDataDto extends RegPageVoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	private String fdId;
    /**
     * 分类
     */
	private String type;
    /**
     * 分类CN (1:已删除)
     */
	private String typecn;
    /**
     * 编码
     */
	private String code;
    /**
     * 名称
     */
	private String name;
    /**
     * 全称
     */
	private String name2;
    /**
     * 有效状态
     */
	private String status;
    /**
     * setid (PS系统字段)
     */
	private String setid;
    /**
     * syncid
     */
	private String syncid;
    /**
     * PS表名称
     */
	private String tablename;
    /**
     * 数据源 (PS系统
     本系统)
     */
	private String datafrom;
    /**
     * 备注
     */
	private String remark;


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

	public String getTypecn() {
		return typecn;
	}

	public void setTypecn(String typecn) {
		this.typecn = typecn;
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

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSetid() {
		return setid;
	}

	public void setSetid(String setid) {
		this.setid = setid;
	}

	public String getSyncid() {
		return syncid;
	}

	public void setSyncid(String syncid) {
		this.syncid = syncid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getDatafrom() {
		return datafrom;
	}

	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
