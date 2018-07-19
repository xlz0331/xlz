package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegEmployCollege;
import com.hwagain.org.register.dto.RegEmployCollegeDto;
import com.hwagain.org.register.mapper.RegEmployCollegeMapper;
import com.hwagain.org.register.service.IRegEmployCollegeService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;

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
 *  入职注册-录用人员明细 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regEmployCollegeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegEmployCollegeServiceImpl extends ServiceImpl<RegEmployCollegeMapper, RegEmployCollege> implements IRegEmployCollegeService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegEmployCollege.class, RegEmployCollegeDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegEmployCollegeDto.class, RegEmployCollege.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegEmployCollegeDto> findAll() {
		Wrapper<RegEmployCollege> wrapper = new CriterionWrapper<RegEmployCollege>(RegEmployCollege.class);
		List<RegEmployCollege> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegEmployCollegeDto.class);
	}

	@Override
	public RegEmployCollegeDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegEmployCollegeDto.class);
	}

	@Override
	public RegEmployCollegeDto save(RegEmployCollegeDto dto) {

		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());
		
		RegEmployCollege entity = dtoToEntityMapper.map(dto, RegEmployCollege.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public RegEmployCollegeDto update(RegEmployCollegeDto dto) {

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());
		
		RegEmployCollege entity = dtoToEntityMapper.map(dto, RegEmployCollege.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegEmployCollege entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		//return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegEmployCollegeDto> findByPage(RegEmployCollegeDto dto,PageVO pageVo) {
		if(null!=dto.getPageVo())
			pageVo = dto.getPageVo();
		
		PageDto<RegEmployCollegeDto> pageDto = new PageDto<RegEmployCollegeDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegEmployCollege> wrapper = new CriterionWrapper< RegEmployCollege>( RegEmployCollege.class);

		wrapper.eq("is_delete", 0);
		//if (null != dto.getCompanyId() && !dto.getCompanyId().isEmpty())
		//	wrapper.eq("company_id", dto.getCompanyId());
		if (null != dto.getPertype())
			wrapper.eq("pertype", dto.getPertype());
		if (null != dto.getIsGiveup())
			wrapper.eq("is_giveup", dto.getIsGiveup());
		if (null != dto.getIsRegister())
			wrapper.eq("is_register", dto.getIsRegister());
		
		if (null == pageDto.getOrderBy())
			wrapper.orderBy("year", false);
				
		Page< RegEmployCollege> page = super.selectPage(new Page< RegEmployCollege>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegEmployCollegeDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
