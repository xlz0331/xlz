package com.hwagain.org.register.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.mybatisplus.enums.SqlLike;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.org.register.dto.RegPsJobDto;
import com.hwagain.org.register.entity.RegPsJob;
import com.hwagain.org.register.mapper.RegPsJobMapper;
import com.hwagain.org.register.service.IRegPsJobService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-06-19
 */
@Service("regPsJobService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegPsJobServiceImpl extends ServiceImpl<RegPsJobMapper, RegPsJob> implements IRegPsJobService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegPsJob.class, RegPsJobDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegPsJobDto.class, RegPsJob.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public String saveOrUpdate(RegPsJob entity) throws CustomException {
		Wrapper<RegPsJob> wrapper = new CriterionWrapper<RegPsJob>(RegPsJob.class);
		wrapper.eq("is_delete", false);
		wrapper.eq("code", entity.getCode());
		RegPsJob obj = super.selectOne(wrapper);
		if(obj!=null){
			obj.setIsDelete(entity.getIsDelete());
			obj.setName(entity.getName());
			obj.setShortname(entity.getShortname());
			super.updateById(obj);
		}else{
			super.insert(entity);
		}
		return "操作成功";
	}

	/**
	 * 查找ps的job列表，提供模糊匹配和分页
	 */
	@Override
	public PageDto<RegPsJobDto> listPsJobs(String namelike,PageVO pageVo) throws CustomException {
		PageDto<RegPsJobDto> pageDto = new PageDto<RegPsJobDto>();
		pageDto.setPage(pageVo.getPage());
		pageDto.setPageSize(pageVo.getPageSize());
		
		Wrapper<RegPsJob> wrapper = new CriterionWrapper<RegPsJob>(RegPsJob.class);
		wrapper.like("name", namelike,SqlLike.DEFAULT);
		
		Page<RegPsJob> page = super.selectPage(new Page<RegPsJob>(pageVo.getPage(),pageVo.getPageSize()), wrapper);
		
		if (page.getRecords().size() != 0) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegPsJobDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	@Override
	public RegPsJob findNameIsExist(String name) throws CustomException {
		Wrapper<RegPsJob> wrapper = new CriterionWrapper<RegPsJob>(RegPsJob.class);
		wrapper.eq("shortname", name);
		return super.selectOne(wrapper);
	}
}
