package com.hwagain.org.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.dept.service.IOrgDeptService;
import com.hwagain.org.task.service.IOrgSyncPsService;

@Component
public class AsyncTask {
	
	private static Logger logger = LoggerFactory.getLogger(AsyncTask.class);

	@Autowired
	private IOrgDeptService orgDeptService;
	@Autowired
	private IOrgDeptProService orgDeptProService;
	@Autowired
	private IOrgDeptHistoryService orgDeptHistoryService;
	@Autowired
	private IOrgSyncPsService orgSyncPsService;
	
	@Async
	public void dataCorrection(String company){
		try {
			long currentTimeMillis = System.currentTimeMillis();  
			orgDeptService.dataCorrection(company);
	        long currentTimeMillis1 = System.currentTimeMillis();  
	        logger.info("数据修正任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms"); 
		} catch (Exception e) {
			logger.info("异步修正数据异常，异常内容："+e.getMessage());
		}
	}
	
	@Async
	public void saveHistory(String company){
		try {
			//同步数据到历史表
			orgDeptHistoryService.saveDeptHistory(company);
			orgDeptHistoryService.saveJobHistory(company);
		} catch (Exception e) {
			logger.info("同步数据到历史表异常，异常内容："+e.getMessage());
		}
	}
	
	@Async
	public void savePro(String company,Integer version){
		try {
			//同步数据到生产表
			orgDeptProService.insertDeptByCompany(company, version);
			orgDeptProService.insertJobByCompany(company, version);
		} catch (Exception e) {
			logger.info("同步历史数据到生产表异常，异常内容："+e.getMessage());
		}
	}
	
	@Async
	public void saveSync(String type, String formType, String dataId, String json,Integer version){
		try {
			orgSyncPsService.save(type, formType, dataId, json,version);
		} catch (Exception e) {
			logger.info("插入同步PS表异常，异常内容："+e.getMessage());
		}
	}
	
	@Async
	public void syncPS(Integer version,Date effdt){
		orgSyncPsService.syncPSDept(version,effdt);
		orgSyncPsService.syncPSJob(version, effdt);
	}
}
