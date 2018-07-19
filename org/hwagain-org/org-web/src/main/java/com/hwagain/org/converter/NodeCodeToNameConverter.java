package com.hwagain.org.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hwagain.framework.core.converter.BaseConverter;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.org.dept.service.IOrgDeptService;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class NodeCodeToNameConverter extends BaseConverter<String, String> {

	private static final Logger logger = LoggerFactory.getLogger(NodeCodeToNameConverter.class);
	
	private IOrgDeptService orgDeptService;
	
	@Override
	public String convert(String arg0, Type<? extends String> arg1, MappingContext arg2) {
		try {
			return getOrgDeptService().findNodeNameByCode(arg0);
		} catch (Exception e) {
			logger.info("部门编号转换名称异常，异常内容："+e.getMessage());
		}
		return "";
	}

	public IOrgDeptService getOrgDeptService() {
		if(orgDeptService==null){
			orgDeptService = SpringBeanUtil.getBean("orgDeptService");
		}
		return orgDeptService;
	}

	public void setOrgDeptService(IOrgDeptService orgDeptService) {
		this.orgDeptService = orgDeptService;
	}

}
