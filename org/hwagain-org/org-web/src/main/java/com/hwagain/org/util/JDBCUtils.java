package com.hwagain.org.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.api.org.dto.SysOrgPersonInfo;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.dept.dto.OrgPsDataDto;
import com.hwagain.org.dept.dto.Pstreedefn;
import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.register.entity.RegPsJob;

public class JDBCUtils {
	private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

	static JDBCConfig jDBCConfig;

	public static JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}
	
	private static final String TREE_NAME = getjDBCConfig().getPSNodeTree();

	// 连接OA系统oracle数据并执行插入语句
	public static int submitOrgAuditProcess(String fdId, String company, Integer version) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getOAUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String username = getjDBCConfig().getOAUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getOAPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, username, password);// 获取连接
			logger.info("连接成功...");
			SysOrgPersonInfo user = UserUtils.getUserInfo();
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String sql = "insert into td_AppFromOtherSystem("
					+ "temId,recordId,title,toUserId,toUsername,appstatus,apptime,triggerMan,memo) "
					+ "values(?,?,?,?,?,?,?,?,?)";// 预编译语句，“？”代表参数
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, "ea9d9e9d06894847a63046757769112d");
			pre.setString(2, fdId);
			pre.setString(3, "组织架构调整申请-" + user.getName());
			pre.setString(4, user.getFdEmployeeNumber());
			pre.setString(5, user.getName());
			pre.setString(6, "0");
			pre.setString(7, date);
			pre.setString(8, "组织架构系统");
			pre.setString(9, company + "," + version);
			int i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("组织架构调整-插入OA流程审批中间表记录条数：" + i);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 获取PS系统树形部门数据列表
	public static List<OrgPsDataDto> getPSTreeNode() {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		List<OrgPsDataDto> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			String sql = ""
					+"SELECT "
					+"TREE_NODE_NUM, "
					+"TREE_NODE, "
					+"TREE_NODE_NUM_END, "
					+"PARENT_NODE_NUM, "
					+"PARENT_NODE_NAME  "
				+"FROM "
					+"PSTREENODE A  "
				+"WHERE "
					+"TREE_NAME = '"+TREE_NAME+"'  "
					+"AND A.EFFDT = ( SELECT MAX( EFFDT ) FROM PSTREENODE WHERE TREE_NAME = A.TREE_NAME )  "
				+"ORDER BY "
					+"A.TREE_NODE_NUM ";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			OrgPsDataDto dto = null;
			while (result.next()) {
				dto = new OrgPsDataDto();
				dto.setDeptCode(result.getString("TREE_NODE"));
				dto.setNodeCode(result.getString("TREE_NODE_NUM"));
				dto.setNodeCodeEnd(result.getString("TREE_NODE_NUM_END"));
				dto.setParentDeptCode(result.getString("PARENT_NODE_NAME"));
				dto.setParentNodeCode(result.getString("PARENT_NODE_NUM"));
				list.add(dto);
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
		return list;
	}
	
	// 插入PS系统树形部门数据列表
	public static int savePSTreeNode(OrgPsDataDto op,Date effdt) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "insert into PSTREENODE(SETID,SETCNTRLVALUE,"
					+ "TREE_NAME,EFFDT,TREE_NODE_NUM,TREE_NODE,TREE_BRANCH,"
					+ "TREE_NODE_NUM_END,TREE_LEVEL_NUM,TREE_NODE_TYPE,PARENT_NODE_NUM,"
					+ "PARENT_NODE_NAME,OLD_TREE_NODE_NUM,NODECOL_IMAGE,NODEEXP_IMAGE)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, "SHARE");
			pre.setString(2, " ");
			pre.setString(3, TREE_NAME);
			pre.setDate(4, new java.sql.Date(effdt.getTime()));
			pre.setString(5, op.getNodeCode());
			pre.setString(6, op.getDeptCode());
			pre.setString(7, " ");
			pre.setString(8, op.getNodeCodeEnd());
			pre.setString(9, "0");
			pre.setString(10, "G");
			pre.setString(11, op.getParentNodeCode());
			pre.setString(12, op.getParentDeptCode());
			pre.setString(13, "N");
			pre.setString(14, " ");
			pre.setString(15, " ");
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功插入到PS系统"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入PS系统树形组织表错误，执行sql为："+sql);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 按名称查询PS部门树
	public static Pstreedefn getPStree() {
		//TODO
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		ResultSet result = null;// 创建一个结果集对象
		Pstreedefn pf = new Pstreedefn();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "select * from pstreedefn where TREE_NAME='"+TREE_NAME+"' "
					+ "and EFFDT = (select MAX(EFFDT) from pstreedefn where TREE_NAME='"+TREE_NAME+"')";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				pf.setALL_VALUES(result.getString("ALL_VALUES"));
				pf.setBRANCH_IMAGE(result.getString("BRANCH_IMAGE"));
				pf.setDESCR(result.getString("DESCR"));
				pf.setDUPLICATE_LEAF(result.getString("DUPLICATE_LEAF"));
				pf.setEFF_STATUS(result.getString("EFF_STATUS"));
				pf.setEFFDT(result.getDate("EFFDT"));
				pf.setLASTUPDOPRID(result.getString("LASTUPDOPRID"));
				pf.setLEAF_COUNT(result.getInt("LEAF_COUNT"));
				pf.setLEAF_IMAGE(result.getString("LEAF_IMAGE"));
				pf.setLEVEL_COUNT(result.getInt("LEVEL_COUNT"));
				pf.setNODE_COUNT(result.getInt("NODE_COUNT"));
				pf.setNODECOL_IMAGE(result.getString("NODECOL_IMAGE"));
				pf.setNODEEXP_IMAGE(result.getString("NODEEXP_IMAGE"));
				pf.setSETCNTRLVALUE(result.getString("SETCNTRLVALUE"));
				pf.setSETID(result.getString("SETID"));
				pf.setTREE_ACC_METHOD(result.getString("TREE_ACC_METHOD"));
				pf.setTREE_ACC_SEL_OPT(result.getString("TREE_ACC_SEL_OPT"));
				pf.setTREE_ACC_SELECTOR(result.getString("TREE_ACC_SELECTOR"));
				pf.setTREE_CATEGORY(result.getString("TREE_CATEGORY"));
				pf.setTREE_HAS_RANGES(result.getString("TREE_HAS_RANGES"));
				pf.setTREE_IMAGE(result.getString("TREE_IMAGE"));
				pf.setTREE_NAME(result.getString("TREE_NAME"));
				pf.setTREE_STRCT_ID(result.getString("TREE_STRCT_ID"));
				pf.setUSE_LEVELS(result.getString("USE_LEVELS"));
				pf.setVALID_TREE(result.getString("VALID_TREE"));
				pf.setVERSION(result.getInt("VERSION"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入PS系统树形组织表错误，执行sql为："+sql);
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
		return pf;
	}
	
	// 按名称查询PS部门树
		public static Integer getPStreeMaxVersion() {
			//TODO
			Connection con = null;// 创建一个数据库连接
			PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
			String sql = "";
			ResultSet result = null;// 创建一个结果集对象
			Integer version = 0;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
				logger.info("开始尝试连接数据库...");
				String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
				String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
				String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
				con = DriverManager.getConnection(url, user, password);// 获取连接
				logger.info("连接成功...");
				sql = "SELECT MAX(VERSION) as version FROM PSTREEDEFN";
				pre = con.prepareStatement(sql);// 实例化预编译语句
				result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
				if(result.next()){
					version = result.getInt("version");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("插入PS系统树形组织表错误，执行sql为："+sql);
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
			return version;
		}
	
	// 插入PS系统树形部门数据列表
	public static int savePSTree(Date effdt,Integer size) {
		savePSTREEPROMPT(effdt);
		Pstreedefn pf = getPStree();
		if(pf==null){
			return 0;
		}
		//TODO
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "insert into PSTREEDEFN(SETID,SETCNTRLVALUE,TREE_NAME,EFFDT,"
					+ "EFF_STATUS,VERSION,"
					+ "TREE_STRCT_ID,DESCR,ALL_VALUES,USE_LEVELS,VALID_TREE,"
					+ "LEVEL_COUNT,NODE_COUNT,LEAF_COUNT,TREE_HAS_RANGES,DUPLICATE_LEAF,"
					+ "TREE_CATEGORY,TREE_ACC_METHOD,TREE_ACC_SELECTOR,"
					+ "TREE_ACC_SEL_OPT,LASTUPDOPRID,TREE_IMAGE,BRANCH_IMAGE,NODECOL_IMAGE,"
					+ "NODEEXP_IMAGE,LEAF_IMAGE) values"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, pf.getSETID());
			pre.setString(2, pf.getSETCNTRLVALUE());
			pre.setString(3, pf.getTREE_NAME());
			pre.setDate(4, new java.sql.Date(effdt.getTime()));
			pre.setString(5, pf.getEFF_STATUS());
			pre.setInt(6, getPStreeMaxVersion()+1);
			pre.setString(7, pf.getTREE_STRCT_ID());
			pre.setString(8, pf.getDESCR());
			pre.setString(9, pf.getALL_VALUES());
			pre.setString(10, pf.getUSE_LEVELS());
			pre.setString(11, pf.getVALID_TREE());
			pre.setInt(12, pf.getLEVEL_COUNT());
			pre.setInt(13, size);
			pre.setInt(14, pf.getLEAF_COUNT());
			pre.setString(15, pf.getTREE_HAS_RANGES());
			pre.setString(16, pf.getDUPLICATE_LEAF());
			pre.setString(17, pf.getTREE_CATEGORY());
			pre.setString(18, pf.getTREE_ACC_METHOD());
			pre.setString(19, pf.getTREE_ACC_SELECTOR());
			pre.setString(20, pf.getTREE_ACC_SEL_OPT());
			pre.setString(21, pf.getLASTUPDOPRID());
			pre.setString(22, pf.getTREE_IMAGE());
			pre.setString(23, pf.getBRANCH_IMAGE());
			pre.setString(24, pf.getNODECOL_IMAGE());
			pre.setString(25, pf.getNODEEXP_IMAGE());
			pre.setString(26, pf.getLEAF_IMAGE());
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功插入到PS系统"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入PS系统树形组织表错误，执行sql为："+sql);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 插入PS系统树形部门数据提示表
	public static int savePSTREEPROMPT(Date effdt) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "insert into PSTREEPROMPT(SETID,SETCNTRLVALUE,TREE_NAME,EFFDT,TREE_BRANCH)"
					+ " values(?,?,?,?,?)";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, "SHARE");
			pre.setString(2, " ");
			pre.setString(3, TREE_NAME);
			pre.setDate(4, new java.sql.Date(effdt.getTime()));
			pre.setString(5, " ");
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功插入到PS系统"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入PS系统树形组织提示表错误，执行sql为："+sql);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 插入PS系统树形部门数据列表
	public static int saveBatchPSTreeNode(List<OrgPsDataDto> ops,Date effdt) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "insert into PSTREENODE("
					+ "SETID,"
					+ "SETCNTRLVALUE,"
					+ "TREE_NAME,"
					+ "EFFDT,"
					+ "TREE_NODE_NUM,"
					+ "TREE_NODE,"
					+ "TREE_BRANCH,"
					+ "TREE_NODE_NUM_END,"
					+ "TREE_LEVEL_NUM,"
					+ "TREE_NODE_TYPE,"
					+ "PARENT_NODE_NUM,"
					+ "PARENT_NODE_NAME,"
					+ "OLD_TREE_NODE_NUM,"
					+ "NODECOL_IMAGE,"
					+ "NODEEXP_IMAGE)"
					+ "values";
			StringBuffer values = new StringBuffer("");
			for (OrgPsDataDto op : ops) {
				values.append(",("
						+ "'SHARE',"
						+ "' ',"
						+ "'"+TREE_NAME+"',"
						+ "'"+new java.sql.Date(effdt.getTime())+"',"
						+ "'"+op.getNodeCode()+"',"
						+ "'"+op.getDeptCode()+"',"
						+ "' ',"
						+ "'"+op.getNodeCodeEnd()+"',"
						+ "'0',"
						+ "'G',"
						+ "'"+op.getParentNodeCode()+"',"
						+ "'"+op.getParentDeptCode()+"',"
						+ "'N',"
						+ "' ',"
						+ "' '"
						+ ")");
			}
			if(StringUtil.isNotNull(values.toString())){
				sql += values.toString().substring(1);
			}
			pre = con.prepareStatement(sql);// 实例化预编译语句
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功插入到PS系统部门树表"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入PS系统树形组织表错误，执行sql为："+sql);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 获取PS系统树形部门数据列表
	public static List<RegPsJob> getPsJob() {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		List<RegPsJob> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			String sql = "SELECT SETID,JOBCODE,DESCR,DESCRSHORT,EFF_STATUS FROM PS_C_JOBCODE_VW";
					
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			RegPsJob job = null;
			while (result.next()) {
				job = new RegPsJob();
				job.setFdId(String.valueOf(IdWorker.getId()));
				job.setCode(result.getString("JOBCODE"));
				job.setIsDelete("A".equals(result.getString("EFF_STATUS"))?0:1);
				job.setName(result.getString("DESCR"));
				job.setSetid(result.getString("SETID"));
				job.setShortname(result.getString("DESCRSHORT"));
				list.add(job);
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
		return list;
	}
	
	// 插入PS系统职位预录入表
	public static int addPSPOSTIONINPUT(OrgJob job,Date effdt,String jobcode) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "insert into PS_C_POSI_INPUT(POSITION_NBR,EFFDT,BUSINESS_UNIT,JOBCODE,DESCR,DESCRSHORT,REPORTS_TO,MAX_HEAD_COUNT,C_CHK_STATUS,C_INPUT_USER)"
					+ " values(?,?,?,?,?,?,?,?,?,?)";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, job.getJobCode());
			pre.setDate(2, new java.sql.Date(effdt.getTime()));
			pre.setString(3, getBUSINESS_UNIT(job.getCompany()));
			pre.setString(4, jobcode);
			pre.setString(5, job.getJobName());
			pre.setString(6, job.getJobName());
			pre.setString(7, StringUtil.isNotNull(job.getParentId())?job.getParentId():" ");
			pre.setInt(8, 5);
			pre.setString(9, "0");
			pre.setString(10, "ZHUJIAWEI");
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功插入到PS系统(表：PS_C_POSI_INPUT)"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("成功插入到PS系统(表：PS_C_POSI_INPUT)错误，执行sql为："+sql);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 插入PS系统职位预录入表
	public static int updatePSPOSTIONINPUT(OrgJob job,Date effdt,Date old) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		String sql = "";
		int i = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			sql = "update PS_C_POSI_INPUT set EFFDT=?,DESCR=?,DESCRSHORT=?,REPORTS_TO=?,MAX_HEAD_COUNT=?"
					+ " where POSITION_NBR=?";
			if(old!=null){
				sql += " and EFFDT=?";
			}
			pre = con.prepareStatement(sql);// 实例化预编译语句
			pre.setDate(1, new java.sql.Date(effdt.getTime()));
			pre.setString(2, job.getJobName());
			pre.setString(3, job.getJobName());
			pre.setString(4, StringUtil.isNotNull(job.getParentId())?job.getParentId():" ");
			pre.setInt(5, job.getMaxCompile());
			pre.setString(6, job.getJobCode());
			if(old!=null){
				pre.setDate(7, new java.sql.Date(old.getTime()));
			}
			i = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数
			logger.info("成功修改PS系统(表：PS_C_POSI_INPUT)"+i+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改PS系统(表：PS_C_POSI_INPUT)错误，执行sql为："+sql);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			logger.error("修改PS系统(表：PS_C_POSI_INPUT)错误，参数为"+JSONObject.toJSONString(job)+","+sf.format(effdt)+","+(old!=null?sf.format(old):""));
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 获取PS系统树形部门数据列表
		public static Map<String,Object> getPSPOSTIONINPUT(String code) {
			Connection con = null;// 创建一个数据库连接
			PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
			ResultSet result = null;// 创建一个结果集对象
			Map<String,Object> map = new HashMap<>();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
				logger.info("开始尝试连接数据库...");
				String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
				String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
				String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
				con = DriverManager.getConnection(url, user, password);// 获取连接
				logger.info("连接成功...");
				String sql = "select POSITION_NBR,EFFDT from PS_C_POSI_INPUT where POSITION_NBR = '"+code+"'";
						
				pre = con.prepareStatement(sql);// 实例化预编译语句
				result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
				while (result.next()) {
					map.put("POSITION_NBR", result.getString("POSITION_NBR"));
					map.put("EFFDT", result.getDate("EFFDT"));
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
			return map;
		}
	
	public static String getBUSINESS_UNIT(String company){
		switch (company) {
		case "001":
			
			return "BU001";
		case "002":
			
			return "BU001";
		case "003":
			
			return "BU004";
		case "004":
			
			return "BU005";
		case "005":
			
			return "BU008";
		case "006":
			
			return "BU003";
		case "007":
			
			return "BU001";
		case "008":
			
			return "BU007";
		case "009":
			
			return "BU001";
		case "010":
			
			return "BU002";
		case "011":
			
			return "BU012";
		default:
			return "BU001";
		}
	}
}
