package com.hwagain.org.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.entity.OrgUserJob;
import com.hwagain.org.user.mapper.OrgUserJobMapper;
import com.hwagain.org.user.mapper.OrgUserMapper;
import com.hwagain.org.user.service.IOrgUserJobService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-04-25
 */
@Service("orgUserJobService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgUserJobServiceImpl extends ServiceImpl<OrgUserJobMapper, OrgUserJob> implements IOrgUserJobService {
	
	@Autowired
	private OrgUserMapper orgUserMapper;
	@Autowired
	private OrgUserJobMapper orgUserJobMapper;
	
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgUserJob.class, OrgUserJobDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgUserJobDto.class, OrgUserJob.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	/**
	 * 通过岗位id查找该岗位人员
	 */
	@Override
	public List<OrgUserJobDto> findByJobCode(String jobcode) {
		List<OrgUserJobDto> userJobs = orgUserJobMapper.findByJobCode(jobcode);
		return entityToDtoMapper.mapAsList(userJobs, OrgUserJobDto.class);
	}
}
