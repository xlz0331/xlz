package com.hwagain.org.dept.mapper;

import com.hwagain.org.dept.dto.OrgDeptJobDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDeptPro;

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
public interface OrgDeptProMapper extends BaseMapper<OrgDeptPro> {

	public List<OrgExcel> findDeptByParam(@Param("company")String company,@Param("typeCode")String[] typeCode);
	
	//同步前删除相关数据
	public Integer deleteDeptByCompany(@Param("company")String company);
	public Integer deleteJobByCompany(@Param("company")String company);
	
	//同步历史数据到生产
	public Integer insertDeptByCompany(@Param("company")String company,@Param("version")Integer version);
	public Integer insertJobByCompany(@Param("company")String company,@Param("version")Integer version);
	
	public List<OrgDeptJobDto> findNodeJobByCompany(@Param("company")String company);
	
	public List<OrgDeptJobDto> findUserNodeJob(@Param("company")String company);
}