package com.hwagain.org.user.dto;


import com.hwagain.framework.core.dto.BaseDto;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-05-02
 */
public class OrgUserJobDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String fdId;
    /**
     * 员工编号
     */
	private String empCode;
	
	private String empName;
    /**
     * 职位编号
     */
	private String jobCode;
	
	private String jobName;

	/**
     * 是否兼职
     */
	private Integer isParttime;
    /**
     * 所属班级ID（适用于三班倒岗位）
     */
	private String classId;

    public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getIsParttime() {
		return isParttime;
	}

	public void setIsParttime(Integer isParttime) {
		this.isParttime = isParttime;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
