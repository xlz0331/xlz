package com.hwagain.org.register.service;


import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.register.dto.RegPsJobDto;
import com.hwagain.org.register.entity.RegPsJob;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hanj
 * @since 2018-06-19
 */
public interface IRegPsJobService extends IService<RegPsJob> {
	
	public String saveOrUpdate(RegPsJob entity) throws CustomException;
	
	public PageDto<RegPsJobDto> listPsJobs(String namelike,PageVO pageVo) throws CustomException;
	
	public RegPsJob findNameIsExist(String name) throws CustomException;
}
