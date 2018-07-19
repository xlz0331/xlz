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
 * @since 2018-07-09
 */
@TableName("reg_employ_college")
public class RegEmployCollege extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	@TableId("fd_id")
	private String fdId;
    /**
     * 公司id
     */
	@TableField("company_id")
	private String companyId;
    /**
     * 年份
     */
	private String year;
    /**
     * 届别
     */
	private String season;
    /**
     * 姓名
     */
	private String name;
    /**
     * 性别
     */
	private String sex;
    /**
     * 名族
     */
	private String nation;
    /**
     * 身份证号码
     */
	private String nid;
    /**
     * 出生日期
     */
	private Date birthdate;
    /**
     * 招聘岗位
     */
	private String position;
    /**
     * 学历
     */
	private String education;
    /**
     * 毕业院校
     */
	private String school;
    /**
     * 专业
     */
	private String major;
    /**
     * 人员类型 (2:校招管培生
     3:校招大专生
     )
     */
	private Integer pertype;
    /**
     * 已放弃入职 (1:已放弃入职)
     */
	@TableField("is_giveup")
	private Integer isGiveup;
    /**
     * 已注册记录 (1:已注册记录)
     */
	@TableField("is_register")
	private Integer isRegister;
    /**
     * 简历ID
     */
	private String ResumeId;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Integer getPertype() {
		return pertype;
	}

	public void setPertype(Integer pertype) {
		this.pertype = pertype;
	}

	public Integer getIsGiveup() {
		return isGiveup;
	}

	public void setIsGiveup(Integer isGiveup) {
		this.isGiveup = isGiveup;
	}

	public Integer getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Integer isRegister) {
		this.isRegister = isRegister;
	}

	public String getResumeId() {
		return ResumeId;
	}

	public void setResumeId(String ResumeId) {
		this.ResumeId = ResumeId;
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
