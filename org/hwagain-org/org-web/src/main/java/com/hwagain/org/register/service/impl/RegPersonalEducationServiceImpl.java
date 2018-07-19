package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.entity.RegPersonalEducation;
import com.hwagain.org.register.dto.RegPersonalEducationDto;
import com.hwagain.org.register.mapper.RegPersonalEducationMapper;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.register.service.IRegPersonalEducationService;
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
 * 入职注册-教育经历 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regPersonalEducationService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPersonalEducationServiceImpl extends ServiceImpl<RegPersonalEducationMapper, RegPersonalEducation>
		implements IRegPersonalEducationService {

	// entity转dto
	static MapperFacade entityToDtoMapper;

	// dto转entity
	static MapperFacade dtoToEntityMapper;

	@Autowired
	IRegPeopleService regPeopleService;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPersonalEducation.class, RegPersonalEducationDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();

		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPersonalEducationDto.class, RegPersonalEducation.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegPersonalEducationDto> findAll() {
		Wrapper<RegPersonalEducation> wrapper = new CriterionWrapper<RegPersonalEducation>(RegPersonalEducation.class);
		List<RegPersonalEducation> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalEducationDto.class);
	}

	@Override
	public RegPersonalEducationDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegPersonalEducationDto.class);
	}

	@Override
	public RegPersonalEducationDto save(RegPersonalEducationDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");
		Assert.isTrue(null != dto.getTypename() && !dto.getTypename().isEmpty(), "类型不能为空");
		Assert.isTrue(null != dto.getSchool() && !dto.getSchool().isEmpty(), "毕业院校不能为空");
		Assert.isTrue(null != dto.getStartdate(), "开始时间不能为空");
		Assert.isTrue(null != dto.getEnddate(), "毕业时间不能为空");
		Assert.isTrue(null != dto.getMajor() && !dto.getMajor().isEmpty(), "专业不能为空");
		Assert.isTrue(null != dto.getEducation() && !dto.getEducation().isEmpty(), "学历不能为空");
		// Assert.isTrue(null != dto.getDegree() && !dto.getDegree().isEmpty(),
		// "学位不能为空");
		Assert.isTrue(null != dto.getRegime() && !dto.getRegime().isEmpty(), "学制不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());

		RegPersonalEducation entity = dtoToEntityMapper.map(dto, RegPersonalEducation.class);
		super.insert(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateById(peo);

		return dto;
	}

	@Override
	public RegPersonalEducationDto update(RegPersonalEducationDto dto) {

		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");
		// Assert.isTrue(null != dto.getTypename() &&
		// !dto.getTypename().isEmpty(), "类型不能为空");
		// Assert.isTrue(null != dto.getSchool() && !dto.getSchool().isEmpty(),
		// "毕业院校不能为空");
		// Assert.isTrue(null != dto.getStartdate() , "开始时间不能为空");
		// Assert.isTrue(null != dto.getEnddate(), "毕业时间不能为空");
		// Assert.isTrue(null != dto.getMajor() && !dto.getMajor().isEmpty(),
		// "专业不能为空");
		// Assert.isTrue(null != dto.getEducation() &&
		// !dto.getEducation().isEmpty(), "学历不能为空");
		// Assert.isTrue(null != dto.getDegree() && !dto.getDegree().isEmpty(),
		// "学位不能为空");
		// Assert.isTrue(null != dto.getRegime() && !dto.getRegime().isEmpty(),
		// "学制不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());

		RegPersonalEducation entity = dtoToEntityMapper.map(dto, RegPersonalEducation.class);
		super.updateById(entity);

		peo.setIsRecord(1); // 履历信息-未录完
		regPeopleService.updateAllById(peo);

		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegPersonalEducation entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		// return super.deleteBatchIds(Arrays.asList(id));
	}

	@Override
	public PageDto<RegPersonalEducationDto> findByPage(RegPersonalEducationDto dto, PageVO pageVo) {
		if (null != dto.getPageVo())
			pageVo = dto.getPageVo();

		PageDto<RegPersonalEducationDto> pageDto = new PageDto<RegPersonalEducationDto>();
		pageDto.setPage(pageVo.getPage() + 1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper<RegPersonalEducation> wrapper = new CriterionWrapper<RegPersonalEducation>(RegPersonalEducation.class);

		wrapper.eq("is_delete", 0);
		if (null != dto.getPersonalId() && !dto.getPersonalId().isEmpty())
			wrapper.eq("personal_id", dto.getPersonalId());

		if (null == pageDto.getOrderBy())
			wrapper.orderBy("startdate", false);

		Page<RegPersonalEducation> page = super.selectPage(
				new Page<RegPersonalEducation>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPersonalEducationDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
}
