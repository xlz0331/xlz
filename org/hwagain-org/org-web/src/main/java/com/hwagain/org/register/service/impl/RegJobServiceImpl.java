package com.hwagain.org.register.service.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.hwagain.org.register.entity.RegJob;
import com.hwagain.org.register.entity.RegPeople;
import com.hwagain.org.company.entity.OrgCompany;
import com.hwagain.org.company.service.IOrgCompanyService;
import com.hwagain.org.dept.dto.OrgDeptProDto;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.job.entity.OrgJobPro;
import com.hwagain.org.job.service.IOrgJobProService;
import com.hwagain.org.register.dto.RegJobDto;
import com.hwagain.org.register.dto.RegJobUpperDto;
import com.hwagain.org.register.dto.RegPeopleDto;
import com.hwagain.org.register.mapper.RegJobMapper;
import com.hwagain.org.register.service.IRegJobService;
import com.hwagain.org.register.service.IRegPeopleService;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.service.IOrgUserJobService;


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
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.ArraysUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  入职注册-岗位信息 服务实现类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@Service("regJobService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegJobServiceImpl extends ServiceImpl<RegJobMapper, RegJob> implements IRegJobService {
	
	
	@Autowired
	IRegPeopleService regPeopleService;
	
	@Autowired
	IOrgJobProService orgJobProService;
	
	@Autowired
	IOrgUserJobService orgUserJobService;
	
	@Autowired
	IOrgDeptProService orgDeptProService;
	
	@Autowired
	IOrgCompanyService orgCompanyService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	static MapperFacade entityToDeptDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(RegJob.class, RegJobDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(RegJobDto.class, RegJob.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
		
		MapperFactory factorythr = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgDeptPro.class, OrgDeptProDto.class).byDefault().register();
		entityToDeptDtoMapper = factorythr.getMapperFacade();
	}

	@Override
	public List<RegJobDto> findAll() {
		Wrapper<RegJob> wrapper = new CriterionWrapper<RegJob>(RegJob.class);
		List<RegJob> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, RegJobDto.class);
	}

	@Override
	public RegJobDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), RegJobDto.class);
	}

	@Override
	public RegJobDto save(RegJobDto dto) {

		Assert.notNull(dto, "提交的记录无数据");
		RegJob regJob = super.selectById(dto.getFdId());
		if(regJob != null) {
			return this.update(dto);
		}
		
		dto.setDocCreateId(UserUtils.getUserId());
		dto.setDocCreateTime(new Date());
		
		RegJob entity = dtoToEntityMapper.map(dto, RegJob.class);
		super.insert(entity);
		RegPeopleDto people = regPeopleService.findOne(dto.getFdId());
		if(this.isCompleteEnter(dto)) {
			people.setIsPosition(9);
		}else {
			people.setIsPosition(1);
		}
		regPeopleService.update(people);
		return dto;
	}

	@Override
	public RegJobDto update(RegJobDto dto) {

		dto.setDocLastUpdateId(UserUtils.getUserId());
		dto.setDocLastUpdateTime(new Date());
		
		RegJob entity = dtoToEntityMapper.map(dto, RegJob.class);
		super.updateById(entity);
		RegPeopleDto people = regPeopleService.findOne(dto.getFdId());
		if(this.isCompleteEnter(dto)) {
			people.setIsPosition(9);
		}else {
			people.setIsPosition(1);
		}
		regPeopleService.update(people);
		return dto;
	}
	
	/**
	 * 是否录入完成
	 * @return
	 */
	private boolean isCompleteEnter(RegJobDto dto) {
		if(dto.getPositionNbr() == null) {
			return false;
		}
		Wrapper<OrgJobPro> wrapper = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
		wrapper.eq("job_code", dto.getPositionNbr());
		OrgJobPro job = orgJobProService.selectOne(wrapper);
		Assert.notNull(job, "未找到该岗位");
		String jobType = job.getJobType();
		//当选择的岗位为设备和工艺类时，分管范围必填
		if(jobType == "24" || jobType == "25" ) {
			if(dto.getScope() == null) return false;
		}
		Field[] fields = dto.getClass().getDeclaredFields();
		String[] col = {"upperName",
						"upperNbr",
						"upperPosi",
						"jobName",
						"jobLevel",
						"institutionId",
						"emptype",
						"empattribute",
						"tryrange",
						"positionNbr",
						"position",
						"systemId"};//必填字段
		int len = col.length;
		for (int i = 0; i < len; i++) {
			for(Field f : fields) {
				f.setAccessible(true);
				if(col[i] == f.getName()) {
					try {
						if (f.get(dto) == null) {
							return false;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		for (String sid : id) {
			RegJob entity = super.selectById(sid);
			entity.setIsDelete(1);
			super.updateById(entity);
		}
		return true;
		//return super.deleteBatchIds(Arrays.asList(id));
	}
	
	@Override
	public PageDto<RegJobDto> findByPage(RegJobDto dto,PageVO pageVo) {
		if(null!=dto.getPageVo())
			pageVo = dto.getPageVo();
		
		PageDto<RegJobDto> pageDto = new PageDto<RegJobDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< RegJob> wrapper = new CriterionWrapper< RegJob>( RegJob.class);

		wrapper.eq("is_delete", 0);
				
		Page< RegJob> page = super.selectPage(new Page< RegJob>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), RegJobDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	/**
	 * 初始化录入界面
	 */
	@Override
	public RegJobDto getAndInit(String personalId) throws CustomException {
		Assert.notNull(personalId,"人员ID为空");
		RegPeople people = regPeopleService.selectById(personalId);
		Assert.notNull(people, "ID为"+ personalId + "的人员在注册登记表中无记录");
		if(people.getIsPosition()==9) {
			Assert.isTrue(false, "ID为"+ personalId + "的人员在注册登已录入完成");
		}
		
		RegJob regJob = super.selectById(people.getFdId());
		if(regJob == null) {
			regJob = new RegJob();
			regJob.setCompanyId(people.getCompanyId());
			regJob.setFdId(people.getFdId());
			regJob.setPertype(people.getPertype());
		}
		
		RegJobDto regJobDto = entityToDtoMapper.map(regJob, RegJobDto.class);
		regJobDto.setName(people.getName());
		regJobDto.setSex(people.getSex());
		regJobDto.setIdCard(people.getNid());
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("DEPTID", people.getCompanyId());
		OrgDeptPro deptPro = orgDeptProService.selectOne(wrapper);
		regJobDto.setCompanyName(deptPro.getDescrshort());
		return regJobDto;
	}
	/**
	 * 通过岗位获取该岗位上级所有信息
	 */
	@Override
	public RegJobUpperDto getUpperInfo(String jobCode) throws CustomException {
		Assert.notNull(jobCode, "岗位号为空");
		RegJobUpperDto upperDto = new RegJobUpperDto();
		//获取直接上级人员list
		Wrapper<OrgJobPro> wrapper = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
		wrapper.eq("job_code", jobCode);
		OrgJobPro jobPro = orgJobProService.selectOne(wrapper);
		Assert.notNull(jobPro, "查不到该记录");
		List<OrgUserJobDto> userJobDtos = orgUserJobService.findByJobCode(jobPro.getParentId());
		
		//获取直接上级岗位
		if(userJobDtos.size() == 0) {
			Wrapper<OrgJobPro> wrapper2 = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
			wrapper2.eq("job_code", jobPro.getParentId());
			OrgJobPro upperJob = orgJobProService.selectOne(wrapper2);
			upperDto.setLeaderJob(upperJob.getJobName());
		}else {
			upperDto.setLeaderJob(userJobDtos.get(0).getJobName());
		}
		
		//获取所有上级部门
		OrgDeptPro dept = orgDeptProService.selectById(jobPro.getDeptId());
		String uppers = dept.getHierarchyId();
		String[] idList = uppers.substring(1, uppers.length()-1).split("x");
		List<OrgDeptPro> upperList = orgDeptProService.findByIds(idList);
		
		//获取分管范围
		if((jobPro.getJobType().equals("24")  || jobPro.getJobType().equals("25")) && jobPro.getManagDepts() != null) {
			String[] managDepts = jobPro.getManagDepts().split(";");
			Wrapper<OrgDeptPro> wrapper2 = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
			wrapper2.in("DEPTID", managDepts);
			List<OrgDeptPro> managDeptList = orgDeptProService.selectList(wrapper2);
			upperDto.setManagDepts(entityToDeptDtoMapper.mapAsList(managDeptList, OrgDeptProDto.class));
		}
		
		upperDto.setLeaders(userJobDtos);
		upperDto.setAllUpperDept(entityToDeptDtoMapper.mapAsList(upperList, OrgDeptProDto.class));
		return upperDto;
	}
}
