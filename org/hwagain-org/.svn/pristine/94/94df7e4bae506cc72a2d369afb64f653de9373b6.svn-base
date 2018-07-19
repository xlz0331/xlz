package com.hwagain.org.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.job.dto.OrgJobHistoryDto;
import com.hwagain.org.job.entity.OrgJobHistory;
import com.hwagain.org.job.mapper.OrgJobHistoryMapper;
import com.hwagain.org.job.service.IOrgJobHistoryService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-15
 */
@Service("orgJobHistoryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgJobHistoryServiceImpl extends ServiceImpl<OrgJobHistoryMapper, OrgJobHistory> implements IOrgJobHistoryService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired OrgJobHistoryMapper oOrgJobHistoryMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgJobHistory.class, OrgJobHistoryDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgJobHistoryDto.class, OrgJobHistory.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public OrgJobHistory findOne(String fdId, Integer version) throws CustomException {
		Wrapper<OrgJobHistory> wrapper = new CriterionWrapper<OrgJobHistory>(OrgJobHistory.class);
		wrapper.eq("fd_id", fdId);
		wrapper.eq("dept_version", version);
		return super.selectOne(wrapper);
	}
	
	@Override
	public List<OrgExcel> findJobByParam(String company,Integer version) throws CustomException {
		return oOrgJobHistoryMapper.findJobByParam(company,version);
	}
}
