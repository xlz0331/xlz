package com.hwagain.org.job.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.job.entity.OrgJobHistory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
public interface IOrgJobHistoryService extends IService<OrgJobHistory> {
	
	public OrgJobHistory findOne(String fdId,Integer version) throws CustomException;
	
	public List<OrgExcel> findJobByParam(String company,Integer version) throws CustomException;
}
