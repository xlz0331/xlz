package com.hwagain.org.user.mapper;

import java.util.List;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;
import com.hwagain.org.user.dto.OrgUserJobDeptDto;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.entity.OrgUser;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2018-03-17
 */
public interface OrgUserMapper extends BaseMapper<OrgUser> {

	public List<OrgUserJobDto> findUserAllByJob();
	
	public List<OrgUserJobDeptDto> findUserJobByDeptIds(List<String> list);
}