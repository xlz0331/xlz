package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegEmployCollege;
import com.hwagain.org.register.dto.RegEmployCollegeDto;
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
public interface IRegEmployCollegeService extends IService<RegEmployCollege> {
	
	public List<RegEmployCollegeDto> findAll() throws CustomException; 
	
	public RegEmployCollegeDto findOne(String fdId) throws CustomException;
	
	public RegEmployCollegeDto save(RegEmployCollegeDto dto) throws CustomException;
	
	public RegEmployCollegeDto update(RegEmployCollegeDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegEmployCollegeDto> findByPage(RegEmployCollegeDto dto,PageVO pageVo) throws CustomException;
}
