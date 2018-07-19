package com.hwagain.org.register.mapper;

import com.hwagain.org.register.entity.RegPeople;

import java.util.List;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
public interface RegPeopleMapper extends BaseMapper<RegPeople> {

	
	public List<String> getAllNid();
}