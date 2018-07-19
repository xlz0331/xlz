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
@TableName("reg_personal_education")
public class RegPersonalEducation extends BaseModel {

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
     * 类型 (统招学历
     非同招学历)
     */
	private String typename;
    /**
     * 毕业院校
     */
	private String school;
    /**
     * 开始时间
     */
	private Date startdate;
    /**
     * 毕业时间
     */
	private Date enddate;
    /**
     * 专业
     */
	private String major;
    /**
     * 学历
     */
	private String education;
    /**
     * 学位
     */
	private String degree;
    /**
     * 学制
     */
	private String regime;
    /**
     * 任职级别
     */
	private String level;
    /**
     * 任职职务
     */
	private String position;
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

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
