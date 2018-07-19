package com.hwagain.org.log.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.log.entity.OrgLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
public interface IOrgLogService extends IService<OrgLog> {
	
	public void save(OrgLog entity) throws CustomException;
	
	public void update(OrgLog entity) throws CustomException;
	
	public JSONObject findListByCompany(String company) throws CustomException;
	
	public JSONObject findOrgLogByCompany(String company,Integer version) throws CustomException;
	
	public OrgLog findByDeptId(String deptid,Integer version) throws CustomException; 
	
	public List<OrgLog> findAllByCompany(String company,Integer version) throws CustomException;
	
	public String backoutLog(String fdId) throws CustomException;
	
	public Integer updateLogStatus(Integer version) throws CustomException;
}
