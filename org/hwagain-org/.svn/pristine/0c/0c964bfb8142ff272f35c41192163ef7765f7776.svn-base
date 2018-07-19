package com.hwagain.org.dept.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.dept.dto.OrgDeptProDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgJobSelectors;
import com.hwagain.org.dept.entity.OrgDeptPro;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgDeptProService extends IService<OrgDeptPro> {
	
	public OrgDeptProDto findOne(String fdId) throws CustomException;
	
	public List<OrgDeptProDto> findTreeAll() throws CustomException;
	
	public List<OrgDeptPro> findByIds(String[] ids) throws CustomException;
	
	public OrgExcel findExcel(String fdId) throws CustomException;
	
	public OrgExcel findDeptExcel(String fdId) throws CustomException;
	
	public OrgExcel findFactoryExcel(String fdId) throws CustomException;
	
	//备份数据库
	public void backupCompany(Long time);
	public void backupDept(Long time);
	public void backupJob(Long time);
	
	//同步前删除相关数据
	public Integer deleteDeptByCompany(String company);
	public Integer deleteJobByCompany(String company);
	
	//同步历史数据到生产
	public Integer insertDeptByCompany(String company,Integer version);
	public Integer insertJobByCompany(String company,Integer version);
	
	public String dataCorrection() throws CustomException;
	
	//手动同步数据到PS，全量同步
	public String syncDeptPS() throws CustomException;
	
	public List<String> findDeptAllById(String depid) throws CustomException;
	
	public List<String> findDeptNodeAndNextIds(String depid) throws CustomException;
	
	public List<OrgDeptProDto> findOrgRootNode(String companycode) throws CustomException;
	
	public List<OrgDeptProDto> findOrgChildNode(String nodeid) throws CustomException;
	
	public List<OrgJobSelectors> findOrgOrJobByParam(Integer type,String keyword) throws CustomException;
	
	public String syncPro(String company,Integer version);
}
