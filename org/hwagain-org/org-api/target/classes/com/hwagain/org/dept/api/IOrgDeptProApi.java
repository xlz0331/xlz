package com.hwagain.org.dept.api;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.org.dept.dto.OrgDeptJobDto;
import com.hwagain.org.dept.dto.OrgDeptProDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgDeptProApi {
	
	public List<OrgDeptJobDto> findNodeJob(String company) throws CustomException;
	
	public List<OrgDeptJobDto> findUserNodeJob(String company) throws CustomException;
	
	public List<OrgDeptProDto> findOrgRootNode(String companycode) throws CustomException;
	
	public List<OrgDeptProDto> findOrgChildNode(String nodeid) throws CustomException;
}
