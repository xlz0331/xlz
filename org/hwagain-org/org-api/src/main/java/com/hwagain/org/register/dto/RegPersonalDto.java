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
public class RegPersonalDto extends RegPageVoDto implements Serializable {

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
     * 员工编号
     */
	private String emplid;
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
     * 身份证住址
     */
	private String address;
    /**
     * 婚姻状况
     */
	private String marry;
    /**
     * 是否独生子女
     */
	private String onlyChild;
    /**
     * 籍贯
     */
	private String nativePlace;
    /**
     * 国家地区
     */
	private String country;
    /**
     * 出生日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthdate;
    /**
     * 出生省份
     */
	private String birthProvince;
    /**
     * 出生地址类型
     */
	private String birthPlaceType;
    /**
     * 出生地点
     */
	private String birthPlace;
    /**
     * 户口所在地
     */
	private String hukouPlace;
    /**
     * 户口类型
     */
	private String hukouType;
    /**
     * 宗教信仰
     */
	private String religion;
    /**
     * 政治面貌
     */
	private String politics;
    /**
     * 入党时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date inpartydate;
    /**
     * 照片名称
     */
	private String picName;
    /**
     * 照片id
     */
	private String picId;
    /**
     * 照片路径
     */
	private String picPath;
    /**
     * 血型
     */
	private String blood;
    /**
     * 身高
     */
	private String height;
    /**
     * 视力_左
     */
	private String visionLeft;
    /**
     * 视力_右
     */
	private String visionRight;
    /**
     * 首次参加工作时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date firstworkdate;
    /**
     * 特长
     */
	private String specialty;
    /**
     * 联系电话
     */
	private String phone;
    /**
     * 家庭电话
     */
	private String phoneHome;
    /**
     * 电子邮箱
     */
	private String emial;
    /**
     * qq
     */
	private String qq;
    /**
     * 现居住地址
     */
	private String address1;
    /**
     * 家庭地址
     */
	private String address2;
    /**
     * 邮件送达地址
     */
	
	private String address3;
    /**
     * 最高学历
     */
	private String highestEduc;
    /**
     * 人员类型 (1:社招管理人员
     2:校招管培生
     3:校招大专生
     4:岗位工)
     */
	private Integer pertype;
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

	public String getEmplid() {
		return emplid;
	}

	public void setEmplid(String emplid) {
		this.emplid = emplid;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(String onlyChild) {
		this.onlyChild = onlyChild;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getBirthPlaceType() {
		return birthPlaceType;
	}

	public void setBirthPlaceType(String birthPlaceType) {
		this.birthPlaceType = birthPlaceType;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getHukouPlace() {
		return hukouPlace;
	}

	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}

	public String getHukouType() {
		return hukouType;
	}

	public void setHukouType(String hukouType) {
		this.hukouType = hukouType;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getPolitics() {
		return politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public Date getInpartydate() {
		return inpartydate;
	}

	public void setInpartydate(Date inpartydate) {
		this.inpartydate = inpartydate;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getVisionLeft() {
		return visionLeft;
	}

	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}

	public String getVisionRight() {
		return visionRight;
	}

	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}

	public Date getFirstworkdate() {
		return firstworkdate;
	}

	public void setFirstworkdate(Date firstworkdate) {
		this.firstworkdate = firstworkdate;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneHome() {
		return phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public String getEmial() {
		return emial;
	}

	public void setEmial(String emial) {
		this.emial = emial;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getHighestEduc() {
		return highestEduc;
	}

	public void setHighestEduc(String highestEduc) {
		this.highestEduc = highestEduc;
	}
	
	public Integer getPertype() {
		return pertype;
	}

	public void setPertype(Integer pertype) {
		this.pertype = pertype;
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
