package com.hwagain.org.config.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.config.dto.OrgVersionAuditDto;
import com.hwagain.org.config.entity.OrgVersionAudit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-14
 */
public interface IOrgVersionAuditService extends IService<OrgVersionAudit> {
	
	public List<OrgVersionAuditDto> findAll(String code) throws CustomException; 
	
	public void isActive(String company) throws CustomException; 
	
	public String auditOrg(String fdId,String status) throws CustomException;
	
	public String submitAuditOrg(String company,String time) throws CustomException;
	
	public void syncOrg(String company,Integer version) throws CustomException; 
	
	public List<OrgVersionAudit> findWaitSyncAll() throws CustomException; 
}
