package com.hwagain.org.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.hwagain.org.config.entity.OrgVersionAudit;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.constant.AuditStatusConstant;

@Configuration
@ImportResource({"classpath:elasticJob.xml"})
public class SyncElasticJob implements SimpleJob{

	protected final Logger logger = LoggerFactory.getLogger(SyncElasticJob.class);
	
	protected static ExecutorService executorService = null;
	
	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}
	
	@Autowired
	private IOrgVersionAuditService orgVersionAuditService;
	
	static List<OrgVersionAudit> list = new ArrayList<OrgVersionAudit>();// 本次要处理的列表
	
	@Override
	public void execute(ShardingContext content) {
		switch (content.getShardingItem()) {
		case 0:
			this.getOrgSyncList();
			this.start();
			break;

		default:
			break;
		}
	}
	
	private void getOrgSyncList(){
		list = orgVersionAuditService.findWaitSyncAll();
	}

	public void start() {
		List<OrgVersionAudit> pendingList = new ArrayList<OrgVersionAudit>();
		try {
			if(list.size()<1){
				logger.info("未查询到待处理的数据...");
				return;
			}
			int n = 1;
			for (int i = 0; i < list.size(); i++) {
				//每次取100条处理
				if(n>100){
					break;
				}
				pendingList.add(list.get(i));//放入待处理集合中
				list.remove(list.get(i));//从原数据集合删除放入到待处理集合中的数据
				n++;
			}
			List<Future<Boolean>> lstFutureResult = new ArrayList<Future<Boolean>>();

			
			// 暂时不考虑重复问题，以后改
			for (OrgVersionAudit obj : pendingList) {
				Future<Boolean> futureResult = executorService.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						try {
							if(System.currentTimeMillis()>=obj.getEffectTime().getTime()){
								logger.info("开始同步公司（编号："+obj.getCompany()+"），同步版本："+obj.getVersion());
								orgVersionAuditService.syncOrg(obj.getCompany(), obj.getVersion());
								obj.setStatus(AuditStatusConstant.ORG_SYNC_OVER);
								orgVersionAuditService.updateById(obj);
								logger.info("同步完成");
							}else{
								logger.info("查询到同步任务，同步公司（编号："+obj.getCompany()+"）未到同步时间");
							}
							return true;
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
						}
						return false;
					}
				});

				lstFutureResult.add(futureResult);
			}
			// 等待 所有线程执行完成
			for (Future<Boolean> futureResult : lstFutureResult) {
				try {
					futureResult.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (list.size() > 0) {// 还有数据，继续处理
				start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
