package com.hwagain.org.config.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.config.dto.OrgNumberConfigDto;
import com.hwagain.org.config.entity.OrgNumberConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
public interface IOrgNumberConfigService extends IService<OrgNumberConfig> {
	
	public List<OrgNumberConfigDto> findAll() throws CustomException; 
	
	public OrgNumberConfig findOneBytype(String type) throws CustomException;
	
	public OrgNumberConfigDto save(OrgNumberConfigDto dto) throws CustomException;
	
	public OrgNumberConfigDto update(OrgNumberConfigDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public Integer findNumberByType(String type) throws CustomException;
}
