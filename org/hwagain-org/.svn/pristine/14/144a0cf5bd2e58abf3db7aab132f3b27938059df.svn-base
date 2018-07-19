package com.hwagain.org.register.sync;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
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

import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.register.entity.RegEmployCollege;
import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.service.IRegEmployCollegeService;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.util.JDBCConfig;

@Configurable
@EnableScheduling
@Component
public class syncRegC3Data {
	private final static Logger logger = LoggerFactory.getLogger(syncRegPsBaseData.class);

	protected static ExecutorService executorService = null;
	protected Date doDate;
	protected static String logClassName = "syncRegC3Data";
	//protected Boolean testControl = true; // 【 测试用】控制只执行一次

	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}

	@Autowired
	IRegPeopleService regPeopleService;
	@Autowired
	JDBCConfig jDBCConfig;

	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
	//@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		 //if (!testControl) return; // 【 测试用】
		 //testControl = false;// 【 测试用】

		doDate = new Date();
		doC3PeopleData();

	}

	public JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	// 查询OA数据库数据
	private void queryC3Data(List<RegPeople> lPeople) {

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

			for (RegPeople p : lPeople) {
				try {
					String sql = " select empid from KQXT.AIO20140410155801.dbo.Com_EmpCard where cardstatuschgday is not null and carddispno ='"
							+ p.getEmplid() + "' ";
					pre = con.prepareStatement(sql);// 实例化预编译语句
					result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
					if (result.next()) {
						p.setIsEmpcar(1);
					}
				} catch (Exception e2) {
					//e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
	}

	public void doC3PeopleData() {
		try {

			Wrapper<RegPeople> wrapper = new CriterionWrapper<RegPeople>(RegPeople.class);
			wrapper.eq("is_delete", 0);
			wrapper.eq("is_empcar", 0);
			wrapper.and("emplid is not null");
			List<RegPeople> lPeople = regPeopleService.selectList(wrapper);

			queryC3Data(lPeople);

			for (RegPeople p : lPeople) {
				if (p.getIsEmpcar() == 1) {
					regPeopleService.updateById(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
