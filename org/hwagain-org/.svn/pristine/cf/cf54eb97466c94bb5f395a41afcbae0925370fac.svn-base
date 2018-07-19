package com.hwagain.org.config.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.org.config.dto.OrgNumberConfigDto;
import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.org.config.mapper.OrgNumberConfigMapper;
import com.hwagain.org.config.service.IOrgNumberConfigService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
@Service("orgNumberConfigService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgNumberConfigServiceImpl extends ServiceImpl<OrgNumberConfigMapper, OrgNumberConfig> implements IOrgNumberConfigService {
	
	@Autowired
	private OrgNumberConfigMapper orgNumberConfigMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgNumberConfig.class, OrgNumberConfigDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgNumberConfigDto.class, OrgNumberConfig.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgNumberConfigDto> findAll() {
		Wrapper<OrgNumberConfig> wrapper = new CriterionWrapper<OrgNumberConfig>(OrgNumberConfig.class);
		List<OrgNumberConfig> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgNumberConfigDto.class);
	}

	@Override
	public OrgNumberConfig findOneBytype(String type) {
		Wrapper<OrgNumberConfig> wrapper = new CriterionWrapper<OrgNumberConfig>(OrgNumberConfig.class);
		wrapper.eq("type", type);
		return super.selectOne(wrapper);
	}

	@Override
	public OrgNumberConfigDto save(OrgNumberConfigDto dto) {
		OrgNumberConfig entity = dtoToEntityMapper.map(dto, OrgNumberConfig.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public OrgNumberConfigDto update(OrgNumberConfigDto dto) {
		OrgNumberConfig entity = dtoToEntityMapper.map(dto, OrgNumberConfig.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}

	@Override
	public Integer findNumberByType(String type) throws CustomException {
		return orgNumberConfigMapper.findNumberByType(type);
	}
}
