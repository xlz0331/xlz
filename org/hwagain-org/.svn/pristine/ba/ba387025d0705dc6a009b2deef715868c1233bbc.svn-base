package com.hwagain.org.user.mapper;

import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.entity.OrgUserJob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2018-04-25
 */
public interface OrgUserJobMapper extends BaseMapper<OrgUserJob> {
	
	public List<OrgUserJobDto> findByJobCode(@Param("jobCode")String jobCode);
}