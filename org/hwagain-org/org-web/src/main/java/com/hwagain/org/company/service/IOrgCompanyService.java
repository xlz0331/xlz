package com.hwagain.org.company.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.company.dto.OrgCompanyDto;
import com.hwagain.org.company.entity.OrgCompany;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgCompanyService extends IService<OrgCompany> {
	
	public List<OrgCompanyDto> findAll() throws CustomException; 
	
	public OrgCompany findOne(String fdId) throws CustomException;
	
	public String save(OrgCompanyDto dto) throws CustomException;
	
	public String update(OrgCompanyDto dto) throws CustomException;
	
	public String deleteByIds(String ids) throws CustomException;
	
	public String findNameByCode(String code) throws CustomException;
}
