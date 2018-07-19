package com.hwagain.org.job.service;

import java.util.List;

import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.org.job.dto.OrgJobDesignAllDto;
import com.hwagain.org.job.dto.OrgJobDesignDto;
import com.hwagain.org.job.dto.OrgJobMenu;
import com.hwagain.org.job.entity.OrgJobDesign;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangyj
 * @since 2018-06-13
 */
public interface IOrgJobDesignService extends IService<OrgJobDesign> {
	
	public List<OrgJobDesignDto> findAll() throws CustomException; 
	
	public OrgJobDesignDto findOne(String fdId) throws CustomException;
	
	public OrgJobDesignDto save(OrgJobDesignDto dto) throws CustomException;
	
	public OrgJobDesignDto update(OrgJobDesignDto dto) throws CustomException;
	
	public Boolean deleteByIds(String ids) throws CustomException;
	
	public PageDto<OrgJobDesignDto> findByPage(OrgJobDesignDto dto,PageVO pageVo) throws CustomException;

	public List<OrgJobDesignAllDto> getDetails(String company, String type_code, String deptId);
	
	
	//定岗定编菜单接口
	public List<OrgJobMenu> findJobMenu();
}
