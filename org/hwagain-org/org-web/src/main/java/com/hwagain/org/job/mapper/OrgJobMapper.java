package com.hwagain.org.job.mapper;

import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.job.entity.OrgJob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
public interface OrgJobMapper extends BaseMapper<OrgJob> {

	public Integer deleteOrgJob(@Param("list")String[] list);
	
	public Integer updateJobDeptByDept(@Param("newDeptId")String newDeptId,@Param("newDeptCode")String newDeptCode,@Param("list")String[] oldDeptId);
	
	public List<OrgExcel> findJobByParam(@Param("company")String company);
}