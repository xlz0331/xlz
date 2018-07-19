package com.hwagain.org.dept.mapper;

import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDeptHistory;

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
public interface OrgDeptHistoryMapper extends BaseMapper<OrgDeptHistory> {

	public Integer findMaxVersion(@Param("company")String company);
	
	
	//审批通过后插入到历史表
	public Integer saveDeptHistory(@Param("company")String company);
	
	public Integer saveJobHistory(@Param("company")String company);
	
	public List<OrgExcel> findDeptByParam(@Param("company")String company,@Param("version")Integer version,@Param("typeCode")String[] typeCode);
}