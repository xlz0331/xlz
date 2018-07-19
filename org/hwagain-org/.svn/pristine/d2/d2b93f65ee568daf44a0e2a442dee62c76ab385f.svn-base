package com.hwagain.org.task.service;

import java.util.Date;
import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.task.entity.OrgSyncPs;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-05-29
 */
public interface IOrgSyncPsService extends IService<OrgSyncPs> {
	
	public void save(String type, String formType, String dataId, String json,Integer version) throws CustomException;
	
	public void deleteSync(String dataid) throws CustomException;
	
	public void sycnPStree(List<OrgSyncPs> list,Date effdt) throws CustomException;
	
	public void syncPSDept(Integer version,Date effdt) throws CustomException;
	
	public void syncPSJob(Integer version,Date effdt) throws CustomException;
	
	public void disposeMaxSyncPSException(String company) throws CustomException;
}
