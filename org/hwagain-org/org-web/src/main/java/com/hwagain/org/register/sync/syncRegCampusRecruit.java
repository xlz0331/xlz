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
import com.hwagain.org.register.service.IRegEmployCollegeService;
import com.hwagain.org.util.JDBCConfig;

@Configurable
@EnableScheduling
@Component
public class syncRegCampusRecruit {
	private final static Logger logger = LoggerFactory.getLogger(syncRegPsBaseData.class);

	protected static ExecutorService executorService = null;
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表

	protected Date doDate;
	protected static String logClassName = "syncRegCampusRecruit";
	//protected Boolean testControl = true; // 【 测试用】控制只执行一次

	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}

	@Autowired
	IRegEmployCollegeService regEmployCollegeService;
	@Autowired
	JDBCConfig jDBCConfig;

	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
	//@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		//if (!testControl) return; // 【 测试用】
		//testControl = false;// 【 测试用】

		doDate = new Date();
		getCampusRecruitData();

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
	private void getCampusRecruitData() {

		//大学生招聘
		String sSql = " select a.ID ResumeId, b.GraduateYear,a.UserName,a.Gender,a.IdentNo,c.JobPositionName,a.Degree,d.Name as UniversityName, "
					  //+ "	    /*e.Name as MajorName,a.OtherMajorName,a.MajorName3,a.MajorName2,a.MajorName1, 	*/    "
					  + "	   ISNULL(ISNULL(ISNULL(ISNULL(e.Name,a.OtherMajorName),a.MajorName3),a.MajorName2),a.MajorName1) as MajorName, "
					  + "	   a.IsUse,isnull(a.IsEntryEmployee,0) IsEntryEmployee, t.Name Company ,2 pertype "
					  + "	 from DB67.CampusRecruit.dbo.t_Resume a "
					  + "	 left join DB67.CampusRecruit.dbo.AppSeason b on a.Season=b.Season "
					  + "	 left join DB67.CampusRecruit.dbo.t_JobPosition c on a.JobPositionId=c.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_University d on a.UniversityID=d.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_Major e on a.MajorID=e.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_Company t on t.ID = a.CompanyId "
					  + "	 where IsUse=1   "
					  + "  union all  "
					  + " select a.ID ResumeId, b.GraduateYear,a.UserName,a.Gender,a.IdentNo,c.JobPositionName,a.Degree,d.Name as UniversityName, "
					  + "	   ISNULL(ISNULL(ISNULL(ISNULL(e.Name,a.OtherMajorName),a.MajorName3),a.MajorName2),a.MajorName1) as MajorName, "
					  + "	   a.IsUse,isnull(a.IsEntryEmployee,0) IsEntryEmployee, t.Name Company ,3 pertype "
					  + "	 from DB67.CampusRecruit.dbo.t_Resume a "
					  + "	 left join DB67.CampusRecruit.dbo.AppSeason b on a.Season=b.Season "
					  + "	 left join DB67.CampusRecruit.dbo.t_JobPosition c on a.JobPositionId=c.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_University d on a.UniversityID=d.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_Major e on a.MajorID=e.ID "
					  + "	 left join DB67.CampusRecruit.dbo.Base_Company t on t.ID = a.CompanyId "
					  + "	 where IsUse=1   ";
		
		listMap.clear();
		listMap = queryOaData(sSql);
		doCampusRecruitInfo();		
		
	}

	public void doCampusRecruitInfo() {
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
							mapCampusRecruitObject(obj);
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
				doCampusRecruitInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private void mapCampusRecruitObject(Map<String, Object> obj) {
		Wrapper< RegEmployCollege> wrapper = new CriterionWrapper< RegEmployCollege>( RegEmployCollege.class);
		wrapper.eq("is_delete", 0);
		wrapper.eq("ResumeId",obj.get("ResumeId"));
		wrapper.eq("pertype", obj.get("pertype"));
		
		RegEmployCollege saveObj = regEmployCollegeService.selectFirst(wrapper);
		if(null ==saveObj )
		{
			saveObj = new  RegEmployCollege();
			saveObj.setFdId(String.valueOf(IdWorker.getId()));
			saveObj.setPertype((Integer) obj.get("pertype"));
			saveObj.setResumeId(String.valueOf(obj.get("ResumeId")));
			saveObj.setIsDelete(0);
			saveObj.setIsGiveup(0);
			//saveObj.setDocCreateId(UserUtils.getUserId());
			saveObj.setDocCreateTime(new Date());
			regEmployCollegeService.insert(saveObj);
		}							
	
		saveObj.setCompanyId(stringIsNotNull(obj.get("Company")));
		saveObj.setYear(String.valueOf((Integer)(obj.get("GraduateYear"))));
		saveObj.setName(stringIsNotNull(obj.get("UserName")));
		saveObj.setSex(stringIsNotNull(obj.get("Gender")));
		saveObj.setNid(stringIsNotNull(obj.get("IdentNo")));
		saveObj.setPosition(stringIsNotNull(obj.get("JobPositionName")));
		saveObj.setEducation(stringIsNotNull(obj.get("Degree")));
		saveObj.setSchool(stringIsNotNull(obj.get("UniversityName")));
		saveObj.setMajor(stringIsNotNull(obj.get("MajorName")));
		
		if(null==saveObj.getIsRegister() || saveObj.getIsRegister()==0)
			saveObj.setIsRegister((Integer)obj.get("IsEntryEmployee"));
		
		saveObj.setDocLastUpdateTime(new Date());
		regEmployCollegeService.updateAllById(saveObj);
	}

	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}
}
