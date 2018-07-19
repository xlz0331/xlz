package com.hwagain.org.config.mapper;

import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
public interface OrgNumberConfigMapper extends BaseMapper<OrgNumberConfig> {

	public Integer findNumberByType(String type);
}