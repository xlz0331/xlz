package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPersonalExigence;
import com.hwagain.org.register.dto.RegPersonalExigenceDto;
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
public interface IRegPersonalExigenceService extends IService<RegPersonalExigence> {
	
	public List<RegPersonalExigenceDto> findAll() throws CustomException; 
	
	public RegPersonalExigenceDto findOne(String fdId) throws CustomException;
	
	public RegPersonalExigenceDto save(RegPersonalExigenceDto dto) throws CustomException;
	
	public RegPersonalExigenceDto update(RegPersonalExigenceDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPersonalExigenceDto> findByPage(RegPersonalExigenceDto dto,PageVO pageVo) throws CustomException;
}
