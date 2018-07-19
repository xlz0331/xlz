package com.hwagain.org.register.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RegPeopleDto extends RegPageVoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * fd_id
	 */
	private String fdId;
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 身份证号码
	 */
	private String nid;
	/**
	 * 出生日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthdate;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 已履历信息 (0:未录入 1:未录完 9:已录完)
	 */
	private Integer isRecord;
	/**
	 * 已岗位信息 (0:未录入 1:未录完 9:已录完)
	 */
	private Integer isPosition;
	/**
	 * 员工编号
	 */
	private String emplid;
	/**
	 * 已员工卡 (0:未发放 1:已发放 )
	 */
	private Integer isEmpcar;
	/**
	 * 员工卡号
	 */
	private String empcarno;
	/**
	 * 已提交PS (0:未提交 1:已提交
	 * 
	 * )
	 */
	private Integer isTops;
	/**
	 * 人员类型 (1:社招管理人员 2:校招管培生 3:校招大专生 4:岗位工)
	 */
	private Integer pertype;
	/**
	 * 提交PS人
	 */
	private String topsuserid;
	/**
	 * 提交PS日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date topsdate;
	/**
	 * 是否删除 (1:已删除)
	 */
	private Integer isDelete;
	/**
	 * 创建人
	 */
	private String docCreateId;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date docCreateTime;
	/**
	 * 最后修改人
	 */
	private String docLastUpdateId;
	/**
	 * 最后修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(Integer isRecord) {
		this.isRecord = isRecord;
	}

	public Integer getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(Integer isPosition) {
		this.isPosition = isPosition;
	}

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
	}

	public Integer getIsEmpcar() {
		return isEmpcar;
	}

	public void setIsEmpcar(Integer isEmpcar) {
		this.isEmpcar = isEmpcar;
	}

	public String getEmpcarno() {
		return empcarno;
	}

	public void setEmpcarno(String empcarno) {
		this.empcarno = empcarno;
	}

	public Integer getIsTops() {
		return isTops;
	}

	public void setIsTops(Integer isTops) {
		this.isTops = isTops;
	}

	public Integer getPertype() {
		return pertype;
	}

	public void setPertype(Integer pertype) {
		this.pertype = pertype;
	}

	public String getTopsuserid() {
		return topsuserid;
	}

	public void setTopsuserid(String topsuserid) {
		this.topsuserid = topsuserid;
	}

	public Date getTopsdate() {
		return topsdate;
	}

	public void setTopsdate(Date topsdate) {
		this.topsdate = topsdate;
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

	/**
	 * 已履历信息 (0:未录入 1:未录完 9:已录完)
	 */
	private String isRecordText;
	public String getIsRecordText() {
		if (null != this.isRecord) {
			if (this.isRecord == 0)
				this.isRecordText = "未录入";
			else if (this.isRecord == 1)
				this.isRecordText = "未录完";
			else if (this.isRecord == 9)
				this.isRecordText = "已录完";
		}
		return isRecordText;
	}
	
	
	/**
	 * 已岗位信息 (0:未录入 1:未录完 9:已录完)
	 */
	private String isPositionText;
	public String getIsPositionText() {
		if (null != this.isPosition) {
			if (this.isPosition == 0)
				this.isPositionText = "未录入";
			else if (this.isPosition == 1)
				this.isPositionText = "未录完";
			else if (this.isPosition == 9)
				this.isPositionText = "已录完";
		}
		return isPositionText;
	}

	private String isEmpcarText;
	public String getIsEmpcarText() {
		if (null != this.isEmpcar) {
			if (this.isEmpcar == 0)
				this.isEmpcarText = "未发放";
			else if (this.isEmpcar == 1)
				this.isEmpcarText = "已发放";
		}
		return isEmpcarText;
	}
	
	/**
	 * 已提交PS (0:未提交 1:已提交 )
	 */
	private String isTopsText;
	public String getIsTopsText() {
		if (null != this.isTops) {
			if (this.isTops == 0)
				this.isTopsText = "未提交";
			else if (this.isTops == 1)
				this.isTopsText = "已提交";
		}
		return isTopsText;
	}

	private String sexText;
	public String getSexText() {
		if(null!=this.sex && !this.sex.isEmpty())
		{
			if(this.sex.equals("M"))
				sexText ="男";
			else if(this.sex.equals("F"))
				sexText ="女";
		}
		return sexText;
	}
	public void setSexText(String sexText) {
		this.sexText = sexText;
	}

}
