package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegWorkerInterviewPicture;
import com.hwagain.org.register.dto.RegWorkerInterviewPictureDto;
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
public interface IRegWorkerInterviewPictureService extends IService<RegWorkerInterviewPicture> {
	
	public List<RegWorkerInterviewPictureDto> findAll() throws CustomException; 
	
	public RegWorkerInterviewPictureDto findOne(String fdId) throws CustomException;
	
	public RegWorkerInterviewPictureDto save(RegWorkerInterviewPictureDto dto) throws CustomException;
	
	public RegWorkerInterviewPictureDto update(RegWorkerInterviewPictureDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegWorkerInterviewPictureDto> findByPage(RegWorkerInterviewPictureDto dto,PageVO pageVo) throws CustomException;
}
