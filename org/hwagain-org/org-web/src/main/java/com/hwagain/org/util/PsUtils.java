package com.hwagain.org.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.job.entity.OrgJob;

public class PsUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(PsUtils.class);

	private final static String PS_WSDL_DEPT = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/CI_C_CI_DEPT_TBL.2.wsdl";
	
	private final static String PS_WSDL_JOBCODE = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/CI_JOBCODE.1.wsdl";
	
	private final static String PS_WSDL_POSITION = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/CI_C_CI_POSITION_DATA.1.wsdl";
	
	/**
	 * 插入部门数据到PS系统
	 * 
	 * @param dept
	 * @return
	 */
	public static ResultMessage addPsDept(OrgDeptPro dept){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        		+ "xmlns:m34=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M345466.V1\">");
        xml.append("<soapenv:Header/>");
        xml.append("<soapenv:Body>");
        xml.append("<m34:Create__CompIntfc__C_CI_DEPT_TBL>");
        xml.append("<m34:SETID>SHARE</m34:SETID>");
        xml.append("<m34:DEPTID>"+dept.getDeptid()+"</m34:DEPTID>");
        xml.append("<m34:DEPT_TBL>");
        xml.append("<m34:EFFDT>"+sf.format(dept.getEffdt())+"</m34:EFFDT>");
        xml.append("<m34:EFF_STATUS>A</m34:EFF_STATUS>");
        xml.append("<m34:DESCR>"+dept.getDescr()+"</m34:DESCR>");
        xml.append("<m34:DESCRSHORT>"+dept.getDescrshort()+"</m34:DESCRSHORT>");
        xml.append("<m34:COMPANY>"+dept.getCompany()+"</m34:COMPANY>");
        xml.append("<m34:SETID_LOCATION>SHARE</m34:SETID_LOCATION>");
        xml.append("<m34:LOCATION>"+resultAddress(dept.getCompany())+"</m34:LOCATION>");
        xml.append("<m34:C_DEPT_ORDER>1</m34:C_DEPT_ORDER>");
        xml.append("<m34:C_DEPT_LEVEL>25</m34:C_DEPT_LEVEL>");
        xml.append("<m34:MANAGER_TYPE>0</m34:MANAGER_TYPE>");
        xml.append("</m34:DEPT_TBL>");
        xml.append("</m34:Create__CompIntfc__C_CI_DEPT_TBL>");
        xml.append("</soapenv:Body>");
        xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_DEPT, xml.toString(), "soapAction", "CI_C_CI_DEPT_TBL_C.V1");
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage updatePsDept(OrgDeptPro dept){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m84=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M841207.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m84:Update__CompIntfc__C_CI_DEPT_TBL>");
		xml.append("<m84:SETID>SHARE</m84:SETID>");
		xml.append("<m84:DEPTID>"+dept.getDeptid()+"</m84:DEPTID>");
		xml.append("<m84:SET_DEPT_BU_VW/>");
		xml.append("<m84:DEPT_TBL>");
		xml.append("<m84:EFFDT>"+sf.format(dept.getEffdt())+"</m84:EFFDT>");
		xml.append("<m84:EFF_STATUS>"+(dept.getIsDelete()==1?"I":"A")+"</m84:EFF_STATUS>");
		xml.append("<m84:DESCR>"+dept.getDescr()+"</m84:DESCR>");
		xml.append("<m84:DESCRSHORT>"+dept.getDescrshort()+"</m84:DESCRSHORT>");
		xml.append("<m84:COMPANY>"+dept.getCompany()+"</m84:COMPANY>");
        xml.append("<m84:SETID_LOCATION>SHARE</m84:SETID_LOCATION>");
        xml.append("<m84:LOCATION>"+resultAddress(dept.getCompany())+"</m84:LOCATION>");
		xml.append("</m84:DEPT_TBL>");
		xml.append("</m84:Update__CompIntfc__C_CI_DEPT_TBL>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_DEPT, xml.toString(), "soapAction", "CI_C_CI_DEPT_TBL_UP.V1");
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage getPsDept(String deptid){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m10=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M1013947.V1\">");
        xml.append("<soapenv:Header/>");
        xml.append("<soapenv:Body>");
        xml.append("<m10:Get__CompIntfc__C_CI_DEPT_TBL>");
        xml.append("<m10:SETID>SHARE</m10:SETID>");
        xml.append("<m10:DEPTID>"+deptid+"</m10:DEPTID>");
        xml.append("</m10:Get__CompIntfc__C_CI_DEPT_TBL>");
        xml.append("</soapenv:Body>");
        xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_DEPT, xml.toString(), "soapAction", "CI_C_CI_DEPT_TBL_G.V1");
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage addPSJobCode(String jobcode,Date effdt,String descr,String descrshort){
		StringBuffer xml = new StringBuffer("");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m44=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M440539.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m44:Create__CompIntfc__JOBCODE>");
		xml.append("<m44:SETID>SHARE</m44:SETID>");
		xml.append("<m44:JOBCODE>"+jobcode+"</m44:JOBCODE>");
		xml.append("<m44:JOBCODE_TBL>");
		xml.append("<m44:EFFDT>"+sf.format(effdt)+"</m44:EFFDT>");
		xml.append("<m44:EFF_STATUS>A</m44:EFF_STATUS>");
		xml.append("<m44:DESCR>"+descr+"</m44:DESCR>");
		xml.append("<m44:DESCRSHORT>"+descrshort+"</m44:DESCRSHORT>");
		xml.append("</m44:JOBCODE_TBL>");
		xml.append("</m44:Create__CompIntfc__JOBCODE>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_JOBCODE, xml.toString(), "soapAction", "CI_JOBCODE_C.V1");
        logger.info("插入PS系统职务"+xml);
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage addPSPosition(OrgJob job,Date eddft,String jobcode){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		Map<String,Object> map = JDBCUtils.getPSPOSTIONINPUT(job.getJobCode());
		if(map!=null&&map.size()>0){
			System.err.println(111);
			try {
				JDBCUtils.updatePSPOSTIONINPUT(job, eddft,(Date)map.get("EFFDT"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.err.println(222);
			JDBCUtils.addPSPOSTIONINPUT(job, eddft,jobcode);
		}
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m57=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M572928.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m57:Create__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("<m57:POSITION_NBR>"+job.getJobCode()+"</m57:POSITION_NBR>");
		xml.append("<m57:POSITION_DATA>");
		xml.append("<m57:EFFDT_0>"+sf.format(eddft)+"</m57:EFFDT_0>");
		xml.append("<m57:EFF_STATUS>A</m57:EFF_STATUS>");
		xml.append("<m57:DESCR>"+job.getJobName()+"</m57:DESCR>");
		xml.append("<m57:DESCRSHORT>"+job.getJobName()+"</m57:DESCRSHORT>");
		xml.append("<m57:ACTION_REASON>NEW</m57:ACTION_REASON>");
		xml.append("<m57:BUSINESS_UNIT>"+JDBCUtils.getBUSINESS_UNIT(job.getCompany())+"</m57:BUSINESS_UNIT>");
		xml.append("<m57:DEPTID>"+job.getDeptCode()+"</m57:DEPTID>");
		xml.append("<m57:JOBCODE>"+jobcode+"</m57:JOBCODE>");
		xml.append("<m57:MAX_HEAD_COUNT_0>5</m57:MAX_HEAD_COUNT_0>");
		xml.append("<m57:LOCATION>"+resultAddress(job.getCompany())+"</m57:LOCATION>");
		xml.append("<m57:COMPANY>"+job.getCompany()+"</m57:COMPANY>");
		xml.append("<m57:REG_TEMP>1</m57:REG_TEMP>");
		xml.append("<m57:FULL_PART_TIME>F</m57:FULL_PART_TIME>");
		xml.append("</m57:POSITION_DATA>");
		xml.append("</m57:Create__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_POSITION, xml.toString(), "soapAction", "CI_C_CI_POSITION_DATA_C.V1");
        logger.info("插入PS系统职位"+xml);
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage updatePSPosition(OrgJob job,Date eddft){
		JDBCUtils.updatePSPOSTIONINPUT(job, eddft,null);
		StringBuffer xml = new StringBuffer("");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m87=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M878922.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m87:Updatedata__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("<m87:POSITION_NBR>"+job.getJobCode()+"</m87:POSITION_NBR>");
		xml.append("<m87:POSITION_DATA>");
		xml.append("<m87:EFFDT_0>"+sf.format(eddft.getTime())+"</m87:EFFDT_0>");
		xml.append("<m87:EFF_STATUS>"+(job.getIsDelete()==1?"I":"A")+"</m87:EFF_STATUS>");
		xml.append("<m87:DESCR>"+job.getJobName()+"</m87:DESCR>");
		xml.append("<m87:DESCRSHORT>"+job.getJobName()+"</m87:DESCRSHORT>");
		xml.append("</m87:POSITION_DATA>");
		xml.append("</m87:Updatedata__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_POSITION, xml.toString(), "soapAction", "CI_C_CI_POSITION_DATA_UD.V1");
        logger.info("修改PS系统职位"+xml);
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static ResultMessage getPSPosition(String code){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:m39=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M398977.V1\">");
		xml.append("<soapenv:Header/>");
		xml.append("<soapenv:Body>");
		xml.append("<m39:Get__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("<m39:POSITION_NBR>"+code+"</m39:POSITION_NBR>");
		xml.append("</m39:Get__CompIntfc__C_CI_POSITION_DATA>");
		xml.append("</soapenv:Body>");
		xml.append("</soapenv:Envelope>");
        ResultMessage result = HttpClientUtils.PostWebService(PS_WSDL_POSITION, xml.toString(), "soapAction", "CI_C_CI_POSITION_DATA_G.V1");
        logger.info("根据职位编号查询PS系统职位"+xml);
        result.setRequestContent(xml.toString());
		return result;
	}
	
	public static void main(String[] args) {
		OrgDeptPro org = new OrgDeptPro();
		org.setDeptid("L90008");
		org.setDescr("集团总部-信息部-测试部数据08");
		org.setDescrshort("测试部数据08");
		org.setEffdt(new Date());
		org.setIsDelete(0);
		org.setCompany("001");
		ResultMessage result = updatePsDept(org);
		System.err.println(result.getStatus());
		System.err.println(result.getResultContent());
	}
	
	private static String resultAddress(String company){
		switch (company) {
		case "001":
			return CompanyAddrConfig.COMPANY_JTZNB;
		case "002":
			return CompanyAddrConfig.COMPANY_JTZNB;
		case "003":
			return CompanyAddrConfig.COMPANY_GXZL;
		case "004":
			return CompanyAddrConfig.COMPANY_GZZY;
		case "005":
			return CompanyAddrConfig.COMPANY_GZZL;
		case "006":
			return CompanyAddrConfig.COMPANY_NNZY;
		case "007":
			return CompanyAddrConfig.COMPANY_JTZNB;
		case "008":
			return CompanyAddrConfig.COMPANY_GZZY;
		case "009":
			return CompanyAddrConfig.COMPANY_GZZY;
		case "010":
			return CompanyAddrConfig.COMPANY_GZZP;
		case "011":
			return CompanyAddrConfig.COMPANY_HJRZP;
		default:
			return CompanyAddrConfig.COMPANY_JTZNB;
		}
	}
}
