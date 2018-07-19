package com.hwagain.org.register.sync;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.Assert;

import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.org.register.entity.RegBaseData;
import com.hwagain.org.register.entity.RegWorkerInterviewPicture;
import com.hwagain.org.register.service.IRegBaseDataService;
import com.hwagain.org.register.service.IRegWorkerInterviewPictureService;
import com.hwagain.org.util.JDBCConfig;

/**
 * <p>
 * 定时任务-岗位工照片
 * </p>
 *
 * @author guoym
 * @since 2018-05-07
 */

@Configurable
@EnableScheduling
@Component
public class syncRegWorkerPic {
	private final static Logger logger = LoggerFactory.getLogger(syncRegPsBaseData.class);

	protected static ExecutorService executorService = null;
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表

	protected Date doDate;
	//protected Boolean testControl = true; // 【 测试用】控制只执行一次

	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}

	@Autowired
	IRegWorkerInterviewPictureService regWorkerInterviewPictureService;
	@Autowired
	JDBCConfig jDBCConfig;

	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
	//@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		//if (!testControl) return; // 【 测试用】
		//testControl = false;// 【 测试用】

		doDate = new Date();
		getWorkerPicData();

	}

	public JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	// 查询OA数据库数据
	private List<Map<String, Object>> queryOaData(String sql) {

		String url = getjDBCConfig().getOAUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		String username = getjDBCConfig().getOAUsername();// 用户名,系统默认的账户名
		String password = getjDBCConfig().getOAPassword();// 你安装时选设置的密码

		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			// 获取进口数据
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			con = DriverManager.getConnection(url, username, password);// 获取连接

			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数

			ResultSetMetaData md = pre.getMetaData();
			int columnCount = md.getColumnCount();
			while (result.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), result.getObject(i));
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.err.println(listMap); //输出数据到控制台

		return listMap;
	}


	
	// 获取PS系统职位信息
	private void getWorkerPicData() {
		
		//处理最近一个月的数据
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
		calendar.add(Calendar.MONTH, -3);		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String sFromDate = sf.format(calendar.getTime());		

		String sSql = " SELECT ID ,姓名 name,身份证号码 nid,职位 position,手机拍照时间 picdate,上传服务器时间 uploaddate,图片名称 picname,图片 picurl,拍照人 username "
					 +"	FROM DB33.促销照片审核.dbo.面试人员照片 where 手机拍照时间 >'"+sFromDate+"' ";

		listMap.clear();
		listMap = queryOaData(sSql);

		if (listMap.size() < 1) {
			logger.info("未查询到同步的数据");
			return;
		}

		Wrapper<RegWorkerInterviewPicture> wrapper = new CriterionWrapper<RegWorkerInterviewPicture>(RegWorkerInterviewPicture.class);
		wrapper.and(" pic_date >='"+sFromDate+"' ");
		regWorkerInterviewPictureService.delete(wrapper);

		doWorkerPicInfo();

	}

	public void doWorkerPicInfo() {
		List<Map<String, Object>> pendingMap = new ArrayList<>();
		try {
			if (listMap.size() < 1) {
				logger.info("未查询到同步的数据...");
				return;
			}
			int n = 1;
			for (int i = 0; i < listMap.size(); i++) {
				// 每次取100条处理
				if (n > 100) {
					break;
				}
				pendingMap.add(listMap.get(i));// 放入待处理集合中
				listMap.remove(listMap.get(i));// 从原数据集合删除放入到待处理集合中的数据
				n++;
			}
			List<Future<Boolean>> lstFutureResult = new ArrayList<Future<Boolean>>();

			for (Map<String, Object> obj : pendingMap) {

				Future<Boolean> futureResult = executorService.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						try {
							mapPsJobObject(obj);
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

			if (listMap.size() > 0) {// 还有数据，继续处理
				doWorkerPicInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private void mapPsJobObject(Map<String, Object> obj) {
				
		RegWorkerInterviewPicture saveObj = new RegWorkerInterviewPicture();
		saveObj.setFdId(String.valueOf(IdWorker.getId()));
		saveObj.setPicId((Integer)(obj.get("ID")));
		saveObj.setName(stringIsNotNull(obj.get("name")));
		saveObj.setNid(stringIsNotNull(obj.get("nid")));
		saveObj.setPosition(stringIsNotNull(obj.get("position")));
		saveObj.setPicDate((Date)(obj.get("picdate")));
		saveObj.setUploadDate((Date)(obj.get("uploaddate")));		
		saveObj.setPicName(stringIsNotNull(obj.get("picname")));
		saveObj.setPicUrl(stringIsNotNull(obj.get("picurl")));
		saveObj.setUsername(stringIsNotNull(obj.get("username")));
		saveObj.setIsDelete(0);
		saveObj.setDocCreateTime(new Date());
		regWorkerInterviewPictureService.insert(saveObj);
	}

	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}
}
