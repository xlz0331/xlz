package com.hwagain.org.job.mapper;

import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.job.entity.OrgJobHistory;

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
public interface OrgJobHistoryMapper extends BaseMapper<OrgJobHistory> {

	public List<OrgExcel> findJobByParam(@Param("company")String company,@Param("version")Integer version);
}