package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.entity.RegPersonalCredentialsFile;
import com.hwagain.org.register.dto.RegPersonalCredentialsFileDto;
import com.hwagain.org.register.mapper.RegPersonalCredentialsFileMapper;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.register.service.IRegPersonalCredentialsFileService;
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
 * 入职注册-证件附件 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regPersonalCredentialsFileService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPersonalCredentialsFileServiceImpl
		extends ServiceImpl<RegPersonalCredentialsFileMapper, RegPersonalCredentialsFile>
		implements IRegPersonalCredentialsFileService {

	// entity转dto
	static MapperFacade entityToDtoMapper;

	// dto转entity
	static MapperFacade dtoToEntityMapper;

	@Autowired
	IRegPeopleService regPeopleService;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPersonalCredentialsFile.class, RegPersonalCredentialsFileDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();

		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPersonalCredentialsFileDto.class, RegPersonalCredentialsFile.class).byDefault()
				.register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegPersonalCredentialsFileDto> findAll() {
		Wrapper<RegPersonalCredentialsFile> wrapper = new CriterionWrapper<RegPersonalCredentialsFile>(
				RegPersonalCredentialsFile.class);
		List<RegPersonalCredentialsFile> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalCredentialsFileDto.class);
	}

	@Override
	public RegPersonalCredentialsFileDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegPersonalCredentialsFileDto.class);
	}

	@Override
	public RegPersonalCredentialsFileDto save(RegPersonalCredentialsFileDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());

		RegPersonalCredentialsFile entity = dtoToEntityMapper.map(dto, RegPersonalCredentialsFile.class);
		super.insert(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateById(peo);

		return dto;
	}

	@Override
	public RegPersonalCredentialsFileDto update(RegPersonalCredentialsFileDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());

		RegPersonalCredentialsFile entity = dtoToEntityMapper.map(dto, RegPersonalCredentialsFile.class);
		super.updateById(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateAllById(peo);

		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegPersonalCredentialsFile entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		// return super.deleteBatchIds(Arrays.asList(id));
	}

	@Override
	public PageDto<RegPersonalCredentialsFileDto> findByPage(RegPersonalCredentialsFileDto dto, PageVO pageVo) {
		PageDto<RegPersonalCredentialsFileDto> pageDto = new PageDto<RegPersonalCredentialsFileDto>();
		pageDto.setPage(pageVo.getPage() + 1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper<RegPersonalCredentialsFile> wrapper = new CriterionWrapper<RegPersonalCredentialsFile>(
				RegPersonalCredentialsFile.class);

		wrapper.eq("is_delete", 0);
		if (null != dto.getPersonalId() && !dto.getPersonalId().isEmpty())
			wrapper.eq("personal_id", dto.getPersonalId());

		Page<RegPersonalCredentialsFile> page = super.selectPage(
				new Page<RegPersonalCredentialsFile>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPersonalCredentialsFileDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	@Override
	public List<RegPersonalCredentialsFileDto> findByPersonal(String personalId) {
		Wrapper<RegPersonalCredentialsFile> wrapper = new CriterionWrapper<RegPersonalCredentialsFile>(
				RegPersonalCredentialsFile.class);
		wrapper.eq("is_delete", 0);
		wrapper.eq("personal_id", personalId);
		List<RegPersonalCredentialsFile> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalCredentialsFileDto.class);
	}
}
