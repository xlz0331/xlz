package com.hwagain.org.user.service;

import java.util.List;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.user.dto.OrgUserDto;
import com.hwagain.org.user.dto.OrgUserJobDeptDto;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.entity.OrgUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-03-17
 */
public interface IOrgUserService extends IService<OrgUser> {
	
	public Boolean isUpdate(List<String> deptCode) throws CustomException;
	
	public List<OrgUserJobDto> findUserAllByJob();
	
	public void updateUserRedis() throws CustomException;
	
	public List<OrgUserDto> findUserAllByDeptId() throws CustomException;
	
	public List<OrgUserJobDeptDto> findUserAllByDeptId(String deptcode) throws CustomException;
}
