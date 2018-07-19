package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegPersonalPictureFile;
import com.hwagain.org.register.dto.RegPersonalPictureFileDto;
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
 * @since 2018-07-10
 */
public interface IRegPersonalPictureFileService extends IService<RegPersonalPictureFile> {
	
	public List<RegPersonalPictureFileDto> findAll() throws CustomException; 
	
	public RegPersonalPictureFileDto findOne(String fdId) throws CustomException;
	
	public RegPersonalPictureFileDto save(RegPersonalPictureFileDto dto) throws CustomException;
	
	public RegPersonalPictureFileDto update(RegPersonalPictureFileDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegPersonalPictureFileDto> findByPage(RegPersonalPictureFileDto dto,PageVO pageVo) throws CustomException;

	public RegPersonalPictureFileDto findByPersonal(String personalId);
}
