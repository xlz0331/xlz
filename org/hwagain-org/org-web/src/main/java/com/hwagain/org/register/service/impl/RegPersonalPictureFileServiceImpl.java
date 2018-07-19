package com.hwagain.org.register.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.register.entity.RegPersonalCredentialsFile;
import com.hwagain.org.register.entity.RegPersonalPictureFile;
import com.hwagain.org.register.dto.RegPersonalCredentialsFileDto;
import com.hwagain.org.register.dto.RegPersonalPictureFileDto;
import com.hwagain.org.register.mapper.RegPersonalPictureFileMapper;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.register.service.IRegPersonalPictureFileService;
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
 *  服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-07-10
 */
@Service("regPersonalPictureFileService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPersonalPictureFileServiceImpl extends ServiceImpl<RegPersonalPictureFileMapper, RegPersonalPictureFile> implements IRegPersonalPictureFileService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired
	IRegPeopleService regPeopleService;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPersonalPictureFile.class, RegPersonalPictureFileDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPersonalPictureFileDto.class, RegPersonalPictureFile.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<RegPersonalPictureFileDto> findAll() {
		Wrapper<RegPersonalPictureFile> wrapper = new CriterionWrapper<RegPersonalPictureFile>(RegPersonalPictureFile.class);
		List<RegPersonalPictureFile> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegPersonalPictureFileDto.class);
	}

	@Override
	public RegPersonalPictureFileDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegPersonalPictureFileDto.class);
	}

	@Override
	public RegPersonalPictureFileDto save(RegPersonalPictureFileDto dto) {
		Assert.isTrue(null != dto.getPersonalId() && !dto.getPersonalId().isEmpty(), "人员ID不能为空");

		RegPeople peo = regPeopleService.selectById(dto.getPersonalId());
		Assert.notNull(peo, "没有找到人员信息登记注册记录");
		Assert.isTrue(peo.getIsTops() == 0, "记录已经传PS,不允许在编辑");

		//只保留一张有效的照片
		Wrapper<RegPersonalPictureFile> wrapper = new CriterionWrapper<RegPersonalPictureFile>(
				RegPersonalPictureFile.class);
		wrapper.eq("is_delete", 0);
		wrapper.eq("personal_id", dto.getPersonalId());
		List<RegPersonalPictureFile> listFile = super.selectList(wrapper);
		
		
		for(RegPersonalPictureFile f : listFile)
		{
			f.setIsDelete(1);
			f.setDocLastUpdateId(UserUtils.getUserId());
			f.setDocLastUpdateTime(new Date());
			super.updateById(f);
		}
		
		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());
		
		RegPersonalPictureFile entity = dtoToEntityMapper.map(dto, RegPersonalPictureFile.class);
		super.insert(entity);
		
		
		
		return dto;
	}

	@Override
	public RegPersonalPictureFileDto update(RegPersonalPictureFileDto dto) {
		RegPersonalPictureFile entity = dtoToEntityMapper.map(dto, RegPersonalPictureFile.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegPersonalPictureFile entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		//return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegPersonalPictureFileDto> findByPage(RegPersonalPictureFileDto dto,PageVO pageVo) {
		PageDto<RegPersonalPictureFileDto> pageDto = new PageDto<RegPersonalPictureFileDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegPersonalPictureFile> wrapper = new CriterionWrapper< RegPersonalPictureFile>( RegPersonalPictureFile.class);
		Page< RegPersonalPictureFile> page = super.selectPage(new Page< RegPersonalPictureFile>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPersonalPictureFileDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}
	
	@Override
	public RegPersonalPictureFileDto findByPersonal(String personalId) {
		Wrapper<RegPersonalPictureFile> wrapper = new CriterionWrapper<RegPersonalPictureFile>(
				RegPersonalPictureFile.class);
		wrapper.eq("is_delete", 0);
		wrapper.eq("personal_id", personalId);
		wrapper.orderBy("doc_create_time",false);
		RegPersonalPictureFile f = super.selectFirst(wrapper);
		return entityToDtoMapper.map(f, RegPersonalPictureFileDto.class);
	}
}
