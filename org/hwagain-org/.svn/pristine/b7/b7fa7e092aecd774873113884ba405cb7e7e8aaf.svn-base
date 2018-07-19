package com.hwagain.org.type.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.type.dto.OrgTypeDto;
import com.hwagain.org.type.entity.OrgType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgTypeService extends IService<OrgType> {

	public String save(OrgTypeDto entity) throws CustomException;
	
	public String update(OrgTypeDto entity) throws CustomException;
	
	public String delete(String ids) throws CustomException;
	
	public List<OrgTypeDto> findAll(String keywork) throws CustomException;
	
	public OrgType findOneByCode(String code) throws CustomException;
}
