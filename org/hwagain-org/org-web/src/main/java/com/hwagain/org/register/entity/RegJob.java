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
@TableName("reg_job")
public class RegJob extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * fd_id
     */
	@TableId("fd_id")
	private String fdId;
    /**
     * 公司
     */
	@TableField("company_id")
	private String companyId;
    /**
     * 岗位编号
     */
	@TableField("position_nbr")
	private String positionNbr;
    /**
     * 岗位名称
     */
	private String position;
    /**
     * 体系id
     */
	@TableField("system_id")
	private String systemId;
    /**
     * 体系名称
     */
	private String system;
    /**
     * 直接上级编号
     */
	@TableField("upper_nbr")
	private String upperNbr;
    /**
     * 直接上级
     */
	@TableField("upper_posi")
	private String upperPosi;
	/**
     * 上级工号
     */
	@TableField("upper_no")
	private String upperNo;
    /**
     * 上级姓名
     */
	@TableField("upper_name")
	private String upperName;
    /**
     * 部门id
     */
	@TableField("dept_id")
	private String deptId;
    /**
     * 部门
     */
	private String dept;
    /**
     * 车间id
     */
	@TableField("workshop_id")
	private String workshopId;
    /**
     * 车间
     */
	private String workshop;
    /**
     * 班别id
     */
	@TableField("classes_id")
	private String classesId;
    /**
     * 班别
     */
	private String classes;
    /**
     * 工段id
     */
	@TableField("section_id")
	private String sectionId;
    /**
     * 工段
     */
	private String section;
    /**
     * 职务编号
     */
	@TableField("job_id")
	private String jobId;
    /**
     * 职务名称
     */
	@TableField("job_name")
	private String jobName;
    /**
     * 职务等级
     */
	@TableField("job_level")
	private String jobLevel;
    /**
     * 试用期
     */
	private String tryrange;
    /**
     * 员工类型
     */
	private String emptype;
    /**
     * 员工属性
     */
	private String empattribute;
    /**
     * 分管范围
     */
	private String scope;
    /**
     * 上班制度id
     */
	@TableField("institution_id")
	private String institutionId;
    /**
     * 上班制度
     */
	private String institution;
    /**
     * 人员类型 (1:社招管理人员
     2:校招管培生
     3:校招大专生
     4:岗位工)
     */
	private Integer pertype;
    /**
     * 操作
     */
	private String operate;
    /**
     * 原因
     */
	private String reason;
    /**
     * 是否管培生 (是
     否)
     */
	@TableField("is_gps")
	private String isGps;
	/**
     * 行政技术职务
     */
	@TableField("tech_jobcode")
	private String techJobcode;
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

	public String getPositionNbr() {
		return positionNbr;
	}

	public void setPositionNbr(String positionNbr) {
		this.positionNbr = positionNbr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getUpperNbr() {
		return upperNbr;
	}

	public void setUpperNbr(String upperNbr) {
		this.upperNbr = upperNbr;
	}

	public String getUpperPosi() {
		return upperPosi;
	}

	public void setUpperPosi(String upperPosi) {
		this.upperPosi = upperPosi;
	}
	
	public String getUpperNo() {
		return upperNo;
	}

	public void setUpperNo(String upperNo) {
		this.upperNo= upperNo;
	}

	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(String workshopId) {
		this.workshopId = workshopId;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getTryrange() {
		return tryrange;
	}

	public void setTryrange(String tryrange) {
		this.tryrange = tryrange;
	}

	public String getEmptype() {
		return emptype;
	}

	public void setEmptype(String emptype) {
		this.emptype = emptype;
	}

	public String getEmpattribute() {
		return empattribute;
	}

	public void setEmpattribute(String empattribute) {
		this.empattribute = empattribute;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Integer getPertype() {
		return pertype;
	}

	public void setPertype(Integer pertype) {
		this.pertype = pertype;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsGps() {
		return isGps;
	}

	public void setIsGps(String isGps) {
		this.isGps = isGps;
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
	
	
	
	public String getTechJobcode() {
		return techJobcode;
	}

	public void setTechJobcode(String techJobcode) {
		this.techJobcode = techJobcode;
	}

	/**
     * 班次编码
     */
	private String shiftcode;
    /**
     * 班次名称
     */
	private String shiftname;
	
	public String getShiftcode() {
		return shiftcode;
	}

	public void setShiftcode(String shiftcode) {
		this.shiftcode = shiftcode;
	}

	public String getShiftname() {
		return shiftname;
	}

	public void setShiftname(String shiftname) {
		this.shiftname = shiftname;
	}
}
