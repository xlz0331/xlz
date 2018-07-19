package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.dto.RegPeopleDto;
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
public interface IRegPeopleService extends IService<RegPeople> {
	
	public List<RegPeopleDto> findAll() throws CustomException; 
	
	public RegPeopleDto findOne(String fdId) throws CustomException;
	
	public RegPeopleDto save(RegPeopleDto dto) throws CustomException;
	
	public RegPeopleDto update(RegPeopleDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPeopleDto> findByPage(RegPeopleDto dto,PageVO pageVo) throws CustomException;

	public RegPeopleDto shuKa(RegPeopleDto dto);
	
	public List<String> getAllNids() throws CustomException;
}
