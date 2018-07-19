package com.hwagain.org.job.api;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.org.job.dto.OrgJobProDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
public interface IOrgJobProApi {
	
	public List<OrgJobProDto> findJobsByDeptId(String deptId) throws CustomException;
}
