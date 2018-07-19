package com.hwagain.org.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.response.ErrorResponseData;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.dept.service.IOrgDeptService;

@Aspect
@Order(1)
@Component("auditValidation")
public class AuditValidation {
	
	private static final Logger logger = LoggerFactory.getLogger(AuditValidation.class);
	
	@Autowired
	private IOrgVersionAuditService orgVersionAuditService;
	@Autowired
	private IOrgDeptService orgDeptService;

	@Around( value = "@annotation(com.hwagain.org.annotation.OrgAuditValidation)")
	public Object validation(ProceedingJoinPoint joinPoint) throws CustomException{
		Object result = null;
		try {
			Object[] objs = joinPoint.getArgs();
			for (Object obj : objs) {
				if(obj instanceof String){
					String ids = String.valueOf(obj);
					String[] id = ids.split(";");
					OrgDept entity = orgDeptService.selectById(id[0]);
					if(entity!=null){
						orgVersionAuditService.isActive(entity.getCompany());
					}
				}else{
					JSONObject json = new JSONObject(obj);
					String company = json.has("company")?json.getString("company"):"";
					orgVersionAuditService.isActive(company);
				}
			}
			result =  joinPoint.proceed();
		} catch (Exception e1) {
			logger.error("验证是否能修改切片异常，异常信息："+e1.getMessage());
		} catch (Throwable e){
			logger.error("切片异常，异常信息："+e.getMessage());
		}
		return result;
	}
	
	protected ErrorResponseData createErrorDara(){
		ErrorResponseData data = ErrorResponseData.newInstance("403","对不起，您没有该操作的执行权限!");
		getResponse().setStatus(403);
		return data;
	}
	
	/**
	 * 获取响应
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 获取请求
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
