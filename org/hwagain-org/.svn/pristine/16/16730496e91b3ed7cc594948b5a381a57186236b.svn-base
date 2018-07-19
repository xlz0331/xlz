package com.hwagain.org.company.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hwagain.org.company.dto.OrgCompanyDto;
import com.hwagain.org.company.entity.OrgCompany;
import com.hwagain.org.company.mapper.OrgCompanyMapper;
import com.hwagain.org.company.service.IOrgCompanyService;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.service.IOrgLogService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-12
 */
@Service("orgCompanyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgCompanyServiceImpl extends ServiceImpl<OrgCompanyMapper, OrgCompany> implements IOrgCompanyService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgCompanyServiceImpl.class);
	
	@Autowired
	private OrgCompanyMapper orgCompanyMapper;
	
	@Autowired
	private IOrgLogService orgLogService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgCompany.class, OrgCompanyDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgCompanyDto.class, OrgCompany.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgCompanyDto> findAll() {
		Wrapper<OrgCompany> wrapper = new CriterionWrapper<OrgCompany>(OrgCompany.class);
		List<OrgCompany> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgCompanyDto.class);
	}

	@Override
	public OrgCompany findOne(String fdId) {
		return super.selectById(fdId);
	}

	@Override
	public String save(OrgCompanyDto dto) {
		Assert.notBlank(dto.getDescr(), "公司全称为空");
		Assert.notBlank(dto.getDescrshort(), "公司简称为空");
		OrgCompany entity = dtoToEntityMapper.map(dto, OrgCompany.class);
		String maxCompany = orgCompanyMapper.findMaxCode();
		if(StringUtil.isNull(maxCompany)){
			maxCompany = "001";
			entity.setCompany(maxCompany);
		}else{
			entity.setCompany(String.format("%03d",Integer.valueOf(maxCompany)+1));
		}
		Integer version = null;
		if(version==null){
			version = 0;
		}
		entity.setVersion(version+1);
		entity.setDocCreateTime(new Date());
		entity.setDocCreatorId(UserUtils.getUserId());
		super.insert(entity);
		try {
			orgLogService.save(new OrgLog("save", "org_company", 
					entity.getVersion()+"",
					"/orgCompany/save", 
					"",
					"公司全称："+entity.getDescr()+",公司简称："+entity.getDescrshort(), 
					"新增公司","",entity.getFdId(),entity.getType(),entity.getCompany(),null,null));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("新增公司-日志信息异常");
		}
		return "保存成功";
	}

	@Override
	public String update(OrgCompanyDto dto) {
		OrgCompany entity = super.selectById(dto.getFdId());
		
		String before = "公司全称："+entity.getDescr()+",公司简称："+entity.getDescrshort();
		String after = "公司全称："+dto.getDescr()+",公司简称："+dto.getDescrshort();
		
		entity = dtoToEntityMapper.map(dto, OrgCompany.class);
		entity.setDocLastUpdateId(UserUtils.getUserId());
		entity.setDocLastUpdateTime(new Date());
		super.updateById(entity);
		try {
			orgLogService.save(new OrgLog("update", "org_company", 
					entity.getVersion()+"",
					"/orgCompany/update", 
					before,
					after, 
					"修改公司信息",entity.getFdId(),entity.getFdId(),entity.getType(),entity.getCompany(),null,null));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改公司-日志信息异常");
		}
		return "保存成功";
	}

	@Override
	public String deleteByIds(String ids) {
		String[] id = ids.split(";");
		try {
			orgCompanyMapper.deleteByIds(id);
			
			// TODO  待完成注销公司操作
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("删除异常");
		}
		return "删除";
	}
	
	@Override
	public String findNameByCode(String code) throws CustomException {
		Wrapper<OrgCompany> wrapper = new CriterionWrapper<OrgCompany>(OrgCompany.class);
		wrapper.eq("is_delete", false);
		wrapper.eq("company", code);
		List<OrgCompany> list = super.selectList(wrapper);
		if(list!=null&&list.size()>0){
			return list.get(0).getDescrshort();
		}
		return "";
	}
}
