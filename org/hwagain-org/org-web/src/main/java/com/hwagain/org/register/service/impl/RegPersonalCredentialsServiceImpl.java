package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.entity.RegPersonalCredentials;
import com.hwagain.org.register.dto.RegPersonalCredentialsDto;
import com.hwagain.org.register.mapper.RegPersonalCredentialsMapper;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.register.service.IRegPersonalCredentialsService;
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
 *  入职注册-具体证件 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regPersonalCredentialsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPersonalCredentialsServiceImpl extends ServiceImpl<RegPersonalCredentialsMapper, RegPersonalCredentials> implements IRegPersonalCredentialsService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	@Autowired
	IRegPeopleService regPeopleService;
	
	

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPersonalCredentials.class, RegPersonalCredentialsDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPersonalCredentialsDto.class, RegPersonalCredentials.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegPersonalCredentialsDto> findAll() {
		Wrapper<RegPersonalCredentials> wrapper = new CriterionWrapper<RegPersonalCredentials>(RegPersonalCredentials.class);
		List<RegPersonalCredentials> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalCredentialsDto.class);
	}

	@Override
	public RegPersonalCredentialsDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegPersonalCredentialsDto.class);
	}

	@Override
	public RegPersonalCredentialsDto save(RegPersonalCredentialsDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());
		
		RegPersonalCredentials entity = dtoToEntityMapper.map(dto, RegPersonalCredentials.class);
		super.insert(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateById(peo);
		
		return dto;
	}

	@Override
	public RegPersonalCredentialsDto update(RegPersonalCredentialsDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());
		
		RegPersonalCredentials entity = dtoToEntityMapper.map(dto, RegPersonalCredentials.class);
		super.updateById(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateAllById(peo);
		
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegPersonalCredentials entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		//return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegPersonalCredentialsDto> findByPage(RegPersonalCredentialsDto dto,PageVO pageVo) {
		if(null!=dto.getPageVo())
			pageVo = dto.getPageVo();
		
		PageDto<RegPersonalCredentialsDto> pageDto = new PageDto<RegPersonalCredentialsDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegPersonalCredentials> wrapper = new CriterionWrapper< RegPersonalCredentials>( RegPersonalCredentials.class);

		wrapper.eq("is_delete", 0);
		if(null!=dto.getPersonalId() && !dto.getPersonalId().isEmpty())
			wrapper.eq("personal_id", dto.getPersonalId());
				
		Page< RegPersonalCredentials> page = super.selectPage(new Page< RegPersonalCredentials>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPersonalCredentialsDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
