package com.hwagain.org.register.dto;


import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-06-19
 */
public class RegPsJobDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 类型
     */
	private String setid;
    /**
     * 职务编码
     */
	private String code;
    /**
     * 职务名称
     */
	private String name;
    /**
     * 职务简称
     */
	private String shortname;
    /**
     * 是否删除
     */
	private Integer isDelete;


	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getSetid() {
		return setid;
	}

	public void setSetid(String setid) {
		this.setid = setid;
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

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
