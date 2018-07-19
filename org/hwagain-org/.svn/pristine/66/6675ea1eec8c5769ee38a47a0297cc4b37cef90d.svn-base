package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.List;

import com.hwagain.org.register.entity.RegWorkerInterviewPicture;
import com.hwagain.org.register.dto.RegWorkerInterviewPictureDto;
import com.hwagain.org.register.mapper.RegWorkerInterviewPictureMapper;
import com.hwagain.org.register.service.IRegWorkerInterviewPictureService;
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
 * @since 2018-07-10
 */
@Service("regWorkerInterviewPictureService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegWorkerInterviewPictureServiceImpl extends ServiceImpl<RegWorkerInterviewPictureMapper, RegWorkerInterviewPicture> implements IRegWorkerInterviewPictureService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegWorkerInterviewPicture.class, RegWorkerInterviewPictureDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegWorkerInterviewPictureDto.class, RegWorkerInterviewPicture.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegWorkerInterviewPictureDto> findAll() {
		Wrapper<RegWorkerInterviewPicture> wrapper = new CriterionWrapper<RegWorkerInterviewPicture>(RegWorkerInterviewPicture.class);
		List<RegWorkerInterviewPicture> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegWorkerInterviewPictureDto.class);
	}

	@Override
	public RegWorkerInterviewPictureDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegWorkerInterviewPictureDto.class);
	}

	@Override
	public RegWorkerInterviewPictureDto save(RegWorkerInterviewPictureDto dto) {
		RegWorkerInterviewPicture entity = dtoToEntityMapper.map(dto, RegWorkerInterviewPicture.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public RegWorkerInterviewPictureDto update(RegWorkerInterviewPictureDto dto) {
		RegWorkerInterviewPicture entity = dtoToEntityMapper.map(dto, RegWorkerInterviewPicture.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegWorkerInterviewPictureDto> findByPage(RegWorkerInterviewPictureDto dto,PageVO pageVo) {
		PageDto<RegWorkerInterviewPictureDto> pageDto = new PageDto<RegWorkerInterviewPictureDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegWorkerInterviewPicture> wrapper = new CriterionWrapper< RegWorkerInterviewPicture>( RegWorkerInterviewPicture.class);
		Page< RegWorkerInterviewPicture> page = super.selectPage(new Page< RegWorkerInterviewPicture>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegWorkerInterviewPictureDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
