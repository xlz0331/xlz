package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPersonalWork;
import com.hwagain.org.register.dto.RegPersonalWorkDto;
import com.hwagain.framework.mybatisplus.service.IService;
import java.util.List;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
public interface IRegPersonalWorkService extends IService<RegPersonalWork> {
	
	public List<RegPersonalWorkDto> findAll() throws CustomException; 
	
	public RegPersonalWorkDto findOne(String fdId) throws CustomException;
	
	public RegPersonalWorkDto save(RegPersonalWorkDto dto) throws CustomException;
	
	public RegPersonalWorkDto update(RegPersonalWorkDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPersonalWorkDto> findByPage(RegPersonalWorkDto dto,PageVO pageVo) throws CustomException;
}
