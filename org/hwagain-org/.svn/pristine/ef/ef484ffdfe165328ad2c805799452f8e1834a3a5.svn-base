package com.hwagain.org.register.sync;

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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hwagain.org.register.entity.RegPsJob;
import com.hwagain.org.register.service.IRegPsJobService;
import com.hwagain.org.util.JDBCUtils;

/**
 * <p>
 * 定时任务-PS职务数据
 * </p>
 *
 * @author hanj
 * @since 2018-06-15
 */

@Configurable
@EnableScheduling
@Component
public class SyncRegPsJob{
	private final static Logger logger = LoggerFactory.getLogger(SyncRegPsJob.class);
	
	@Autowired
	private IRegPsJobService regPsJobService;
	
	protected static ExecutorService executorService = null;
	
	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}
	
	static List<RegPsJob> list = new ArrayList<RegPsJob>();// 本次要处理的列表

	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
	public void execute() {
		getPsJob();
		logger.info("查询PS职务数据共"+list.size()+"条...");
		logger.info("开始执行插入。。。");
		this.start();
		logger.info("执行完成。。。");
	}
	
	private void getPsJob(){
		list.addAll(JDBCUtils.getPsJob());
	}
	
	public void start() {
		List<RegPsJob> pendingList = new ArrayList<RegPsJob>();
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
			for (RegPsJob obj : pendingList) {
				Future<Boolean> futureResult = executorService.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						try {
							regPsJobService.saveOrUpdate(obj);
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
