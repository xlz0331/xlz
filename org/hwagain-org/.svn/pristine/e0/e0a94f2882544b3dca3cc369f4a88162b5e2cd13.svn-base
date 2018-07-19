package com.hwagain.org.util;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

public class HttpClientUtils {
	
	public static ResultMessage PostWebService(String wsdl,String xml,String soapActionName,String soapActionValue){
        try {
        	 int timeout = 10000;
             // HttpClient发送SOAP请求
             System.out.println("HttpClient 发送SOAP请求");
             HttpClient client = new HttpClient();
             PostMethod postMethod = new PostMethod(wsdl);
             // 设置连接超时
             client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
             // 设置读取时间超时
             client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
             // 然后把Soap请求数据添加到PostMethod中
             RequestEntity requestEntity = new StringRequestEntity(xml, "text/xml", "UTF-8");
             //设置请求头部，否则可能会报 “no SOAPAction header” 的错误
             postMethod.setRequestHeader(soapActionName,soapActionValue);
             // 设置请求体
             postMethod.setRequestEntity(requestEntity);
             int status = client.executeMethod(postMethod);
             // 打印请求状态码
             System.out.println("status:" + status);
             // 获取响应体输入流
             InputStream is = postMethod.getResponseBodyAsStream();
             // 获取请求结果字符串
             String result = IOUtils.toString(is);
             System.out.println("result: " + result);
             return new ResultMessage(status,result);
		} catch (Exception e) {
			return new ResultMessage(500,"请求异常");
		}
	}

    public static void main(String[] args) throws Exception {
        String wsdl = "http://192.168.68.101:8050/PSIGW/PeopleSoftServiceListeningConnector/CI_C_CI_DEPT_TBL.2.wsdl";
        int timeout = 10000;
        StringBuffer sb = new StringBuffer("");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        		+ "xmlns:m34=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/M345466.V1\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<m34:Create__CompIntfc__C_CI_DEPT_TBL>");
        sb.append("<m34:SETID>SHARE</m34:SETID>");
        sb.append("<m34:DEPTID>L90008</m34:DEPTID>");
        sb.append("<m34:DEPT_TBL>");
        sb.append("<m34:EFFDT>2018/01/01</m34:EFFDT>");
        sb.append("<m34:EFF_STATUS>A</m34:EFF_STATUS>");
        sb.append("<m34:DESCR>集团总部-信息部-测试部数据</m34:DESCR>");
        sb.append("<m34:DESCRSHORT>测试部数据</m34:DESCRSHORT>");
        sb.append("<m34:COMPANY>001</m34:COMPANY>");
        sb.append("<m34:SETID_LOCATION>SHARE</m34:SETID_LOCATION>");
        sb.append("<m34:LOCATION>10003</m34:LOCATION>");
        sb.append("<m34:C_DEPT_ORDER>1</m34:C_DEPT_ORDER>");
        sb.append("<m34:C_DEPT_LEVEL>25</m34:C_DEPT_LEVEL>");
        sb.append("<m34:MANAGER_TYPE>0</m34:MANAGER_TYPE>");
        sb.append("</m34:DEPT_TBL>");
        sb.append("</m34:Create__CompIntfc__C_CI_DEPT_TBL>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");

        
        
        // HttpClient发送SOAP请求
        System.out.println("HttpClient 发送SOAP请求");
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(wsdl);
        // 设置连接超时
        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        // 设置读取时间超时
        client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
        // 然后把Soap请求数据添加到PostMethod中
        RequestEntity requestEntity = new StringRequestEntity(sb.toString(), "text/xml", "UTF-8");
        //设置请求头部，否则可能会报 “no SOAPAction header” 的错误
        postMethod.setRequestHeader("soapAction","CI_C_CI_DEPT_TBL_C.V1");
        // 设置请求体
        postMethod.setRequestEntity(requestEntity);
        int status = client.executeMethod(postMethod);
        // 打印请求状态码
        System.out.println("status:" + status);
        // 获取响应体输入流
        InputStream is = postMethod.getResponseBodyAsStream();
        // 获取请求结果字符串
        String result = IOUtils.toString(is);
        System.out.println("result: " + result);
        
    }
}
