package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.entity.RegPersonalFamily;
import com.hwagain.org.register.dto.RegPersonalFamilyDto;
import com.hwagain.org.register.mapper.RegPersonalFamilyMapper;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.register.service.IRegPersonalFamilyService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
 *  入职注册-家庭成员 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regPersonalFamilyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPersonalFamilyServiceImpl extends ServiceImpl<RegPersonalFamilyMapper, RegPersonalFamily> implements IRegPersonalFamilyService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	@Autowired
	IRegPeopleService regPeopleService;
	
	

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPersonalFamily.class, RegPersonalFamilyDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPersonalFamilyDto.class, RegPersonalFamily.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegPersonalFamilyDto> findAll() {
		Wrapper<RegPersonalFamily> wrapper = new CriterionWrapper<RegPersonalFamily>(RegPersonalFamily.class);
		List<RegPersonalFamily> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalFamilyDto.class);
	}

	@Override
	public RegPersonalFamilyDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegPersonalFamilyDto.class);
	}

	@Override
	public RegPersonalFamilyDto save(RegPersonalFamilyDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");
		Assert.isTrue(null != dto.getName() && !dto.getName().isEmpty(), "姓名不能为空");
		Assert.isTrue(null != dto.getRelation() && !dto.getRelation().isEmpty(), "关系不能为空");
		Assert.isTrue(null != dto.getPhone() && !dto.getPhone().isEmpty(), "联系电话不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());
		
		RegPersonalFamily entity = dtoToEntityMapper.map(dto, RegPersonalFamily.class);
		super.insert(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateById(peo);
		
		return dto;
	}

	@Override
	public RegPersonalFamilyDto update(RegPersonalFamilyDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");
		//Assert.isTrue(null != dto.getName() && !dto.getName().isEmpty(), "姓名不能为空");
		//Assert.isTrue(null != dto.getRelation() && !dto.getRelation().isEmpty(), "关系不能为空");
		//Assert.isTrue(null != dto.getPhone() && !dto.getPhone().isEmpty(), "联系电话不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());
		
		RegPersonalFamily entity = dtoToEntityMapper.map(dto, RegPersonalFamily.class);
		super.updateById(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateAllById(peo);
		
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegPersonalFamily entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		//return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegPersonalFamilyDto> findByPage(RegPersonalFamilyDto dto,PageVO pageVo) {
		if(null!=dto.getPageVo())
			pageVo = dto.getPageVo();
		
		PageDto<RegPersonalFamilyDto> pageDto = new PageDto<RegPersonalFamilyDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegPersonalFamily> wrapper = new CriterionWrapper< RegPersonalFamily>( RegPersonalFamily.class);

		wrapper.eq("is_delete", 0);
		if(null!=dto.getPersonalId() && !dto.getPersonalId().isEmpty())
			wrapper.eq("personal_id", dto.getPersonalId());
				
		Page< RegPersonalFamily> page = super.selectPage(new Page< RegPersonalFamily>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPersonalFamilyDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
