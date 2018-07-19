package com.hwagain.org.register.mapper;

import com.hwagain.org.register.dto.RegEmployTableDto;
import com.hwagain.org.register.entity.RegWorkerInterview;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
public interface RegWorkerInterviewMapper extends BaseMapper<RegWorkerInterview> {
	
	public List<RegWorkerInterview> checkExist(@Param("fdId")String fdId,@Param("nid")String nid,@Param("picNo")String picNo);

	public List<RegEmployTableDto> queryRegEmployTableData(@Param("oabatch") String oabatch);

}