package com.hwagain.org.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hwagain.framework.core.converter.BaseConverter;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.org.job.service.IOrgJobService;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class JobIdToNameConverter extends BaseConverter<String, String> {
	
	private static final Logger logger = LoggerFactory.getLogger(JobIdToNameConverter.class);
	
	private IOrgJobService orgJobService;

	@Override
	public String convert(String arg0, Type<? extends String> arg1, MappingContext arg2) {
		try {
			return getOrgJobService().findOrgJobName(arg0);
		} catch (Exception e) {
			logger.info("职位转换名称异常，异常内容："+e.getMessage());
		}
		return "";
	}

	public IOrgJobService getOrgJobService() {
		if(orgJobService==null){
			orgJobService = SpringBeanUtil.getBean("orgJobService");
		}
		return orgJobService;
	}

	public void setOrgJobService(IOrgJobService orgJobService) {
		this.orgJobService = orgJobService;
	}

}
