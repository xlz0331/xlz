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
import org.springframework.util.Assert;

import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.org.register.entity.RegBaseData;
import com.hwagain.org.register.service.IRegBaseDataService;
import com.hwagain.org.util.JDBCConfig;

/**
 * <p>
 * 定时任务-PS基础数据
 * </p>
 *
 * @author guoym
 * @since 2018-05-07
 */

@Configurable
@EnableScheduling
@Component
public class syncRegPsBaseData {

	private final static Logger logger = LoggerFactory.getLogger(syncRegPsBaseData.class);

	protected static ExecutorService executorService = null;
	static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();// 本次要处理的列表

	protected Date doDate;
	protected String logType;
	protected String logMeno;
	protected List<String> repPositionNbr; // 判断职位重复
	protected static String logClassName = "syncSalaryPosition";
	//protected Boolean testControl = true; // 【 测试用】控制只执行一次

	static {
		int processCount = 50;
		executorService = new ThreadPoolExecutor(processCount / 2 + 1, processCount * 2 + 1, 5L, TimeUnit.MINUTES,
				new LinkedBlockingQueue<Runnable>());
	}

	@Autowired
	IRegBaseDataService regBaseDataService;
	@Autowired
	JDBCConfig jDBCConfig;

	@Scheduled(cron = "0 0 2 ? * *") // 每天2点执行
	//@Scheduled(cron = "0/30 * * * * ?") // 5秒一次【 测试用】
	public void Discount() {

		//if (!testControl) return; // 【 测试用】
		//testControl = false;// 【 测试用】

		doDate = new Date();
		getPsBaseData();

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

	//查询PS数据
	private List<Map<String, Object>> queryPsData(String sql) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
					
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
	private void getPsBaseData() {
		logMeno = "链接OA数据库查询PS基础数据";
		logger.info(logMeno);

		String sSql = " select * from  openquery(HCPRD,'   "
				+ " 		select  fieldname as TYPE,t.TYPECN ,fieldvalue CODE,xlatshortname NAME,xlatlongname NAME2, eff_status STATUS,to_char(SYNCID) SYNCID ,null SETID,''PSXLATITEM'' TABLENAME    "
				+ "		  from sysadm.PSXLATITEM p ,    "
				+ "			  (select ''SEX'' as TYPE , ''性别'' as TYPECN from dual union select ''MAR_STATUS'',''婚姻状况'' from dual union select ''HIGHEST_EDUC_LVL'',''学历'' from dual    "
				+ "			   union select ''C_BIRTH_PLACE_TYPE'',''出生地类型'' from dual  union select ''C_BLOOD_TYPE'',''血型'' from dual  union select ''POLITICAL_STA_CHN'',''政治面貌'' from dual    "
				+ "			   union select ''C_EMPLOY_PLACE'',''招聘来源地'' from dual  union select ''C_APPLYJOB_BY'',''求职途径'' from dual  union select ''C_APPLYJOB_WEB'',''求职网站'' from dual    "
				+ "			   union select ''C_TRAIN_TYPE'',''培养类别'' from dual  union select ''C_PRORES_PRD'',''试用期限'' from dual  union select ''RELATIONSHIP'',''关系'' from dual    "
				+ "			   union select ''EXAM_TYPE_CD'',''体检类型'' from dual  union select ''C_EXAM_RESULT'',''体检结果'' from dual  union select ''REFERRAL'',''后续安排'' from dual    "
				+ "			   union select ''C_DISABLE_GRADE'',''残障等级'' from dual  union select ''C_DISABLE_TYPE'',''残疾类型'' from dual  union select ''C_SI_TYPE'',''社保类型'' from dual    "
				+ "			   union select ''C_RND_RULE_COM'',''取整规则'' from dual  union select ''ACCOUNT_TYPE_PYE'',''帐户类型'' from dual     "
				+ "			  ) t where p.FIELDNAME=t.TYPE   "
				+ "		union all SELECT ''BIRTHSTATE'',''省份'',STATE,DESCR,DESCR ,''A'',null,BIRTHCOUNTRY,''PS_BIRTHSTATE_VW''  FROM SYSADM.PS_BIRTHSTATE_VW WHERE BIRTHCOUNTRY=''CHN''   "
				+ "		union all SELECT ''ETHNIC_GRP_CD'',''民族'',ETHNIC_GRP_CD,DESCRSHORT,DESCR50,eff_status,null, SETID,''PS_ETHNIC_GRP_TBL'' FROM SYSADM.PS_ETHNIC_GRP_TBL    "
				+ "		union all SELECT ''RELIGION_CD'',''宗教信仰'',RELIGION_CD,DESCRSHORT,DESCR,eff_status,null, SETID,''PS_RELIGION_TBL'' FROM SYSADM.PS_RELIGION_TBL    "
				+ "		union all SELECT ''HU_KOU'',''户口类型'',HUKOU_TYPE_CHN,DESCR,DESCR,''A'',null, null,''PS_HUKOU_T_DTL_CHN'' FROM SYSADM.PS_HUKOU_T_DTL_CHN    "
				+ "		union all SELECT ''CONTRIB_AREA_CHN'',''户口所在地'',CONTRIB_AREA_CHN,DESCR,DESCR,''A'',null, null,''PS_HUKOU_L_DTL_CHN'' FROM SYSADM.PS_HUKOU_L_DTL_CHN   "
				+ "		union all SELECT ''ACTION'',''操作'',ACTION,ACTION_DESCRSHORT,ACTION_DESCR,EFF_STATUS,null, null,''PS_ACTION_TBL'' FROM SYSADM.PS_ACTION_TBL   "
				+ "     union all SELECT ''ACTION_REASON'',''操作原因'',ACTION_REASON,DESCRSHORT,DESCR,EFF_STATUS,ACTION, null,''PS_ACTN_REASON_TBL'' FROM SYSADM.PS_ACTN_REASON_TBL  "
				+ "		union all SELECT ''SUPV_LVL_ID'',''职务等级'',SUPV_LVL_ID,DESCRSHORT,DESCR,eff_status,null, null,''PS_SUPVSR_LVL_TBL'' FROM SYSADM.PS_SUPVSR_LVL_TBL   "
				+ "     union all SELECT ''C_TECH_JOBCODE'',''行政-技术职务'',C_TECH_JOBCODE,descr,descr,''A'',supv_lvl_id, null,''PS_C_SUPV_TJOB_VW'' from SYSADM.PS_C_SUPV_TJOB_VW  "
				+ "		union all SELECT ''EMPL_CLASS'',''员工类别'',EMPL_CLASS,DESCRSHORT,DESCR,eff_status,null, SETID,''PS_EMPL_CLASS_TBL'' FROM SYSADM.PS_EMPL_CLASS_TBL where SETID=''CHN''   "
				+ "		union all SELECT ''EDU_RATING'',''学位'',REVIEW_RATING,DESCRSHORT,DESCR,''A'',null, null,''PS_REVW_RATING_TBL'' FROM SYSADM.PS_REVW_RATING_TBL WHERE RATING_MODEL = ''EDU''   "
				+ "		union all SELECT ''DRIVER_LICENSE_TYPE'',''驾驶证类型'',LICENSE_TYPE,DESCR,DESCR,EFF_STATUS,null, COUNTRY,''PS_DRIVER_LTYP_TBL'' FROM SYSADM.PS_DRIVER_LTYP_TBL    "
				+ "		union all SELECT ''EDLVLACHV'',''概要学历'',jpm_cat_item_id,DESCRSHORT,DESCRSHORT,EFF_STATUS,null, COUNTRY,''PS_JPM_CAT_ITEMS'' FROM SYSADM.PS_JPM_CAT_ITEMS where jpm_cat_type=''EDLVLACHV''   "
				+ "	') t  ";

		listMap.clear();
		listMap = queryOaData(sSql);

		if (listMap.size() < 1) {
			logMeno = "未查询到同步的数据";
			logger.info(logMeno);
			return;
		}

		logMeno = "保存岗位信息";
		logger.info(logMeno);

		// 全部删除【PS系统】的记录
		Wrapper<RegBaseData> wrapperBase = new CriterionWrapper<RegBaseData>(RegBaseData.class);
		wrapperBase.eq("datafrom", "PS系统");
		regBaseDataService.delete(wrapperBase);

		doPsBaseInfo();

	}

	public void doPsBaseInfo() {
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
				doPsBaseInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private void mapPsJobObject(Map<String, Object> obj) {
							
		RegBaseData saveObj = new RegBaseData();
		saveObj.setFdId(String.valueOf(IdWorker.getId()));
		saveObj.setType(stringIsNotNull(obj.get("TYPE")));
		saveObj.setTypecn(stringIsNotNull(obj.get("TYPECN")));
		saveObj.setCode(stringIsNotNull(obj.get("CODE")));
		saveObj.setName(stringIsNotNull(obj.get("NAME")));
		saveObj.setName2(stringIsNotNull(obj.get("NAME2")));
		saveObj.setStatus(stringIsNotNull(obj.get("STATUS")));
		saveObj.setSetid(stringIsNotNull(obj.get("SETID")));
		saveObj.setSyncid(stringIsNotNull(obj.get("SYNCID")));
		saveObj.setTablename(stringIsNotNull(obj.get("TABLENAME")));
		saveObj.setDatafrom("PS系统");
		regBaseDataService.insert(saveObj);
	}

	public String stringIsNotNull(Object obj) {
		return obj != null ? (String) obj : null;
	}

	public BigDecimal bigDecimalIsNotNull(Object obj) {
		return obj != null ? (BigDecimal) obj : BigDecimal.valueOf(0L);
	}


}