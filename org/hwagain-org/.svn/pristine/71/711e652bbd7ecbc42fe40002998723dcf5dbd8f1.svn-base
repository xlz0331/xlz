package com.hwagain.org.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hwagain.org.user.service.IOrgUserService;

@Component
@Order(value=1)
public class MyApplicationRunner implements ApplicationRunner{
	
	private final static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

	@Autowired
	private IOrgUserService orgUserService;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		try {
			orgUserService.updateUserRedis();
		} catch (Exception e) {
			logger.info("自启动用户插入redis异常，异常内容："+e.getMessage());
		}
	}

}
