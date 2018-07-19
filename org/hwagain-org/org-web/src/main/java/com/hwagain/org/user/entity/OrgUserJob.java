package com.hwagain.org.user.entity;

import com.hwagain.framework.sys.comons.model.BaseModel;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author hanj
 * @since 2018-05-02
 */
@TableName("org_user_job")
public class OrgUserJob extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private String fdId;
    /**
     * 员工编号
     */
	@TableField("emp_code")
	private String empCode;
    /**
     * 职位编号
     */
	@TableField("job_code")
	private String jobCode;
    /**
     * 是否兼职
     */
	@TableField("is_parttime")
	private Integer isParttime;
    /**
     * 所属班级ID（适用于三班倒岗位）
     */
	@TableField("class_id")
	private String classId;


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

}
