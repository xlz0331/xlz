package com.hwagain.org.converter;

import com.hwagain.framework.api.org.api.ISysOrgService;
import com.hwagain.framework.core.converter.BaseConverter;
import com.hwagain.framework.core.util.SpringBeanUtil;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class UserIdToNameConverter extends BaseConverter<String, String> {

	private ISysOrgService sysOrgService;
	
	@Override
	public String convert(String source, Type<? extends String> destinationType, MappingContext mappingContext) {
		if(source!=null){
			String name = "";
			try {
				name = getSysOrgService().getPersonNameById(source);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return name;
		}
		return "";
	}

	public ISysOrgService getSysOrgService() {
		if(sysOrgService==null){
			sysOrgService = SpringBeanUtil.getBean("sysOrgService");
		}
		return sysOrgService;
	}

	public void setSysOrgService(ISysOrgService sysOrgService) {
		this.sysOrgService = sysOrgService;
	}
	
	
}
