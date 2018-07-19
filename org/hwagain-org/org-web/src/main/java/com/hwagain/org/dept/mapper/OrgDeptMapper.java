package com.hwagain.org.dept.mapper;

import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDept;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface OrgDeptMapper extends BaseMapper<OrgDept> {

	public Integer insertOrgDeptBatch(@Param("list")List<OrgDept> list);
	
	public Integer updateParentId(@Param("parentId")String parentId,@Param("parentDEPTID")String parentDEPTID,@Param("list")List<String> list);
	
	
	//改变部门、公司、职位的版本
	public Integer updateDeptVersion(@Param("company")String company,@Param("version")Integer version,@Param("date")Date date);
	
	public Integer updateJobVersion(@Param("company")String company,@Param("version")Integer version);
	
	public Integer updateCompanyVersion(@Param("company")String company,@Param("version")Integer version);
	
	
	public Integer deleteBatchByIds(@Param("list")String[] list);
	
	public Integer updateBatchByIds(@Param("fdId")String fdId,@Param("list")String[] list);
	
	public Integer updateJobIdByIds(@Param("jobId")String jobId,@Param("list")String[] list);
	
	public String findNodeNameByCode(@Param("list")String[] list);
	
	public List<OrgExcel> findDeptByParam(@Param("company")String company,@Param("typeCode")String[] typeCode);
}