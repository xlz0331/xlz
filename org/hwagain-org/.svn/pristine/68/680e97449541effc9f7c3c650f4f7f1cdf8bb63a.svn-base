package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.List;

import com.hwagain.org.register.entity.RegSyncPs;
import com.hwagain.org.register.dto.RegSyncPsDto;
import com.hwagain.org.register.mapper.RegSyncPsMapper;
import com.hwagain.org.register.service.IRegSyncPsService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.util.ArraysUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-07-02
 */
@Service("regSyncPsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegSyncPsServiceImpl extends ServiceImpl<RegSyncPsMapper, RegSyncPs> implements IRegSyncPsService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegSyncPs.class, RegSyncPsDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegSyncPsDto.class, RegSyncPs.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegSyncPsDto> findAll() {
		Wrapper<RegSyncPs> wrapper = new CriterionWrapper<RegSyncPs>(RegSyncPs.class);
		List<RegSyncPs> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegSyncPsDto.class);
	}

	@Override
	public RegSyncPsDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegSyncPsDto.class);
	}

	@Override
	public RegSyncPsDto save(RegSyncPsDto dto) {
		RegSyncPs entity = dtoToEntityMapper.map(dto, RegSyncPs.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public RegSyncPsDto update(RegSyncPsDto dto) {
		RegSyncPs entity = dtoToEntityMapper.map(dto, RegSyncPs.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegSyncPsDto> findByPage(RegSyncPsDto dto,PageVO pageVo) {
		PageDto<RegSyncPsDto> pageDto = new PageDto<RegSyncPsDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegSyncPs> wrapper = new CriterionWrapper< RegSyncPs>( RegSyncPs.class);
		Page< RegSyncPs> page = super.selectPage(new Page< RegSyncPs>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegSyncPsDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
