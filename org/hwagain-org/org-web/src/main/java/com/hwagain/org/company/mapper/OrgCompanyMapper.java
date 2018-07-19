package com.hwagain.org.company.mapper;

import com.hwagain.org.company.entity.OrgCompany;

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
public interface OrgCompanyMapper extends BaseMapper<OrgCompany> {

	public Integer deleteByIds(@Param("list")String[] ids);
	
	public String findMaxCode();
}