package com.hwagain.org.job.mapper;

import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgJobSelectors;
import com.hwagain.org.job.dto.OrgJobProDto;
import com.hwagain.org.job.entity.OrgJobPro;

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
public interface OrgJobProMapper extends BaseMapper<OrgJobPro> {

	public List<OrgExcel> findJobByParam(@Param("company")String company);
	
	public OrgJobProDto getJobCurrentNum(@Param("jobCode")String jobCode);
	
	public List<OrgJobSelectors> findJobByKeywork(@Param("nodeid")String nodeid,@Param("keyword")String keyword);
	
	public List<OrgJobSelectors> findJobByJobIds(@Param("list")String[] ids);
}