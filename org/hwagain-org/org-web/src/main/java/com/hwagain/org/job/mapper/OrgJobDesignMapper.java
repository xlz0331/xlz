package com.hwagain.org.job.mapper;

import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.job.dto.OrgJobDesignDetailDto;
import com.hwagain.org.job.entity.OrgJobDesign;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author huangyj
 * @since 2018-06-13
 */
public interface OrgJobDesignMapper extends BaseMapper<OrgJobDesign> {
	// 按公司及编码类型查询体系、部门、工厂等多个等级的deptID
	public List<OrgDeptDto> getDeptIdByParam(@Param("company")String company, @Param("typeCode")String typeCodem,@Param("deptId")String deptId);
	// 按公司及编码类型查询体系、部门、工厂等多个等级及其下属所有职位 
	public List<OrgJobDesignDetailDto> getDetails(@Param("company")String company, @Param("typeCode")String typeCode,@Param("deptId")String deptId);
}