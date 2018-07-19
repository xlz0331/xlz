package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.List;

import com.hwagain.org.register.entity.RegBaseData;
import com.hwagain.org.register.dto.RegBaseDataDto;
import com.hwagain.org.register.mapper.RegBaseDataMapper;
import com.hwagain.org.register.service.IRegBaseDataService;
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
 *  入职注册-基础数据
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regBaseDataService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegBaseDataServiceImpl extends ServiceImpl<RegBaseDataMapper, RegBaseData> implements IRegBaseDataService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegBaseData.class, RegBaseDataDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegBaseDataDto.class, RegBaseData.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegBaseDataDto> findAll() {
		Wrapper<RegBaseData> wrapper = new CriterionWrapper<RegBaseData>(RegBaseData.class);
		List<RegBaseData> list = super.selectList(wrapper);
		//List<RegBaseDataDto> list2 = entityToDtoMapper.mapAsList(list, RegBaseDataDto.class);
		//list2.get(0).setPageVo(new PageVO());
		//list2.get(0).getPageVo().setPageSize(200);
		return entityToDtoMapper.mapAsList(list, RegBaseDataDto.class);
	}

	@Override
	public RegBaseDataDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegBaseDataDto.class);
	}

	@Override
	public RegBaseDataDto save(RegBaseDataDto dto) {
		RegBaseData entity = dtoToEntityMapper.map(dto, RegBaseData.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public RegBaseDataDto update(RegBaseDataDto dto) {
		RegBaseData entity = dtoToEntityMapper.map(dto, RegBaseData.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegBaseDataDto> findByPage(RegBaseDataDto dto,PageVO pageVo) {
		if(null!=dto.getPageVo())
			pageVo = dto.getPageVo();
		
		PageDto<RegBaseDataDto> pageDto = new PageDto<RegBaseDataDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegBaseData> wrapper = new CriterionWrapper< RegBaseData>( RegBaseData.class);
		
		wrapper.eq("status", "A");
		if(null != dto.getType() && !dto.getType().isEmpty())
			wrapper.eq("type", dto.getType());
		if(null != dto.getTypecn() && !dto.getTypecn().isEmpty())
			wrapper.eq("typecn", dto.getTypecn());
		
		Page< RegBaseData> page = super.selectPage(new Page< RegBaseData>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegBaseDataDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	
	@Override
	public List<RegBaseDataDto> findByTypeCn(String typeCn) {
		Wrapper<RegBaseData> wrapper = new CriterionWrapper<RegBaseData>(RegBaseData.class);
		wrapper.eq("status", "A");
		wrapper.eq("typecn", typeCn);
		wrapper.orderBy("code");
		List<RegBaseData> list = super.selectList(wrapper);
		
		return entityToDtoMapper.mapAsList(list, RegBaseDataDto.class);
	}
	
}
