package com.hwagain.org.type.mapper;

import com.hwagain.org.type.entity.OrgType;

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
public interface OrgTypeMapper extends BaseMapper<OrgType> {

	public Integer deleteByIds(@Param("list")String[] ids);
	
	public String findMaxCode();
}