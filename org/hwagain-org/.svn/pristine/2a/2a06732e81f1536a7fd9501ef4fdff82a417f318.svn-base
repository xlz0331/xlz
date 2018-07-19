package com.hwagain.org.dept.service;

import java.util.Date;
import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgRegroup;
import com.hwagain.org.dept.entity.OrgDept;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgDeptService extends IService<OrgDept> {
	
	public List<OrgDeptDto> findAll() throws CustomException; 
	
	public OrgDeptDto findOne(String fdId) throws CustomException;
	
	public OrgDeptDto save(OrgDeptDto dto) throws CustomException;
	
	public OrgDeptDto update(OrgDeptDto dto) throws CustomException;
	
	public String deleteById(String id,String state) throws CustomException;
	
	public List<OrgDeptDto> findByNodeId(String fdId) throws CustomException;
	
	public String splitDept(String fdId,String names,String state) throws CustomException;
	
	public String mergeDept(String fdIds,String name,String state) throws CustomException;
	
	public List<OrgDeptDto> findAllByCompany(String company,String type) throws CustomException;
	
	public List<OrgDept> findAllByCompany(String company) throws CustomException;
	
	
	
	public List<OrgDept> findByNode(String fdId,String company) throws CustomException;
	
	public List<OrgDeptDto> findDeptParentNode(String fdId,String code) throws CustomException;
	
	public String dataCorrection(String company) throws CustomException;
	
	public List<OrgDeptDto> findDeptByIds(String ids) throws CustomException;
	
	public String saveBatchByRegroup(String regroups) throws CustomException;
	
	public List<OrgRegroup> findRegroup(String company) throws CustomException;
	
	public Integer updateJobIdByIds(String jobId,String deptIds) throws CustomException;
	
	public String findNodeNameByCode(String codes);
	
	//改变部门、公司、职位的版本
	public Integer updateDeptVersion(String company,Integer version,Date date);
	
	public Integer updateJobVersion(String company,Integer version);
	
	public Integer updateCompanyVersion(String company,Integer version);
	
	public OrgExcel findExcel(String fdId) throws CustomException;
	
	public OrgExcel findDeptExcel(String fdId) throws CustomException;
	
	public OrgExcel findFactoryExcel(String fdId) throws CustomException;
	
	public String findCompanyId(String company) throws CustomException;
}
