package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPersonalEducation;
import com.hwagain.org.register.dto.RegPersonalEducationDto;
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
public interface IRegPersonalEducationService extends IService<RegPersonalEducation> {
	
	public List<RegPersonalEducationDto> findAll() throws CustomException; 
	
	public RegPersonalEducationDto findOne(String fdId) throws CustomException;
	
	public RegPersonalEducationDto save(RegPersonalEducationDto dto) throws CustomException;
	
	public RegPersonalEducationDto update(RegPersonalEducationDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPersonalEducationDto> findByPage(RegPersonalEducationDto dto,PageVO pageVo) throws CustomException;
}
