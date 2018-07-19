package com.hwagain.org.job.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.org.config.service.IOrgNumberConfigService;
import com.hwagain.org.constant.TypeConstant;
import com.hwagain.org.converter.JobIdToNameConverter;
import com.hwagain.org.converter.NodeCodeToNameConverter;
import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptService;
import com.hwagain.org.job.dto.OrgJobDto;
import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.job.mapper.OrgJobMapper;
import com.hwagain.org.job.service.IOrgJobService;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.task.AsyncTask;

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
 * @since 2018-03-15
 */
@Service("orgJobService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgJobServiceImpl extends ServiceImpl<OrgJobMapper, OrgJob> implements IOrgJobService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgJobServiceImpl.class);
	
	@Autowired
	private IOrgLogService orgLogService;
	
	@Autowired
	private OrgJobMapper orgJobMapper;
	
	@Autowired
	private IOrgNumberConfigService orgNumberConfigService;
	
	@Autowired
	private IOrgDeptService orgDeptService;
	
	@Autowired
	private AsyncTask asyncTask;
	
	@Autowired
	private IOrgDeptHistoryService orgDeptHistoryService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.getConverterFactory().registerConverter("jobConverter", new JobIdToNameConverter());
		factory.getConverterFactory().registerConverter("NodeConverter", new NodeCodeToNameConverter());
		ClassMapBuilder<OrgJob, OrgJobDto> builder = factory.classMap(OrgJob.class, OrgJobDto.class).byDefault();
		builder.fieldMap("parentId","parentName").converter("jobConverter").add();
		builder.fieldMap("managDepts","managDeptNames").converter("NodeConverter").add();
		builder.register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgJobDto.class, OrgJob.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgJobDto> findAll() {
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		List<OrgJob> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgJobDto.class);
	}

	@Override
	public OrgJobDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), OrgJobDto.class);
	}

	@Override
	public OrgJobDto save(OrgJobDto dto,OrgDept dept,String url) {
		Assert.notBlank(dto.getDataType(), "数据类型为空");
		Assert.notBlank(dto.getJobName(), "职务名称不能为空");
		if(dto.getIsManage()==null){
			dto.setIsManage(0);
		}
		OrgJob entity = dtoToEntityMapper.map(dto, OrgJob.class);
		entity.setDocCreateTime(new Date());
		entity.setDocCreatorId(UserUtils.getUserId());
		entity.setDeptId(dept.getFdId());
		entity.setDeptCode(dept.getDeptid());
		entity.setCompany(dept.getCompany());
		entity.setDeptVersion(dept.getVersion());
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("job");
		entity.setJobCode(String.valueOf(config.getNumber()+1));
		super.insert(entity);
		config.setNumber(config.getNumber()+1);
		orgNumberConfigService.updateById(config);
		try {
			orgLogService.save(new OrgLog("save", "org_job", 
					entity.getDeptVersion()+"",
					url, 
					"",
					entity.getJobName(), 
					"新增职务","",entity.getFdId(),
					entity.getJobCode(),entity.getCompany(),null,null));
		} catch (Exception e) {
			logger.info("新增职务-日志信息异常，异常信息："+e.getMessage());
		}
		return dto;
	}

	@Override
	public OrgJobDto update(OrgJobDto dto,String url) {
		OrgJob entity = dtoToEntityMapper.map(dto, OrgJob.class);
		entity.setDocLastUpdateId(UserUtils.getUserId());
		entity.setDocLastUpdateTime(new Date());
		super.updateById(entity);
		return dto;
	}

	@Override
	public String deleteByIds(String ids,String state) {
		OrgJob entity = super.selectById(ids);
		Assert.notNull(entity, "删除对象为空");
		orgJobMapper.deleteOrgJob(ids.split(";"));
		try {
			String remark = "注销【"+entity.getJobName()+"】";
			orgLogService.save(new OrgLog("delete", "org_job", 
					entity.getDeptVersion()+"",
					"/orgJob/delete", 
					entity.getJobName(),
					entity.getJobName(), 
					remark,entity.getFdId(),entity.getFdId(),
					entity.getJobType(),entity.getCompany(),null,state));
		} catch (Exception e) {
			logger.info("修改数据-日志信息异常，异常信息："+e.getMessage());
		}
		//生成同步记录
		asyncTask.saveSync("delete", "job", entity.getFdId(), JSONObject.toJSONString(entity),entity.getDeptVersion());
		return "删除成功！";
	}
	
	@Override
	public PageDto<OrgJobDto> findByPage(OrgJobDto dto,PageVO pageVo) {
		PageDto<OrgJobDto> pageDto = new PageDto<OrgJobDto>();
		pageDto.setPage(pageVo.getPage()+1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper< OrgJob> wrapper = new CriterionWrapper< OrgJob>( OrgJob.class);
		Page< OrgJob> page = super.selectPage(new Page< OrgJob>(pageDto.getPage(), pageDto.getPageSize()), wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), OrgJobDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	@Override
	public void saveJobList(List<OrgJobDto> list,OrgDept dept,String url) throws CustomException {
		if(list!=null&&list.size()>0){
			for (OrgJobDto orgJobDto : list) {
				if(orgJobDto.getDataType().equals("save")){
					this.save(orgJobDto, dept, url);
				}else if(orgJobDto.getDataType().equals("update")){
					this.update(orgJobDto, url);
				}else if(orgJobDto.getDataType().equals("delete")){
					this.deleteByIds(orgJobDto.getFdId(),null);
				}
			}
		}
		
	}

	@Override
	public List<OrgJobDto> findOrgJobByDeptId(String deptId) throws CustomException {
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("dept_id", deptId);
		wrapper.eq("is_delete", false);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgJobDto.class);
	}

	@Override
	public List<OrgJobDto> findOrgJobByDeptId(String deptId, String type) throws CustomException {
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("dept_id", deptId);
		wrapper.eq("job_type", type);
		wrapper.eq("is_delete", false);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgJobDto.class);
	}

	@Override
	public String saveJob(OrgJobDto dto) throws CustomException {
		Assert.notBlank(dto.getJobName(), "名称不能为空");
		Assert.notBlank(dto.getJobType(), "类型不能为空");
		Assert.notBlank(dto.getDeptId(), "部门ID不能为空");
		isOne(dto);
		OrgDept dept = orgDeptService.selectById(dto.getDeptId());
		Assert.notNull(dept, "部门不存在！");
		OrgJob entity = dtoToEntityMapper.map(dto, OrgJob.class);
		entity.setDocCreatorId(UserUtils.getUserId());
		entity.setDocCreateTime(new Date());
		entity.setDeptCode(dept.getDeptid());
		entity.setCompany(dept.getCompany());
		entity.setDeptVersion(orgDeptHistoryService.findMaxVersion(entity.getCompany())+1);
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("job");
		entity.setJobCode(String.valueOf(config.getNumber()+1));
		super.insert(entity);
		if(StringUtil.isNotNull(entity.getManagDepts())){
			orgDeptService.updateJobIdByIds(entity.getJobCode(), entity.getManagDepts());
		}
		config.setNumber(config.getNumber()+1);
		orgNumberConfigService.updateById(config);
		try {
			orgLogService.save(new OrgLog("save", "org_job", 
					entity.getDeptVersion()+"",
					"/orgJob/save", 
					entity.getJobName(),
					"", 
					"新增:"+entity.getJobName(),entity.getFdId(),"",
					entity.getJobType(),entity.getCompany(),null,dto.getState()));
		} catch (Exception e) {
			logger.info("新增职务-日志信息异常，异常信息："+e.getMessage());
		}
		//生成同步记录
		asyncTask.saveSync("save", "job", entity.getFdId(), JSONObject.toJSONString(entity),entity.getDeptVersion());
		return "保存成功";
	}
	
	private void isOne(OrgJobDto dto){
		if(TypeConstant.JOB_ZZW.equals(dto.getJobType())){
			Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
			wrapper.eq("job_type", TypeConstant.JOB_ZZW);
			wrapper.eq("dept_id", dto.getDeptId());
			wrapper.eq("is_delete", false);
			Integer i = super.selectCount(wrapper);
			if(i>0){
				Assert.throwException("主要职位已存在！");
			}
		}
	}

	@Override
	public String updateJob(OrgJobDto dto) throws CustomException {
		Assert.notBlank(dto.getFdId(), "ID不能为空");
		Assert.notBlank(dto.getJobName(), "名称不能为空");
		Assert.notBlank(dto.getJobType(), "类型不能为空");
		Assert.notBlank(dto.getDeptId(), "部门ID不能为空");
		OrgJob entity = super.selectById(dto.getFdId());
		Assert.notNull(entity, "修改职位不存在！");
		Assert.isTrue(entity.getJobType().equals(dto.getJobType()),"不能修改职位类型！");
		String befor = entity.getJobName();
		if(StringUtil.isNotNull(entity.getManagDepts())){
			orgDeptService.updateJobIdByIds("", entity.getManagDepts());
		}
		entity.setJobName(dto.getJobName());
		entity.setParentId(dto.getParentId());
		entity.setRemark(dto.getRemark());
		entity.setIsManage(dto.getIsManage());
		
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("job");
		entity.setDocLastUpdateId(UserUtils.getUserId());
		entity.setDocLastUpdateTime(new Date());
		entity.setDeptVersion(orgDeptHistoryService.findMaxVersion(entity.getCompany())+1);
		super.updateById(entity);
		if(StringUtil.isNotNull(entity.getManagDepts())){
			orgDeptService.updateJobIdByIds(entity.getJobCode(), entity.getManagDepts());
		}
		config.setNumber(config.getNumber()+1);
		orgNumberConfigService.updateById(config);
		try {
			orgLogService.save(new OrgLog("update", "org_job", 
					entity.getDeptVersion()+"",
					"/orgJob/update", 
					befor,
					entity.getJobName(), 
					"由【"+befor+"】修改成【"+entity.getJobName()+"】",entity.getFdId(),entity.getFdId(),entity.getJobType(),
					entity.getCompany(),null,dto.getState()));
		} catch (Exception e) {
			logger.info("修改职务-日志信息异常，异常信息："+e.getMessage());
		}
		//生成同步记录
		asyncTask.saveSync("update", "job", entity.getFdId(), JSONObject.toJSONString(entity),entity.getDeptVersion());
		return "保存成功";
	}

	@Override
	public List<OrgJobDto> findOrgJobByParam(String deptId, String type) throws CustomException {
		Assert.notBlank(deptId, "部门ID不能为空");
		Assert.notBlank(type, "类型不能为空");
		OrgDept dept = orgDeptService.selectById(deptId);
		Assert.notNull(dept, "节点对象为空");
		//上级节点可选职位
		List<OrgJob> parentList = null;
		if(StringUtil.isNotNull(dept.getParentId())){
			Wrapper<OrgJob> parentwrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
			parentwrapper.eq("dept_id", dept.getParentId());
			parentwrapper.in("job_type", new String[] {TypeConstant.JOB_ZZW,TypeConstant.JOB_FGZW,TypeConstant.JOB_FZC,TypeConstant.JOB_ZJL,TypeConstant.JOB_BZ});
			parentwrapper.eq("is_delete", false);
			parentList = super.selectList(parentwrapper);
		}
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("dept_id", deptId);
		wrapper.in("job_type", type.split(";"));
		wrapper.eq("is_delete", false);
		List<OrgJob> list = super.selectList(wrapper);
		if(parentList!=null&&parentList.size()>0){
			list.addAll(parentList);
		}
		return entityToDtoMapper.mapAsList(list, OrgJobDto.class);
	}

	@Override
	public String findOrgJobName(String code) throws CustomException {
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("job_code", code);
		wrapper.eq("is_delete", false);
		List<OrgJob> list = super.selectList(wrapper);
		if(list!=null&&list.size()>0){
			return list.get(0).getJobName();
		}
		return "";
	}

	@Override
	public void updateJobDeptIdByDeptId(String newDeptId,String newDeptCode,String[] oldDeptId) throws CustomException {
		try {
			orgJobMapper.updateJobDeptByDept(newDeptId, newDeptCode, oldDeptId);
		} catch (Exception e) {
			logger.info("原先部门职位数据转移到新的部门发生异常，异常内容为："+e.getMessage());
			Assert.throwException("原先部门职位数据转移到新的部门发生异常");
		}
		
	}

	@Override
	public List<OrgJobDto> findOrgJobByCompany(String company, String typeCode) throws CustomException {
		Assert.notBlank(company, "公司编号不能为空");
		Assert.notBlank(typeCode, "节点类型不能为空");
		List<OrgDeptDto> deptList = orgDeptService.findAllByCompany(company, TypeConstant.ORG_GS);
		Assert.notEmpty(deptList, "公司数据为空");
		OrgDeptDto deptdto = deptList.get(0);
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("dept_code", deptdto.getDeptid());
		wrapper.in("job_type", typeCode.split(";"));
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgJobDto.class);
	}

	@Override
	public List<OrgJob> findOrgJobByCompany(String company) throws CustomException {
		Wrapper<OrgJob> wrapper = new CriterionWrapper<OrgJob>(OrgJob.class);
		wrapper.eq("company", company);
		return super.selectList(wrapper);
	}
	
	@Override
	public List<OrgExcel> findJobByParam(String company) throws CustomException {
		return orgJobMapper.findJobByParam(company);
	}
}
