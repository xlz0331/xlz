package com.hwagain.org.type.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.converter.UserIdToNameConverter;
import com.hwagain.org.type.dto.OrgTypeDto;
import com.hwagain.org.type.entity.OrgType;
import com.hwagain.org.type.mapper.OrgTypeMapper;
import com.hwagain.org.type.service.IOrgTypeService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
@Service("orgTypeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgTypeServiceImpl extends ServiceImpl<OrgTypeMapper, OrgType> implements IOrgTypeService {
	
	@Autowired
	private OrgTypeMapper orgTypeMapper;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.getConverterFactory().registerConverter("userConverter", new UserIdToNameConverter());
		ClassMapBuilder<OrgType, OrgTypeDto> builder =  factory.classMap(OrgType.class, OrgTypeDto.class).byDefault();
		builder.fieldMap("docCreatorId", "docCreatorName").converter("userConverter").add();
		builder.register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgTypeDto.class, OrgType.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public String save(OrgTypeDto dto) throws CustomException {
		Assert.notBlank(dto.getName(), "类型名称为空");
		OrgType entity = dtoToEntityMapper.map(dto, OrgType.class);
		entity.setDocCreateTime(new Date());
		entity.setDocCreatorId(UserUtils.getUserId());
		
		//生成类型CODE，查询最大CODE+1
		try {
			String maxCode = orgTypeMapper.findMaxCode();
			if(StringUtil.isNull(maxCode)){
				maxCode = "10";
				entity.setCode(maxCode);
			}else{
				entity.setCode(String.valueOf(Integer.valueOf(maxCode)+1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.insert(entity);
		return "保存成功";
	}

	@Override
	public String update(OrgTypeDto dto) throws CustomException {
		Assert.notBlank(dto.getName(), "类型名称为空");
		Assert.notBlank(dto.getFdId(), "数据ID为空");
		OrgType entity = super.selectById(dto.getFdId());
		Assert.notNull(entity, "查找不到要修改的数据");
		entity.setName(dto.getName());
		entity.setDocLastUpdateId(UserUtils.getUserId());
		entity.setDocLastUpdateTime(new Date());
		super.updateById(entity);
		return "保存成功";
	}

	@Override
	public String delete(String ids) throws CustomException {
		Assert.notBlank(ids, "参数为空");
		String[] id = ids.split(";");
		try {
			orgTypeMapper.deleteByIds(id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("删除异常");
		}
		return "删除成功";
	}

	@Override
	public List<OrgTypeDto> findAll(String keywork) throws CustomException {
		Wrapper<OrgType> wrapper = new CriterionWrapper<>(OrgType.class);
		if(StringUtil.isNotNull(keywork)){
			wrapper.like("name", "%"+keywork+"%");
		}
		wrapper.eq("is_delete", false);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgTypeDto.class);
	}

	@Override
	public OrgType findOneByCode(String code) throws CustomException {
		Wrapper<OrgType> wrapper = new CriterionWrapper<>(OrgType.class);
		wrapper.eq("code", code);
		wrapper.eq("is_delete", false);
		return super.selectOne(wrapper);
	}

}
