package com.hwagain.org.dept.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.dept.dto.OrgDeptHistoryDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDeptHistory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
public interface IOrgDeptHistoryService extends IService<OrgDeptHistory> {
	
	public List<OrgDeptHistoryDto> findAll(String version,String company) throws CustomException; 
	
	public Integer findMaxVersion(String company);
	
	//提交申请后插入到历史表
	public Integer saveDeptHistory(String company);
	
	public Integer saveJobHistory(String company);
	
	
	public String cleanRedis(String version,String company) throws CustomException;
	
	public OrgExcel findExcel(String fdId,Integer version) throws CustomException;
	
	public OrgExcel findDeptExcel(String fdId,Integer version) throws CustomException;
	
	public OrgExcel findFactoryExcel(String fdId,Integer version) throws CustomException;
}
