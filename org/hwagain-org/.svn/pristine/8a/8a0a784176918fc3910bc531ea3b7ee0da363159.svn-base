package com.hwagain.org.register.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.register.entity.RegJob;
import com.hwagain.org.register.entity.RegPersonal;
import com.hwagain.org.register.entity.RegPersonalEducation;
import com.hwagain.org.register.entity.RegPersonalEmployWay;
import com.hwagain.org.register.entity.RegPersonalExigence;
import com.hwagain.org.register.entity.RegPersonalFamily;
import com.hwagain.org.register.entity.RegPersonalWork;
import com.hwagain.org.register.entity.RegSyncPs;
import com.hwagain.org.register.service.IRegSyncPsService;
import com.hwagain.org.util.HttpClientUtils;
import com.hwagain.org.util.JDBCConfig;
import com.hwagain.org.util.ResultMessage;

public class PsCiUtils {

	private final static String PS_WSDL_HCSIT = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/";
	private final static String PS_WSDL_HCPRD = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/";
	private static final Logger logger = LoggerFactory.getLogger(PsCiUtils.class);

	static JDBCConfig jDBCConfig;
	static IRegSyncPsService regSyncPsService;

	public static JDBCConfig getjDBCConfig() {
		if (jDBCConfig == null) {
			jDBCConfig = SpringBeanUtil.getBean(JDBCConfig.class);
		}
		return jDBCConfig;
	}

	public static String getPsWsdl() {
		return getjDBCConfig().getPSUrl().contains("HCSIT") ? PS_WSDL_HCSIT : PS_WSDL_HCPRD;
	}

	public static int execPsSql(List<String> lSql) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");

