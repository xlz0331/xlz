package com.hwagain.org.job.service;

import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.job.dto.OrgJobDto;
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
 * @author hanj
 * @since 2018-03-15
 */
public interface IOrgJobService extends IService<OrgJob> {
	
	public List<OrgJobDto> findAll() throws CustomException; 
	
	public OrgJobDto findOne(String fdId) throws CustomException;
	
	public OrgJobDto save(OrgJobDto dto,OrgDept dept,String url) throws CustomException;
	
	public OrgJobDto update(OrgJobDto dto,String url) throws CustomException;
	
	public String deleteByIds(String ids,String state) throws CustomException;
	
	public PageDto<OrgJobDto> findByPage(OrgJobDto dto,PageVO pageVo) throws CustomException;
	
	/**
	 * ==================================
	 */
	
	public void saveJobList(List<OrgJobDto> list,OrgDept dept,String url) throws CustomException;
	
	public List<OrgJobDto> findOrgJobByDeptId(String deptId) throws CustomException;
	
	public List<OrgJobDto> findOrgJobByDeptId(String deptId,String type) throws CustomException;
	
	
	public String saveJob(OrgJobDto dto) throws CustomException;
	
	public String updateJob(OrgJobDto dto) throws CustomException;
	
	public List<OrgJobDto> findOrgJobByParam(String deptId,String type) throws CustomException;
	
	public String findOrgJobName(String code) throws CustomException;
	
	public void updateJobDeptIdByDeptId(String newDeptId,String newDeptCode,String[] oldDeptId) throws CustomException;
	
	public List<OrgJobDto> findOrgJobByCompany(String company,String typeCode) throws CustomException;
	
	public List<OrgJob> findOrgJobByCompany(String company) throws CustomException;
	
	public List<OrgExcel> findJobByParam(String company) throws CustomException;
}
