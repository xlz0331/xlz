package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPersonalCredentials;
import com.hwagain.org.register.dto.RegPersonalCredentialsDto;
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
public interface IRegPersonalCredentialsService extends IService<RegPersonalCredentials> {
	
	public List<RegPersonalCredentialsDto> findAll() throws CustomException; 
	
	public RegPersonalCredentialsDto findOne(String fdId) throws CustomException;
	
	public RegPersonalCredentialsDto save(RegPersonalCredentialsDto dto) throws CustomException;
	
	public RegPersonalCredentialsDto update(RegPersonalCredentialsDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPersonalCredentialsDto> findByPage(RegPersonalCredentialsDto dto,PageVO pageVo) throws CustomException;
}
