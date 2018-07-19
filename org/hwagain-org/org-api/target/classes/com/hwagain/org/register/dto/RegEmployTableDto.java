package com.hwagain.org.register.dto;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class RegEmployTableDto extends RegJobDto implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 民族
     */
	private String nation;
    /**
     * 身份证号码
     */
	private String nid;
	
	
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
	
	/**
	 * 面试官
	 */
	private String douser;
	public String getDouser() {
		return douser;
	}

	public void setDouser(String douser) {
		this.douser = douser;
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
	
	private Integer age;
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