			int result = 0;
			for (String sql : lSql) {
				try {
					pre = con.prepareStatement(sql);
					result = pre.executeUpdate();
				} catch (Exception e2) {
					logger.info("执行PS语句出错：" + sql);
					logger.info(e2.getMessage());
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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

	// 人员信息-创建-生成员工工号
	public static Integer personalCreate(RegPersonal p) {
		String sPsWsdl = getPsWsdl() + "CI_C_CI_PERSON_DATA3.1.wsdl";
		String sAction = "CI_C_CI_PERSON_DATA3_C.V1";
		String sSchemas = "M378828.V1";
		StringBuffer xml;
		xml = personalPsCiXml_Create(p, sSchemas);

		ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);
		syncLog("create", "reg_personal", p.getFdId(), rm, JSONObject.toJSONString(p), xml.toString());

		logger.info("人员信息-人员创建:" + p.getEmplid() + ";" + p.getFdId() + ";");

		return rm.getStatus();
	}

	// 根据身份证号码获取员工工号
	public static String personalGetEmplidByNid(String sNid) {
		String sEmplid = "";
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
			String sql = " select max(emplid) nid from SYSADM.PS_PERS_NID where national_id ='" + sNid + "' ";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			if (result.next()) {
				sEmplid = result.getString("nid");
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
		return sEmplid;
	}

	public static StringBuffer personalPsCiXml_Create(RegPersonal p, String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 更新时所有的EFFDT都是关键字-所以统一使用DocCreatetime
		String sEffDate = sf.format(p.getDocCreateTime());
		String sName = p.getName();
		String sLastName = sName.substring(0, 1);
		String sFirstName = sName.replace(sLastName, "");

		StringBuffer xml = new StringBuffer("");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m37=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m37:Create__CompIntfc__C_CI_PERSON_DATA3>");
		xml.append("<!--<m37:EMPLID>?</m37:EMPLID>-->");
		xml.append("<m37:BIRTHDATE>" + sf.format(p.getBirthdate()) + "</m37:BIRTHDATE>");
		xml.append("<m37:BIRTHPLACE>" + p.getBirthPlace() + "</m37:BIRTHPLACE>");
		xml.append("<m37:BIRTHCOUNTRY>CHN</m37:BIRTHCOUNTRY>");
		xml.append("<m37:BIRTHSTATE>" + p.getBirthProvince() + "</m37:BIRTHSTATE>");
		xml.append("<m37:DERIVED_EMP_0>Y</m37:DERIVED_EMP_0>");
		xml.append("<m37:DERIVED_CWR_0>N</m37:DERIVED_CWR_0>");
		xml.append("<m37:DERIVED_POI_0>N</m37:DERIVED_POI_0>");
		xml.append("<m37:NAME_TYPE_VW>");
		xml.append("<m37:NAME_TYPE>PRI</m37:NAME_TYPE>");
		xml.append("<m37:NAMES>");
		xml.append("<m37:NAME_TYPE>PRI</m37:NAME_TYPE>");
		xml.append("<m37:EFFDT>" + sEffDate + "</m37:EFFDT>");
		xml.append("<m37:COUNTRY_NM_FORMAT>CHN</m37:COUNTRY_NM_FORMAT>");
		xml.append("<m37:LAST_NAME_0>" + sLastName + "</m37:LAST_NAME_0>");
		xml.append("<m37:FIRST_NAME_0>" + sFirstName + "</m37:FIRST_NAME_0>");
		xml.append("</m37:NAMES>");
		xml.append("</m37:NAME_TYPE_VW>");
		xml.append("<m37:PERS_DATA_EFFDT>");
		xml.append("<m37:EFFDT_1>" + sEffDate + "</m37:EFFDT_1>");
		xml.append("<m37:MAR_STATUS>" + p.getMarry() + "</m37:MAR_STATUS>");
		xml.append("<m37:SEX_0>" + p.getSex() + "</m37:SEX_0>");
		// 最高学历-09-本科-03-初中
		xml.append("<m37:HIGHEST_EDUC_LVL_0>" + p.getHighestEduc() + "</m37:HIGHEST_EDUC_LVL_0>");
		xml.append("</m37:PERS_DATA_EFFDT>");
		xml.append("<m37:PERS_NID>");
		xml.append("<m37:NATIONAL_ID_TYPE>CHN18</m37:NATIONAL_ID_TYPE>");
		xml.append("<m37:NATIONAL_ID>" + p.getNid() + "</m37:NATIONAL_ID>");
		xml.append("<m37:PRIMARY_NID>Y</m37:PRIMARY_NID>");
		xml.append("<m37:NID_SPECIAL_CHAR>" + p.getNid() + "</m37:NID_SPECIAL_CHAR>");
		xml.append("</m37:PERS_NID>");
		xml.append("<m37:ADDRESS_TYPE_VW>");
		xml.append("<m37:ADDRESSES>");
		xml.append("<m37:EFFDT_3>" + sEffDate + "</m37:EFFDT_3>");
		xml.append("<m37:COUNTRY_1>CHN</m37:COUNTRY_1>");
		xml.append("<m37:ADDRESS1>" + p.getAddress2() + "</m37:ADDRESS1>");
		xml.append("</m37:ADDRESSES>");
		xml.append("</m37:ADDRESS_TYPE_VW>");
		xml.append("<m37:PERSONAL_PHONE>");
		xml.append("<m37:PHONE_TYPE>CELL</m37:PHONE_TYPE>");
		xml.append("<m37:PHONE>" + getNotEmptyStr(p.getPhone()) + "</m37:PHONE>");
		xml.append("<m37:PREF_PHONE_FLAG>Y</m37:PREF_PHONE_FLAG>");
		xml.append("</m37:PERSONAL_PHONE>");
		xml.append("<m37:PERSONAL_PHONE>");
		xml.append("<m37:PHONE_TYPE>HOME</m37:PHONE_TYPE>");
		xml.append("<m37:PHONE>" + getNotEmptyStr(p.getPhoneHome()) + "</m37:PHONE>");
		xml.append("<m37:PREF_PHONE_FLAG>N</m37:PREF_PHONE_FLAG>");
		xml.append("</m37:PERSONAL_PHONE>");
		xml.append("<m37:EMAIL_ADDRESSES>");
		xml.append("<m37:E_ADDR_TYPE>BUSN</m37:E_ADDR_TYPE>");
		xml.append("<m37:EMAIL_ADDR>" + getNotEmptyStr(p.getEmial()) + "</m37:EMAIL_ADDR>");
		xml.append("<m37:PREF_EMAIL_FLAG>Y</m37:PREF_EMAIL_FLAG>");
		xml.append("</m37:EMAIL_ADDRESSES>");
		xml.append("</m37:Create__CompIntfc__C_CI_PERSON_DATA3>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	// 人员信息-编辑-同步
	public static String personalToPs(RegPersonal p) {
		String result = "";

		String sPsWsdl = getPsWsdl() + "CI_C_CI_PERSON_DATA3.1.wsdl";
		String sAction = "CI_C_CI_PERSON_DATA3_UD.V1";
		String sSchemas = "M967068.V1";
		StringBuffer xml;

		xml = personalPsCiXml_Update(p, sSchemas);

		ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);
		syncLog("update", "reg_personal", p.getFdId(), rm, JSONObject.toJSONString(p), xml.toString());

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 更新时所有的EFFDT都是关键字-所以统一使用DocCreatetime
		String sEffDate = sf.format(p.getDocCreateTime());
		List<String> lSql = new ArrayList<String>();
		// 民族
		lSql.add("DELETE FROM SYSADM.PS_DIVERS_ETHNIC WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add(
				"insert into SYSADM.ps_DIVERS_ETHNIC(Emplid, Reg_Region,Ethnic_Grp_Cd, Setid, Aps_Ec_Nds_Aus, Primary_Indicator) values ('"
						+ p.getEmplid() + "','CHN', '" + p.getNation() + "', 'CHN', 'N', 'N')");
		// 宗教信仰
		lSql.add("DELETE FROM SYSADM.PS_DIVERS_RELIGION WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add("insert into SYSADM.ps_DIVERS_RELIGION(Emplid, Reg_Region, Religion_Cd, Setid) values ('"
				+ p.getEmplid() + "','CHN', '" + p.getReligion() + "', 'CHN')");
		// 户口信息
		lSql.add("DELETE FROM SYSADM.PS_PERS_HUKOU_CHN WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add("insert into SYSADM.ps_PERS_HUKOU_CHN(Emplid, Effdt, Hukou_Type_Chn, Contrib_Area_Chn) values ('"
				+ p.getEmplid() + "',date'" + sEffDate + "', '" + p.getHukouType() + "', '" + p.getHukouPlace() + "')");
		// 籍贯信息
		lSql.add("DELETE FROM SYSADM.ps_PERS_NATIVE_CHN WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add(
				"insert into SYSADM.ps_PERS_NATIVE_CHN(Emplid, Birthstate, c_Birth_City, c_Birth_County, Native_Place_Chn) values ('"
						+ p.getEmplid() + "','" + p.getBirthProvince() + "', ' ', ' '， '" + p.getNativePlace() + "')");
		// 工作信息-首次工作时间-工龄起算日期
		lSql.add("DELETE FROM SYSADM.PS_PERS_WRKLIF_CHN WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add("insert into SYSADM.PS_PERS_WRKLIF_CHN(EMPLID,START_DT_CHN, START_HIRE_DT) values ('" + p.getEmplid()
				+ "',date'" + sf.format(p.getFirstworkdate()) + "', date'" + sEffDate + "')");
		// 政治面貌
		lSql.add("DELETE FROM SYSADM.PS_PERS_POLITY_CHN WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add("insert into SYSADM.PS_PERS_POLITY_CHN(EMPLID, Effdt, POLITICAL_STA_CHN) values ('" + p.getEmplid()
				+ "',date'" + (null == p.getInpartydate() ? sEffDate : sf.format(p.getInpartydate())) + "', '"
				+ p.getPolitics() + "')");
		// 视力-血型-是否独生子女-身高-特长
		lSql.add("DELETE FROM SYSADM.PS_PERS_DATA_OTH WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add(
				"INSERT INTO SYSADM.PS_PERS_DATA_OTH(EMPLID,C_LEFT_VISION, C_RIGHT_VISION,C_BLOOD_TYPE, C_ONLY_CHILD, HEIGHT_IN_LINES, DESCR100,C_CHECK_EDU) "
						+ " values ('" + p.getEmplid() + "', '" + p.getVisionLeft() + "', '" + p.getVisionRight()
						+ "', '" + p.getBlood() + "', '" + (p.getOnlyChild().equals("1") ? "Y" : "N") + "', '"
						+ p.getHeight() + "', '" + p.getSpecialty() + "',' ')");
		// QQ
		lSql.add("DELETE FROM SYSADM.PS_PERSON_IMCHAT  WHERE EMPLID='" + p.getEmplid()
				+ "' AND MCF_IM_PROTOCOL = 'QQ' AND MCFIMDOMAIN='QQ'");
		if (null != p.getQq() && !p.getQq().isEmpty())
			lSql.add(
					"INSERT INTO SYSADM.PS_PERSON_IMCHAT(EMPLID,MCF_IM_PROTOCOL,MCFIMDOMAIN, MCF_IMUSERID, PREF_CHATID_FLAG) VALUES ('"
							+ p.getEmplid() + "', 'QQ', 'QQ', '" + p.getQq() + "', 'Y')");
		// 地址-全删全增
		lSql.add("DELETE FROM SYSADM.PS_ADDRESSES  WHERE EMPLID='" + p.getEmplid()
				+ "' AND ADDRESS_TYPE IN ('10','20', '30')");
		// 身份证地址
		lSql.add(
				"INSERT INTO SYSADM.PS_ADDRESSES(EMPLID, ADDRESS_TYPE, EFFDT, EFF_STATUS, COUNTRY, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, CITY, STATE,NUM1, NUM2, HOUSE_TYPE, ADDR_FIELD1, ADDR_FIELD2, ADDR_FIELD3, COUNTY, POSTAL, GEO_CODE, IN_CITY_LIMIT, ADDRESS1_AC, ADDRESS2_AC, ADDRESS3_AC, CITY_AC, REG_REGION, LASTUPDDTTM, LASTUPDOPRID) "
						+ " VALUES ('" + p.getEmplid() + "','10', DATE'" + sEffDate + "', 'A', 'CHN', '"
						+ p.getAddress()
						+ "', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', sysdate, 'ZHUJIAWEI')");
		// 员工编号, 注册信息.出生日期, 现居住地_详细, 现居住地_市, 现居住地_省,录入人, "20", DateTime.Now
		// 现居住地址
		lSql.add(
				"INSERT INTO SYSADM.PS_ADDRESSES(EMPLID, ADDRESS_TYPE, EFFDT, EFF_STATUS, COUNTRY, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, CITY, STATE,NUM1, NUM2, HOUSE_TYPE, ADDR_FIELD1, ADDR_FIELD2, ADDR_FIELD3, COUNTY, POSTAL, GEO_CODE, IN_CITY_LIMIT, ADDRESS1_AC, ADDRESS2_AC, ADDRESS3_AC, CITY_AC, REG_REGION, LASTUPDDTTM, LASTUPDOPRID) "
						+ " VALUES ('" + p.getEmplid() + "','20', DATE'" + sEffDate + "', 'A', 'CHN', '"
						+ p.getAddress1()
						+ "', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', sysdate, 'ZHUJIAWEI')");
		// 邮寄地址
		lSql.add(
				"INSERT INTO SYSADM.PS_ADDRESSES(EMPLID, ADDRESS_TYPE, EFFDT, EFF_STATUS, COUNTRY, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, CITY, STATE,NUM1, NUM2, HOUSE_TYPE, ADDR_FIELD1, ADDR_FIELD2, ADDR_FIELD3, COUNTY, POSTAL, GEO_CODE, IN_CITY_LIMIT, ADDRESS1_AC, ADDRESS2_AC, ADDRESS3_AC, CITY_AC, REG_REGION, LASTUPDDTTM, LASTUPDOPRID) "
						+ " VALUES ('" + p.getEmplid() + "','30', DATE'" + sEffDate + "', 'A', 'CHN', '"
						+ p.getAddress3()
						+ "', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', sysdate, 'ZHUJIAWEI')");
		// 家庭地址
		lSql.add("UPDATE SYSADM.PS_ADDRESSES set ADDRESS1= '" + p.getAddress2() + "' where EMPLID = '" + p.getEmplid()
				+ "' and EFFDT=DATE'" + sEffDate + "' and ADDRESS_TYPE='HOME' ");
		// 出生地类型
		lSql.add("UPDATE SYSADM.PS_PERSON SET  C_BIRTH_PLACE_TYPE ='" + p.getBirthPlaceType() + "' where EMPLID = '"
				+ p.getEmplid() + "'   ");

		execPsSql(lSql);

		if (rm.getStatus() == 200)
			result = "同步成功";
		else
			result = "同步失败";
		logger.info("同步PS-人员信息:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);

		return result;
	}

	public static StringBuffer personalPsCiXml_Update(RegPersonal p, String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 更新时所有的EFFDT都是关键字-所以统一使用DocCreatetime
		String sEffDate = sf.format(p.getDocCreateTime());
		String sName = p.getName();
		String sLastName = sName.substring(0, 1);
		String sFirstName = sName.replace(sLastName, "");

		StringBuffer xml = new StringBuffer("");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m37=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m37:Updatedata__CompIntfc__C_CI_PERSON_DATA3>");
		xml.append("<m37:EMPLID>" + p.getEmplid() + "</m37:EMPLID>");
		xml.append("<m37:BIRTHDATE>" + sf.format(p.getBirthdate()) + "</m37:BIRTHDATE>");
		xml.append("<m37:BIRTHPLACE>" + p.getBirthPlace() + "</m37:BIRTHPLACE>");
		xml.append("<m37:BIRTHCOUNTRY>CHN</m37:BIRTHCOUNTRY>");
		xml.append("<m37:BIRTHSTATE>" + p.getBirthProvince() + "</m37:BIRTHSTATE>");
		xml.append("<m37:DERIVED_EMP_0>Y</m37:DERIVED_EMP_0>");
		xml.append("<m37:DERIVED_CWR_0>N</m37:DERIVED_CWR_0>");
		xml.append("<m37:DERIVED_POI_0>N</m37:DERIVED_POI_0>");
		xml.append("<m37:NAME_TYPE_VW>");
		xml.append("<m37:NAME_TYPE>PRI</m37:NAME_TYPE>");
		xml.append("<m37:NAMES>");
		xml.append("<m37:NAME_TYPE>PRI</m37:NAME_TYPE>");
		xml.append("<m37:EFFDT>" + sEffDate + "</m37:EFFDT>");
		xml.append("<m37:COUNTRY_NM_FORMAT>CHN</m37:COUNTRY_NM_FORMAT>");
		xml.append("<m37:LAST_NAME_0>" + sLastName + "</m37:LAST_NAME_0>");
		xml.append("<m37:FIRST_NAME_0>" + sFirstName + "</m37:FIRST_NAME_0>");
		xml.append("</m37:NAMES>");
		xml.append("</m37:NAME_TYPE_VW>");
		xml.append("<m37:PERS_DATA_EFFDT>");
		xml.append("<m37:EFFDT_1>" + sEffDate + "</m37:EFFDT_1>");
		xml.append("<m37:MAR_STATUS>" + p.getMarry() + "</m37:MAR_STATUS>");
		xml.append("<m37:SEX_0>" + p.getSex() + "</m37:SEX_0>");
		// 最高学历-09-本科-03-初中
		xml.append("<m37:HIGHEST_EDUC_LVL_0>" + p.getHighestEduc() + "</m37:HIGHEST_EDUC_LVL_0>");
		xml.append("</m37:PERS_DATA_EFFDT>");
		xml.append("<m37:PERS_NID>");
		xml.append("<m37:NATIONAL_ID_TYPE>CHN18</m37:NATIONAL_ID_TYPE>");
		xml.append("<m37:NATIONAL_ID>" + p.getNid() + "</m37:NATIONAL_ID>");
		xml.append("<m37:PRIMARY_NID>Y</m37:PRIMARY_NID>");
		xml.append("<m37:NID_SPECIAL_CHAR>" + p.getNid() + "</m37:NID_SPECIAL_CHAR>");
		xml.append("</m37:PERS_NID>");
		// 执行更新时有地址会报错，不知道什么-只能手动更新
		// xml.append("<m37:ADDRESS_TYPE_VW>");
		// xml.append("<m37:ADDRESSES>");
		// xml.append("<m37:EFFDT_3>" + sEffDate + "</m37:EFFDT_3>");
		// xml.append("<m37:COUNTRY_1>CHN</m37:COUNTRY_1>");
		// xml.append("<m37:ADDRESS1>" + p.getAddress2() + "</m37:ADDRESS1>");
		// xml.append("</m37:ADDRESSES>");
		// xml.append("</m37:ADDRESS_TYPE_VW>");
		xml.append("<m37:PERSONAL_PHONE>");
		xml.append("<m37:PHONE_TYPE>CELL</m37:PHONE_TYPE>");
		xml.append("<m37:PHONE>" + getNotEmptyStr(p.getPhone()) + "</m37:PHONE>");
		xml.append("<m37:PREF_PHONE_FLAG>Y</m37:PREF_PHONE_FLAG>");
		xml.append("</m37:PERSONAL_PHONE>");
		xml.append("<m37:PERSONAL_PHONE>");
		xml.append("<m37:PHONE_TYPE>HOME</m37:PHONE_TYPE>");
		xml.append("<m37:PHONE>" + getNotEmptyStr(p.getPhoneHome()) + "</m37:PHONE>");
		xml.append("<m37:PREF_PHONE_FLAG>N</m37:PREF_PHONE_FLAG>");
		xml.append("</m37:PERSONAL_PHONE>");
		xml.append("<m37:EMAIL_ADDRESSES>");
		xml.append("<m37:E_ADDR_TYPE>BUSN</m37:E_ADDR_TYPE>");
		xml.append("<m37:EMAIL_ADDR>" + getNotEmptyStr(p.getEmial()) + "</m37:EMAIL_ADDR>");
		xml.append("<m37:PREF_EMAIL_FLAG>Y</m37:PREF_EMAIL_FLAG>");
		xml.append("</m37:EMAIL_ADDRESSES>");
		xml.append("</m37:Updatedata__CompIntfc__C_CI_PERSON_DATA3>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	// 此方法无效-接口不通
	public static StringBuffer personalPsCiXml_Update2(RegPersonal p) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 更新时所有的EFFDT都是关键字-所以统一使用DocCreatetime
		String sEffDate = sf.format(p.getDocCreateTime());
		String sName = p.getName();
		String sLastName = sName.substring(0, 1);
		String sFirstName = sName.replace(sLastName, "");

		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m68=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M688390.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m68:Updatedata__CompIntfc__CI_PERSONAL_DATA>");
		xml.append("<m68:KEYPROP_EMPLID>" + p.getEmplid() + "</m68:KEYPROP_EMPLID>");
		xml.append("<m68:PROP_BIRTHDATE>" + sf.format(p.getBirthdate()) + "</m68:PROP_BIRTHDATE>");
		xml.append("<m68:PROP_BIRTHPLACE>" + p.getBirthPlace() + "</m68:PROP_BIRTHPLACE>");
		xml.append("<m68:PROP_BIRTHCOUNTRY>CHN</m68:PROP_BIRTHCOUNTRY>");
		xml.append("<m68:PROP_BIRTHSTATE>" + p.getBirthProvince() + "</m68:PROP_BIRTHSTATE>");
		xml.append("<m68:COLL_NAME_TYPE_VW>");
		xml.append("<m68:KEYPROP_NAME_TYPE>PRI</m68:KEYPROP_NAME_TYPE>");
		xml.append("<m68:COLL_NAMES>");
		xml.append("<m68:KEYPROP_NAME_TYPE>PRI</m68:KEYPROP_NAME_TYPE>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_COUNTRY_NM_FORMAT>CHN</m68:PROP_COUNTRY_NM_FORMAT>");
		xml.append("<m68:PROP_LAST_NAME>" + sLastName + "</m68:PROP_LAST_NAME>");
		xml.append("<m68:PROP_FIRST_NAME>" + sFirstName + "</m68:PROP_FIRST_NAME>");
		xml.append("<m68:PROP_LAST_NAME_PREF_NLD>1</m68:PROP_LAST_NAME_PREF_NLD>");
		xml.append("</m68:COLL_NAMES>");
		xml.append("</m68:COLL_NAME_TYPE_VW>");
		xml.append("<m68:COLL_PERS_DATA_EFFDT>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_MAR_STATUS>" + p.getMarry() + "</m68:PROP_MAR_STATUS>");
		xml.append("<m68:PROP_SEX>" + p.getSex() + "</m68:PROP_SEX>");
		// 最高统一招学历
		xml.append("<m68:PROP_HIGHEST_EDUC_LVL>01</m68:PROP_HIGHEST_EDUC_LVL>");
		xml.append("</m68:COLL_PERS_DATA_EFFDT>");
		// 身份证
		xml.append("<m68:COLL_PERS_NID>");
		xml.append("<m68:KEYPROP_COUNTRY>CHN</m68:KEYPROP_COUNTRY>");
		xml.append("<m68:KEYPROP_NATIONAL_ID_TYPE>CHN18</m68:KEYPROP_NATIONAL_ID_TYPE>");
		xml.append("<m68:NATIONAL_ID>" + p.getNid() + "</m68:NATIONAL_ID>");
		xml.append("<m68:PROP_PRIMARY_NID>Y</m68:PROP_PRIMARY_NID>");
		xml.append("<m68:PROP_TAX_REF_ID_SGP>N</m68:PROP_TAX_REF_ID_SGP>");
		xml.append("<m68:PROP_NATIONAL_ID>" + p.getNid() + "</m68:PROP_NATIONAL_ID>");
		xml.append("</m68:COLL_PERS_NID>");
		// 其他信息
		xml.append("<m68:PERS_DATA_OTH>");
		xml.append("<m68:C_LEFT_VISION>" + p.getVisionLeft() + "</m68:C_LEFT_VISION>");
		xml.append("<m68:C_RIGHT_VISION>" + p.getVisionRight() + "</m68:C_RIGHT_VISION>");
		xml.append("<m68:HEIGHT_IN_LINES>" + p.getHeight() + "</m68:HEIGHT_IN_LINES>");
		xml.append("<m68:DESCR100>" + p.getSpecialty() + "</m68:DESCR100>");
		xml.append("<m68:C_ONLY_CHILD>" + (p.getOnlyChild().equals("1") ? "Y" : "N") + "</m68:C_ONLY_CHILD>");
		xml.append("</m68:PERS_DATA_OTH>");
		// 家庭地址
		xml.append("<m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>HOME</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:COLL_ADDRESSES>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>HOME</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_EFF_STATUS>A</m68:PROP_EFF_STATUS>");
		xml.append("<m68:PROP_COUNTRY>CHN</m68:PROP_COUNTRY>");
		xml.append("<m68:PROP_ADDRESS1>" + p.getAddress2() + "</m68:PROP_ADDRESS1>");
		xml.append("</m68:COLL_ADDRESSES>");
		xml.append("</m68:COLL_ADDRESS_TYPE_VW>");
		// 身份证地址
		xml.append("<m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>10</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:COLL_ADDRESSES>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>10</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_EFF_STATUS>A</m68:PROP_EFF_STATUS>");
		xml.append("<m68:PROP_COUNTRY>CHN</m68:PROP_COUNTRY>");
		xml.append("<m68:PROP_ADDRESS1>" + p.getAddress() + "</m68:PROP_ADDRESS1>");
		xml.append("</m68:COLL_ADDRESSES>");
		xml.append("</m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>20</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:COLL_ADDRESSES>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>20</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_EFF_STATUS>A</m68:PROP_EFF_STATUS>");
		xml.append("<m68:PROP_COUNTRY>CHN</m68:PROP_COUNTRY>");
		xml.append("<m68:PROP_ADDRESS1>" + p.getAddress1() + "</m68:PROP_ADDRESS1>");
		xml.append("</m68:COLL_ADDRESSES>");
		xml.append("</m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>30</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:COLL_ADDRESSES>");
		xml.append("<m68:KEYPROP_ADDRESS_TYPE>30</m68:KEYPROP_ADDRESS_TYPE>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_EFF_STATUS>A</m68:PROP_EFF_STATUS>");
		xml.append("<m68:PROP_COUNTRY>CHN</m68:PROP_COUNTRY>");
		xml.append("<m68:PROP_ADDRESS1>" + p.getAddress3() + "</m68:PROP_ADDRESS1>");
		xml.append("</m68:COLL_ADDRESSES>");
		xml.append("</m68:COLL_ADDRESS_TYPE_VW>");
		xml.append("<m68:COLL_EMAIL_ADDRESSES>");
		xml.append("<m68:KEYPROP_E_ADDR_TYPE>BUSN</m68:KEYPROP_E_ADDR_TYPE>");
		xml.append("<m68:PROP_EMAIL_ADDR>" + getNotEmptyStr(p.getEmial()) + "</m68:PROP_EMAIL_ADDR>");
		xml.append("<m68:PROP_PREF_EMAIL_FLAG>Y</m68:PROP_PREF_EMAIL_FLAG>");
		xml.append("</m68:COLL_EMAIL_ADDRESSES>");
		xml.append("<m68:COLL_PERSON_IMCHAT>");
		xml.append("<m68:KEYPROP_MCF_IM_PROTOCOL>QQ</m68:KEYPROP_MCF_IM_PROTOCOL>");
		xml.append("<m68:KEYPROP_MCFIMDOMAIN>QQ</m68:KEYPROP_MCFIMDOMAIN>");
		xml.append("<m68:PROP_MCF_IMUSERID>" + getNotEmptyStr(p.getQq()) + "</m68:PROP_MCF_IMUSERID>");
		xml.append("<m68:PROP_PREF_CHATID_FLAG>Y</m68:PROP_PREF_CHATID_FLAG>");
		xml.append("</m68:COLL_PERSON_IMCHAT>");
		xml.append("<m68:COLL_PERSONAL_PHONE>");
		xml.append("<m68:KEYPROP_PHONE_TYPE>CELL</m68:KEYPROP_PHONE_TYPE>");
		xml.append("<m68:PROP_COUNTRY_CODE/>");
		xml.append("<m68:PROP_PHONE>" + getNotEmptyStr(p.getPhone()) + "</m68:PROP_PHONE>");
		xml.append("<m68:PROP_PREF_PHONE_FLAG>Y</m68:PROP_PREF_PHONE_FLAG>");
		xml.append("</m68:COLL_PERSONAL_PHONE>");
		xml.append("<m68:COLL_PERSONAL_PHONE>");
		xml.append("<m68:KEYPROP_PHONE_TYPE>HOME</m68:KEYPROP_PHONE_TYPE>");
		xml.append("<m68:PROP_PHONE>" + getNotEmptyStr(p.getPhoneHome()) + "</m68:PROP_PHONE>");
		xml.append("<m68:PROP_PREF_PHONE_FLAG>N</m68:PROP_PREF_PHONE_FLAG>");
		xml.append("</m68:COLL_PERSONAL_PHONE>");
		// 宗教
		xml.append("<m68:COLL_DIVERS_RELIGION>");
		xml.append("<m68:KEYPROP_REG_REGION>CHN</m68:KEYPROP_REG_REGION>");
		xml.append("<m68:PROP_RELIGION_CD>" + p.getReligion() + "</m68:PROP_RELIGION_CD>");
		xml.append("<m68:PROP_BLOOD_TYPE>U</m68:PROP_BLOOD_TYPE>");
		xml.append("</m68:COLL_DIVERS_RELIGION>");
		// 户口
		xml.append("<m68:COLL_PERS_HUKOU_CHN>");
		xml.append("<m68:KEYPROP_EFFDT>" + sEffDate + "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_HUKOU_TYPE_CHN>" + p.getHukouType() + "</m68:PROP_HUKOU_TYPE_CHN>");
		xml.append("<m68:PROP_CONTRIB_AREA_CHN>" + p.getHukouPlace() + "</m68:PROP_CONTRIB_AREA_CHN>");
		xml.append("</m68:COLL_PERS_HUKOU_CHN>");
		xml.append("<m68:COLL_PERS_WRKLIF_CHN>");
		xml.append("<m68:PROP_START_DT_CHN>" + sf.format(p.getFirstworkdate()) + "</m68:PROP_START_DT_CHN>");
		xml.append("</m68:COLL_PERS_WRKLIF_CHN>");
		// 政治面貌
		xml.append("<m68:COLL_PERS_POLITY_CHN>");
		xml.append("<m68:KEYPROP_EFFDT>" + (null == p.getInpartydate() ? sEffDate : sf.format(p.getInpartydate()))
				+ "</m68:KEYPROP_EFFDT>");
		xml.append("<m68:PROP_POLITICAL_STA_CHN>" + p.getPolitics() + "</m68:PROP_POLITICAL_STA_CHN>");
		xml.append("</m68:COLL_PERS_POLITY_CHN>");
		xml.append("<m68:COLL_PERS_NATIVE_CHN>");
		xml.append("<m68:PROP_NATIVE_PLACE_CHN>" + p.getNativePlace() + "</m68:PROP_NATIVE_PLACE_CHN>");
		xml.append("</m68:COLL_PERS_NATIVE_CHN>");
		xml.append("</m68:Updatedata__CompIntfc__CI_PERSONAL_DATA>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	// 工作经历-全删全加
	public static List<Integer> workQueryPsData(String empNo) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		List<Integer> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");
			String sql = " select SEQUENCE_NBR from SYSADM.PS_PRIORWORK_EXPER  where emplid ='" + empNo + "' ";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next()) {
				list.add(result.getInt("SEQUENCE_NBR"));
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
		return list;
	}

	// 工作经历-全删全加
	public static String workToPs(RegPersonal p, List<RegPersonalWork> lWork) {
		String result = "";
		// 全删
		List<String> lSql = new ArrayList<String>();
		lSql.add(" delete from SYSADM.PS_PRIORWORK_EXPER  where emplid ='" + p.getEmplid() + "' ");
		execPsSql(lSql);
		// List<Integer> lCheck = workCheck(p.getEmplid());
		Integer iSeq = 1;
		String sPsWsdl = getPsWsdl() + "CI_C_CI_PRIOR_WORK_EXPER.1.wsdl";
		String sAction = "";
		String sSchemas = "M667913.V1";
		// String sMethod="";
		StringBuffer xml;

		for (RegPersonalWork w : lWork) {
			// 全增
			sAction = "CI_C_CI_PRIOR_WORK_EXPER_UP.V1"; // 新增
			xml = workPsCiXml_Insert(p.getEmplid(), iSeq, w, sSchemas);

			ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);
			syncLog("insert", "reg_work", w.getFdId(), rm, JSONObject.toJSONString(w), xml.toString());

			iSeq = iSeq + 1;
		}

		result = "同步成功";
		logger.info("同步PS-工作经历:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);
		return result;
	}

	public static StringBuffer workPsCiXml_Update(String empNo, Integer iSeq, RegPersonalWork w) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m66=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/m663526.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m66:Updatedata__CompIntfc__C_CI_PRIOR_WORK_EXPER>");
		xml.append("<m66:EMPLID>" + empNo + "</m66:EMPLID>");
		xml.append("<m66:PRIORWORK_EXPER>");
		xml.append("<m66:START_DT>" + sf.format(w.getStartdate()) + "</m66:START_DT>");
		xml.append("<m66:SEQUENCE_NBR>" + String.valueOf(iSeq) + "</m66:SEQUENCE_NBR>");
		xml.append("<m66:EMPLOYER>" + w.getCompany() + "</m66:EMPLOYER>");
		xml.append("<m66:CITY>" + w.getCity() + "</m66:CITY>");
		xml.append("<m66:PHONE>" + w.getPhone() + "</m66:PHONE>");
		xml.append("<m66:END_DT>" + sf.format(w.getEnddate()) + "</m66:END_DT>");
		xml.append("<m66:ENDING_TITLE>" + w.getPosition() + "</m66:ENDING_TITLE>");
		if (null != w.getSalary())
			xml.append("<m66:ENDING_RATE>" + String.valueOf(w.getSalary()) + "</m66:ENDING_RATE>");
		xml.append("<m66:NAME>" + w.getProveName() + "</m66:NAME>");
		xml.append("<m66:PHONE1>" + w.getProvePhone() + "</m66:PHONE1>");
		xml.append("<m66:DEPTNAME>" + w.getProveDept() + "</m66:DEPTNAME>");
		xml.append("<m66:JOBCODE_DESCR>" + w.getProvePosi() + "</m66:JOBCODE_DESCR>");

		xml.append("<m66:COUNTRY>CHN</m66:COUNTRY>");
		xml.append("<m66:C_EXPR_TYPE>0</m66:C_EXPR_TYPE>"); // 外部工作
		xml.append("</m66:PRIORWORK_EXPER>");
		xml.append("</m66:Updatedata__CompIntfc__C_CI_PRIOR_WORK_EXPER>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	public static StringBuffer workPsCiXml_Insert(String empNo, Integer iSeq, RegPersonalWork w, String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m66=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m66:Update__CompIntfc__C_CI_PRIOR_WORK_EXPER>");
		xml.append("<m66:EMPLID>" + empNo + "</m66:EMPLID>");
		xml.append("<m66:PRIORWORK_EXPER>");
		xml.append("<m66:START_DT>" + sf.format(w.getStartdate()) + "</m66:START_DT>");
		xml.append("<m66:SEQUENCE_NBR>" + String.valueOf(iSeq) + "</m66:SEQUENCE_NBR>");
		xml.append("<m66:EMPLOYER>" + w.getCompany() + "</m66:EMPLOYER>");
		xml.append("<m66:CITY>" + w.getCity() + "</m66:CITY>");
		xml.append("<m66:PHONE>" + w.getPhone() + "</m66:PHONE>");
		xml.append("<m66:END_DT>" + sf.format(w.getEnddate()) + "</m66:END_DT>");
		xml.append("<m66:ENDING_TITLE>" + w.getPosition() + "</m66:ENDING_TITLE>");
		if (null != w.getSalary())
			xml.append("<m66:ENDING_RATE>" + String.valueOf(w.getSalary()) + "</m66:ENDING_RATE>");
		xml.append("<m66:NAME>" + w.getProveName() + "</m66:NAME>");
		xml.append("<m66:PHONE1>" + w.getProvePhone() + "</m66:PHONE1>");
		xml.append("<m66:DEPTNAME>" + w.getProveDept() + "</m66:DEPTNAME>");
		xml.append("<m66:JOBCODE_DESCR>" + w.getProvePosi() + "</m66:JOBCODE_DESCR>");

		xml.append("<m66:COUNTRY>CHN</m66:COUNTRY>");
		xml.append("<m66:C_EXPR_TYPE>0</m66:C_EXPR_TYPE>"); // 外部工作
		xml.append("</m66:PRIORWORK_EXPER>");
		xml.append("</m66:Update__CompIntfc__C_CI_PRIOR_WORK_EXPER>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	// 家庭成员-全删全加
	public static String familyToPs(RegPersonal p, List<RegPersonalFamily> lFamily) {
		String result = "";
		// 全删
		List<String> lSql = new ArrayList<String>();
		lSql.add("  delete from SYSADM.PS_PRIORWORK_EXPER  where emplid ='" + p.getEmplid() + "'  ");
		lSql.add("  delete from SYSADM.PS_DEP_BEN where emplid='" + p.getEmplid() + "'  ");
		lSql.add("  delete from SYSADM.PS_DEP_BEN_NAME where emplid='" + p.getEmplid() + "'  ");
		lSql.add("  delete from SYSADM.PS_DEP_BEN_ADDR where emplid='" + p.getEmplid() + "'  ");
		lSql.add("  delete from SYSADM.PS_DEP_BEN_EFF where emplid='" + p.getEmplid() + "'  ");
		execPsSql(lSql);

		Integer iSeq = 1;
		String sSeq = "";
		String sPsWsdl = getPsWsdl() + "CI_C_CI_DEPEND_BENEF.1.wsdl";
		String sSchemas = "M726835.V1";
		String sAction = "";
		// String sMethod="";
		StringBuffer xml;

		for (RegPersonalFamily w : lFamily) {
			// 全增
			sAction = "CI_C_CI_DEPEND_BENEF_UP.V1";
			sSeq = "0" + String.valueOf(iSeq);
			xml = familyPsCiXml_Insert(p.getEmplid(), sSeq, w, sSchemas);

			ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);

			syncLog("insert", "reg_family", w.getFdId(), rm, JSONObject.toJSONString(w), xml.toString());

			if (rm.getStatus() == 200) {
				lSql = new ArrayList<String>();
				lSql.add(" update PS_DEP_BEN_ADDR set ADDRESS1='" + w.getAddress() + "' where emplid='" + p.getEmplid()
						+ "' and  DEPENDENT_BENEF ='" + sSeq + "' ");
				execPsSql(lSql);
			}
			iSeq = iSeq + 1;
		}

		result = "同步成功";
		logger.info("同步PS-工作经历:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);
		return result;
	}

	public static StringBuffer familyPsCiXml_Insert(String empNo, String sSeq, RegPersonalFamily w, String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date doDate = new Date();
		String sEffDate = sf.format(doDate);
		String sName = w.getName();
		String sLastName = sName.substring(0, 1);
		String sFirstName = sName.replace(sLastName, "");

		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m72=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m72:Update__CompIntfc__C_CI_DEPEND_BENEF>");
		xml.append("<m72:EMPLID>" + empNo + "</m72:EMPLID>");
		xml.append("<m72:DEP_BEN>");
		xml.append("<m72:DEPENDENT_BENEF>" + sSeq + "</m72:DEPENDENT_BENEF>");
		xml.append("<m72:BIRTHDATE>" + sEffDate + "</m72:BIRTHDATE>");
		xml.append("<m72:PHONE>" + w.getPhone() + "</m72:PHONE>");
		xml.append("<m72:C_MOBIL_PHONE>" + w.getPhone() + "</m72:C_MOBIL_PHONE>");
		xml.append("<m72:DEP_BEN_NAME>");
		xml.append("<m72:EFFDT>" + sEffDate + "</m72:EFFDT>");

		xml.append("<m72:LAST_NAME_SRCH>" + sLastName + "</m72:LAST_NAME_SRCH>");
		xml.append("<m72:FIRST_NAME_SRCH>" + sFirstName + "</m72:FIRST_NAME_SRCH>");
		xml.append("<m72:LAST_NAME>" + sLastName + "</m72:LAST_NAME>");
		xml.append("<m72:FIRST_NAME>" + sFirstName + "</m72:FIRST_NAME>");
		xml.append("<m72:NAME_DISPLAY>" + sName + "</m72:NAME_DISPLAY>");
		xml.append("</m72:DEP_BEN_NAME>");

		xml.append("<m72:DEP_BEN_ADDR>");
		xml.append("<m72:EFFDT_1>" + sEffDate + "</m72:EFFDT_1>");
		xml.append("<m72:SAME_ADDRESS_EMPL>Y</m72:SAME_ADDRESS_EMPL>"); // 与员工地址相同
		xml.append("<m72:C_EMGR_CNTRT>N</m72:C_EMGR_CNTRT>");
		xml.append("<m72:COUNTRY>CHN</m72:COUNTRY>");
		xml.append("<m72:ADDRESS_TYPE>HOME</m72:ADDRESS_TYPE>");

		xml.append("<m72:ADDRESS1>" + w.getAddress() + "</m72:ADDRESS1>");
		xml.append("<m72:ADDRESS2>" + w.getAddress() + "</m72:ADDRESS2>");
		xml.append("<m72:EFFDT_0>" + sEffDate + "</m72:EFFDT_0>");
		xml.append("</m72:DEP_BEN_ADDR>");
		xml.append("<m72:DEP_BEN_EFF>");
		xml.append("<m72:EFFDT_3>" + sEffDate + "</m72:EFFDT_3>");
		xml.append("<m72:RELATIONSHIP>" + w.getRelation() + "</m72:RELATIONSHIP>");
		// xml.append("<m72:DEP_BENEF_TYPE>B</m72:DEP_BENEF_TYPE>");
		xml.append("<m72:SEX>M</m72:SEX>");
		xml.append("<m72:OCCUPATION>" + w.getPosition() + "</m72:OCCUPATION>");
		xml.append("<m72:STUDENT>N</m72:STUDENT>");
		xml.append("<m72:DISABLED>" + (w.getIsHJ().equals("1") ? "Y" : "N") + "</m72:DISABLED>");
		xml.append("<m72:C_COMPANY>" + w.getCompany() + "</m72:C_COMPANY>");
		xml.append("</m72:DEP_BEN_EFF>");
		xml.append("</m72:DEP_BEN>");
		xml.append("</m72:Update__CompIntfc__C_CI_DEPEND_BENEF>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	public static void syncLog(String type, String formType, String dataId, ResultMessage rm, String json,
			String sentXml) {
		RegSyncPs log = new RegSyncPs();
		log.setType(type);
		log.setFormType(formType);
		log.setDataId(dataId);
		log.setJson(json);
		log.setSentXml(sentXml);
		log.setResultXml(rm.getResultContent());
		log.setResultStatus(String.valueOf(rm.getStatus()));
		log.setDocCreateTime(new Date());
		log.setDocCreatorId(UserUtils.getUserId());
		if (regSyncPsService == null) {
			regSyncPsService = SpringBeanUtil.getBean("regSyncPsService");
		}
		regSyncPsService.insert(log);
	}

	// 紧急联系人-全删全加
	public static String exigenceToPs(RegPersonal p, List<RegPersonalExigence> lExigence) {
		String result = "";
		// 全删
		List<String> lSql = new ArrayList<String>();
		lSql.add("  delete from SYSADM.PS_EMERGENCY_CNTCT  where emplid ='" + p.getEmplid() + "'  ");
		execPsSql(lSql);

		// 【主要联系人】有且只有一个-并且必须是第一个
		Integer iSeq = 1;
		String sPsWsdl = getPsWsdl() + "CI_C_CI_EMERGENCY_CONTACT.1.wsdl";
		String sSchemas = "M130461.V1";
		String sAction = "";
		// String sMethod="";
		StringBuffer xml;

		for (RegPersonalExigence w : lExigence) {
			// 全增
			sAction = "CI_C_CI_EMERGENCY_CONTACT_UP.V1";
			xml = exigencePsCiXml_Insert(p.getEmplid(), iSeq, w, sSchemas);

			ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);

			syncLog("insert", "reg_exigence", w.getFdId(), rm, JSONObject.toJSONString(w), xml.toString());

			iSeq = iSeq + 1;
		}

		result = "同步成功";
		logger.info("同步PS-紧急联系人:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);
		return result;
	}

	public static StringBuffer exigencePsCiXml_Insert(String empNo, Integer iSeq, RegPersonalExigence w,
			String sSchemas) {

		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m13=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m13:Update__CompIntfc__C_CI_EMERGENCY_CONTACT2>");
		xml.append("<m13:EMPLID>" + empNo + "</m13:EMPLID>");
		xml.append("<m13:EMERGENCY_CNTCT>");
		xml.append("<m13:CONTACT_NAME>" + w.getName() + "</m13:CONTACT_NAME>");
		xml.append("<m13:SAME_ADDRESS_EMPL>N</m13:SAME_ADDRESS_EMPL>");
		xml.append("<m13:PRIMARY_CONTACT>" + (iSeq == 1 ? "Y" : "N") + "</m13:PRIMARY_CONTACT>");
		xml.append("<m13:ADDRESS1_0>" + w.getAddress() + "</m13:ADDRESS1_0>");
		xml.append("<m13:RELATIONSHIP>" + w.getRelation() + "</m13:RELATIONSHIP>");
		xml.append("<m13:C_EXPR_PHONE>" + w.getPhone2() + "</m13:C_EXPR_PHONE>");
		xml.append("<m13:C_MOVE_PHONE>" + w.getPhone() + "</m13:C_MOVE_PHONE>");
		xml.append("</m13:EMERGENCY_CNTCT>");
		xml.append("</m13:Update__CompIntfc__C_CI_EMERGENCY_CONTACT2>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	// 求职途径-只传第一条
	public static String employWayToPs(RegPersonal p, RegPersonalEmployWay employWay) {
		List<String> lSql = new ArrayList<String>();
		// 求职途径
		lSql.add("DELETE FROM SYSADM.PS_C_EMP_APPLYJOB WHERE EMPLID='" + p.getEmplid() + "'");
		lSql.add(
				"insert into SYSADM.PS_C_EMP_APPLYJOB(EMPLID,C_EMPLOY_PLACE, C_RECOMD_PERSON, C_EMPLOY_PERSON, C_APPLYJOB_BY, C_APPLYJOB_WEB, C_TRAIN_TYPE,C_CAMPUSRECRUIT_YN,C_CAMPUSRECRUIT_L) "
						+ " values ('" + p.getEmplid() + "', '1', '"
						+ (null == employWay.getRemark() || employWay.getRemark().isEmpty() ? ""
								: employWay.getRemark())
						+ " ', ' ', '" + employWay.getCode() + "', ' ', ' ',' ',' ')");
		// 员工编号,招聘单位所在地,推荐人,面试官,求职途径,求职网站,培养类别);
		execPsSql(lSql);
		return "同步成功";
	}

	// 最高学历
	public static String educationToPs(RegPersonal p, List<RegPersonalEducation> lEducation) {
		String result = "";
		// 全删
		List<String> lSql = new ArrayList<String>();
		lSql.add(
				" delete from SYSADM.PS_JPM_JP_ITEMS where JPM_PROFILE_ID in  (  select JPM_PROFILE_ID from PS_JPM_PROFILE where emplid='"
						+ p.getEmplid() + "' ) ");
		lSql.add(" delete from SYSADM.PS_JPM_PROFILE where emplid='" + p.getEmplid() + "' ");
		execPsSql(lSql);

		Integer iSeq = 1;
		String sPsWsdl = getPsWsdl() + "CI_CI_JPM_PROFILE_ADD.1.wsdl";
		String sSchemas = "M998933.V1";
		String sAction = "";
		// String sMethod="";
		StringBuffer xml;

		for (RegPersonalEducation w : lEducation) {
			// 全增
			sAction = "CI_CI_JPM_PROFILE_ADD_C.V1";
			xml = educationPsCiXml_Insert(p, iSeq, w, sSchemas);
			ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);
			syncLog("insert", "reg_education", w.getFdId(), rm, JSONObject.toJSONString(w), xml.toString());

			iSeq = iSeq + 1;
		}

		if (null != lEducation && lEducation.size() > 1) {
			educationDoAfterPsInsert(p.getEmplid());
		}

		result = "同步成功";
		logger.info("同步PS-最高学历:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);
		return result;
	}

	public static StringBuffer educationPsCiXml_Insert(RegPersonal p, Integer iSeq, RegPersonalEducation w,
			String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m99=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m99:Create__CompIntfc__CI_JPM_PROFILE_ADD> ");
		xml.append("<!--<m99:JPM_PROFILE_ID>?</m99:JPM_PROFILE_ID>--> ");
		xml.append("<m99:DESCR>" + p.getName() + "</m99:DESCR> ");
		xml.append("<m99:EMPLID>" + p.getEmplid() + "</m99:EMPLID> ");
		xml.append("<m99:JPM_JP_TYPE>PERSON</m99:JPM_JP_TYPE> ");
		xml.append("<m99:JPM_JP_ITEMS> ");
		xml.append("<m99:JPM_CAT_TYPE>EDLVLACHV</m99:JPM_CAT_TYPE> ");
		// <!--学历-->
		xml.append("<m99:JPM_CAT_ITEM_ID>" + w.getEducation() + "</m99:JPM_CAT_ITEM_ID> ");
		// <!--在校任职-->
		xml.append("<!--<m99:JPM_CAT_ITEM_QUAL></m99:JPM_CAT_ITEM_QUAL>--> ");
		xml.append("<m99:EFFDT>" + sf.format(w.getEnddate()) + "</m99:EFFDT> ");
		xml.append("<m99:EFF_STATUS>A</m99:EFF_STATUS> ");
		xml.append("<m99:COUNTRY>CHN</m99:COUNTRY> ");
		// <!--是否最高统招Y/N--> ");
		xml.append("<m99:JPM_YN_1>" + (w.getTypename().equals("统招学历") ? "Y" : "N") + "</m99:JPM_YN_1> ");
		// <!--是否最高非统招Y/N--> ");
		xml.append("<m99:JPM_YN_2>N</m99:JPM_YN_2> ");
		xml.append("<m99:RATING_MODEL>EDU</m99:RATING_MODEL> ");
		// <!--学位-->
		xml.append("<m99:JPM_RATING1>" + w.getDegree() + "</m99:JPM_RATING1> ");
		// <!--学制-->
		xml.append("<m99:JPM_DECIMAL_2>" + w.getRegime() + "</m99:JPM_DECIMAL_2> ");
		xml.append("<!--<m99:SCHOOL_CODE>?</m99:SCHOOL_CODE>--> ");
		xml.append("<m99:SCHOOL_DESCR>" + w.getSchool() + "</m99:SCHOOL_DESCR> ");
		xml.append("<m99:MAJOR_DESCR>" + w.getMajor() + "</m99:MAJOR_DESCR> ");
		xml.append("</m99:JPM_JP_ITEMS> ");
		xml.append("</m99:Create__CompIntfc__CI_JPM_PROFILE_ADD> ");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	public static void educationDoAfterPsInsert(String empNo) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库...");
			String url = getjDBCConfig().getPSUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			String user = getjDBCConfig().getPSUsername();// 用户名,系统默认的账户名
			String password = getjDBCConfig().getPSPassword();// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			logger.info("连接成功...");

			String sql = " select min(JPM_PROFILE_ID) mid from  SYSADM.PS_JPM_PROFILE where emplid='" + empNo + "' ";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			ResultSet result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			if (result.next()) {
				String sMinProId = result.getString("mid");
				if (null != sMinProId && !sMinProId.isEmpty()) {
					try {
						sql = " update SYSADM.PS_JPM_JP_ITEMS set JPM_PROFILE_ID = '" + sMinProId
								+ "' where JPM_PROFILE_ID in ( select JPM_PROFILE_ID from  SYSADM.PS_JPM_PROFILE where emplid='"
								+ empNo + "' and JPM_PROFILE_ID !='" + sMinProId + "'  ) ";
						if (pre != null)
							pre.close();
						pre = con.prepareStatement(sql);
						pre.executeUpdate();
					} catch (Exception e2) {
						logger.info("执行PS语句出错：" + sql);
						logger.info(e2.getMessage());
					}

					try {
						sql = " delete from  SYSADM.PS_JPM_PROFILE where emplid='" + empNo + "' and JPM_PROFILE_ID !='"
								+ sMinProId + "'   ";
						if (pre != null)
							pre.close();
						pre = con.prepareStatement(sql);
						pre.executeUpdate();
					} catch (Exception e3) {
						logger.info("执行PS语句出错：" + sql);
						logger.info(e3.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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

	// 人员职位
	public static String regJobToPs(RegPersonal p, RegJob j) {
		String result = "";

		String sPsWsdl = getPsWsdl() + "CI_C_CI_JOB_DATA_EMP2.1.wsdl";
		String sSchemas = "M139585.V1";
		String sAction = "";
		// String sMethod="";
		StringBuffer xml;

		sAction = "CI_C_CI_JOB_DATA_EMP2_C.V1";
		xml = regJobPsCiXml_Insert(p, j, sSchemas);
		ResultMessage rm = HttpClientUtils.PostWebService(sPsWsdl, xml.toString(), "soapAction", sAction);
		syncLog("insert", "reg_job", j.getFdId(), rm, JSONObject.toJSONString(j), xml.toString());

		if (rm.getStatus() == 200)
			result = "同步成功";
		else
			result = "同步失败";

		logger.info("同步PS-人员职位:" + p.getEmplid() + ";" + p.getFdId() + ";" + result);
		return result;
	}

	// 新增和修改同一个接口
	public static StringBuffer regJobPsCiXml_Insert(RegPersonal p, RegJob j, String sSchemas) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 更新时所有的EFFDT都是关键字-所以统一使用DocCreatetime
		String sEffDate = sf.format(p.getDocCreateTime());
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m13=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/"
						+ sSchemas + "\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m13:Create__CompIntfc__C_CI_JOB_DATA_EMP2> ");
		xml.append("<m13:EMPLID>" + p.getEmplid() + "</m13:EMPLID> ");
		xml.append("<m13:EMPL_RCD>0</m13:EMPL_RCD> ");
		xml.append("<m13:JOB> ");
		xml.append("<m13:EFFDT_0>" + sEffDate + "</m13:EFFDT_0> ");
		xml.append("<m13:EFFSEQ>0</m13:EFFSEQ> ");
		xml.append("<m13:POSITION_NBR>" + j.getPositionNbr() + "</m13:POSITION_NBR> ");
		xml.append("<m13:ACTION>HIR</m13:ACTION> ");
		// 入职原因(101社会招聘;100:校园招聘)
		xml.append("<m13:ACTION_REASON>" + j.getReason() + "</m13:ACTION_REASON> ");
		// 班次
		// xml.append("<m13:SHIFT>?</m13:SHIFT> ");
		// 汇报关系-->
		xml.append("<m13:REPORTS_TO>" + j.getUpperNbr() + "</m13:REPORTS_TO> ");
		// 职务等级-->
		xml.append("<m13:SUPV_LVL_ID>" + j.getJobLevel() + "</m13:SUPV_LVL_ID> ");
		// 试用期限-->
		xml.append("<m13:C_PRORES_PRD>" + j.getTryrange() + "</m13:C_PRORES_PRD> ");
		// 试用期截止日期
		// xml.append("<m13:C_PRO_RES_EDDT>?</m13:C_PRO_RES_EDDT> ");
		// 行政技术职务
		xml.append("<m13:C_TECH_JOBCODE>" + j.getTechJobcode() + "</m13:C_TECH_JOBCODE> ");
		// 员工类型
		xml.append("<m13:EMPL_CLASS>" + j.getEmptype() + "</m13:EMPL_CLASS> ");
		xml.append("</m13:JOB> ");
		xml.append("</m13:Create__CompIntfc__C_CI_JOB_DATA_EMP2> ");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");

		return xml;
	}

	public static String getNotEmptyStr(String s) {
		return null != s && !s.isEmpty() ? s : "-";
	}

}
