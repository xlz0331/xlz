package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegSyncPs;
import com.hwagain.org.register.dto.RegSyncPsDto;
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
 * @since 2018-07-02
 */
public interface IRegSyncPsService extends IService<RegSyncPs> {
	
	public List<RegSyncPsDto> findAll() throws CustomException; 
	
	public RegSyncPsDto findOne(String fdId) throws CustomException;
	
	public RegSyncPsDto save(RegSyncPsDto dto) throws CustomException;
	
	public RegSyncPsDto update(RegSyncPsDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<RegSyncPsDto> findByPage(RegSyncPsDto dto,PageVO pageVo) throws CustomException;
}
