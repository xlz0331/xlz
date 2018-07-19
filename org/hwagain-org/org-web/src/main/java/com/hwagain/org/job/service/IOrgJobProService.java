package com.hwagain.org.job.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgJobSelectors;
import com.hwagain.org.job.dto.OrgJobProDto;
import com.hwagain.org.job.entity.OrgJobPro;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
public interface IOrgJobProService extends IService<OrgJobPro> {
	
	public List<OrgJobProDto> findJobsByDeptId(String deptId) throws CustomException;
	
	public List<OrgExcel> findJobByParam(String company) throws CustomException; 
	
	public Boolean isJobUnfilled(String jobCode) throws CustomException;
	
	public List<OrgJobSelectors> findJobByKeywork(String keyword) throws CustomException;
	
	public List<OrgJobSelectors> findJobsByNodeId(String nodeid) throws CustomException;
	
	public List<OrgJobSelectors> findJobsByIds(String jobids) throws CustomException;
	
	//全量同步职位到PS系统
	public String syncPS() throws CustomException;
}
