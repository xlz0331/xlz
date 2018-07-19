package com.hwagain.org.dept.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.redis.RedisUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.core.util.TreeUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.company.service.IOrgCompanyService;
import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.org.config.service.IOrgNumberConfigService;
import com.hwagain.org.constant.RedisConstant;
import com.hwagain.org.constant.TypeConstant;
import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgRegroup;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.mapper.OrgDeptMapper;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.dept.service.IOrgDeptService;
import com.hwagain.org.job.dto.OrgJobDto;
import com.hwagain.org.job.service.IOrgJobProService;
import com.hwagain.org.job.service.IOrgJobService;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.task.AsyncTask;
import com.hwagain.org.type.entity.OrgType;
import com.hwagain.org.type.service.IOrgTypeService;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.service.IOrgUserService;

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
@Service("orgDeptService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgDeptServiceImpl extends ServiceImpl<OrgDeptMapper, OrgDept> implements IOrgDeptService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgDeptServiceImpl.class);
	
	@Autowired
	private IOrgNumberConfigService orgNumberConfigService;
	
	@Autowired
	private IOrgLogService orgLogService;
	
	@Autowired
	private IOrgDeptHistoryService orgDeptHistoryService;
	
	@Autowired
	private IOrgJobService orgJobService;
	
	@Autowired
	private IOrgTypeService orgTypeService;
	
	@Autowired
	private IOrgUserService orgUserService;
	
	@Autowired
	private OrgDeptMapper orgDeptMapper;
	
	@Autowired
	private AsyncTask asyncTask;
	
	@Autowired
	private IOrgDeptProService orgDeptProService;
	
	@Autowired RedisUtil redisUtil;
	
	@Autowired IOrgJobProService orgJobProService;
	
	@Autowired IOrgCompanyService orgCompanyService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgDept.class, OrgDeptDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgDeptDto.class, OrgDept.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgDeptDto> findAll() {
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		List<OrgDept> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgDeptDto.class);
	}

	@Override
	public OrgDeptDto findOne(String fdId) {
		Assert.notBlank(fdId, "fdId为空");
		OrgDeptDto dto = entityToDtoMapper.map(super.selectById(fdId), OrgDeptDto.class);
		dto.setJobs(orgJobService.findOrgJobByDeptId(fdId));
		return dto;
	}

	@Transactional
	@Override
	public OrgDeptDto save(OrgDeptDto dto) {
		Assert.notBlank(dto.getDescrshort(), "名称为空");
		Assert.notBlank(dto.getTypeCode(), "类型ID为空");
		dto.setFdId(IdWorker.getId()+"");
		OrgDept parentNode = null;
		if(StringUtil.isNotNull(dto.getParentId())){
			parentNode = super.selectById(dto.getParentId());
			Assert.notNull(parentNode, "上级节点为空");
			dto.setCompany(parentNode.getCompany());
			dto.setParentDEPTID(parentNode.getDeptid());
			dto.setDescr(parentNode.getDescr()+"-"+dto.getDescrshort());
			dto.setHierarchyId(parentNode.getHierarchyId()+dto.getFdId()+"x");
		}else{
			Assert.notBlank(dto.getCompany(), "公司为空");
			dto.setHierarchyId("x"+dto.getFdId()+"x");
			dto.setDescr(dto.getDescrshort());
		}
		OrgType orgType = orgTypeService.findOneByCode(dto.getTypeCode());
		Assert.notNull(orgType, "类型为空");
		
		OrgDept entity = dtoToEntityMapper.map(dto, OrgDept.class);
		entity.setDocCreateTime(new Date());
		entity.setDocCreatorId(UserUtils.getUserId());
		entity.setVersion(orgDeptHistoryService.findMaxVersion(entity.getCompany())+1);
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("dept");
		
		entity.setDeptid(String.valueOf(config!=null?config.getNumber()+1:1));
		super.insert(entity);
		
		{
			if(StringUtil.isNotNull(dto.getAddIds())){
				orgDeptMapper.updateBatchByIds(entity.getFdId(), dto.getAddIds().split(";"));
			}
		}
		
		config.setNumber(config.getNumber()+1);
		config.setDocLastUpdateId(UserUtils.getUserId());
		config.setDocLastUpdateTime(new Date());
		orgNumberConfigService.updateById(config);
		//插入职务表
		try {
			orgJobService.saveJobList(dto.getJobs(), entity, "/orgDept/save");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("新增职务信息异常，异常信息："+e.getMessage());
		}
		
		try {
			String remark = "【"+parentNode.getDescrshort()+"】"+"下,新增【"+entity.getDescrshort()+"】";
			orgLogService.save(new OrgLog("save", "org_dept", 
					entity.getVersion()+"",
					"/orgDept/save", 
					entity.getDescrshort(),
					"", 
					remark,entity.getFdId(),"",
					entity.getTypeCode(),entity.getCompany(),null,dto.getState()));
		} catch (Exception e) {
			logger.info("新增"+orgType.getName()+"-日志信息异常，异常信息："+e.getMessage());
		}
		
		//生成同步记录
		asyncTask.saveSync("save", "dept", entity.getFdId(), JSONObject.toJSONString(entity),entity.getVersion());
		return dto;
	}

	@Override
	public OrgDeptDto update(OrgDeptDto dto) {
		Assert.notBlank(dto.getDescrshort(), "名称为空");
		Assert.notBlank(dto.getFdId(), "修改数据ID为空");
		OrgDept entity = super.selectById(dto.getFdId());
		Assert.notNull(entity, "修改数据不存在");
		String before = null;
		if(!dto.getDescrshort().equals(entity.getDescrshort())){
			if(StringUtil.isNotNull(dto.getParentId())){
				Assert.isTrue(dto.getParentId().equals(entity.getParentId()),"暂时不支持转移部门");
			}
			if(StringUtil.isNotNull(entity.getParentId())){
				OrgDept parentNode = super.selectById(entity.getParentId());
				dto.setDescr(parentNode.getDescr()+"-"+dto.getDescrshort());
			}
			before = entity.getDescrshort();
			entity = dtoToEntityMapper.map(dto, OrgDept.class);
			entity.setDocLastUpdateId(UserUtils.getUserId());
			entity.setDocLastUpdateTime(new Date());
			entity.setVersion(orgDeptHistoryService.findMaxVersion(entity.getCompany())+1);
			super.updateById(entity);
			
			{
				if(StringUtil.isNotNull(dto.getAddIds())){
					orgDeptMapper.updateBatchByIds(entity.getFdId(), dto.getAddIds().split(";"));
				}
			}
			
			try {
				orgLogService.save(new OrgLog("update", "org_dept", 
						entity.getVersion()+"",
						"/orgDept/update", 
						before,
						dto.getDescrshort(), 
						"修改数据",entity.getFdId(),entity.getFdId(),
						entity.getTypeCode(),entity.getCompany(),null,dto.getState()));
			} catch (Exception e) {
				logger.info("修改数据-日志信息异常，异常信息："+e.getMessage());
			}
		}
		//生成同步记录
		asyncTask.saveSync("update", "dept", entity.getFdId(), JSONObject.toJSONString(entity),entity.getVersion());
		return dto;
	}

	@Override
	public String deleteById(String id,String state) {
		OrgDept entity = super.selectById(id);
		Assert.notNull(entity, "删除对象不存在");
		Integer version = orgDeptHistoryService.findMaxVersion(entity.getCompany())+1;
		isDelete(id,state,entity,version);
		int i = orgDeptMapper.deleteBatchByIds(new String[] {id});
		if(i>0){
			//生成同步记录
			asyncTask.saveSync("delete", "dept", id, "",version);
			return "删除成功";
		}else{
			return "删除失败";
		}
	}
	
	private void isDelete(String id,String state,OrgDept entity,Integer version){
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.like("parent_id", id);
		wrapper.eq("is_delete", false);
		List<OrgDept> list = super.selectList(wrapper);
		if(list!=null&&list.size()>0){
			Assert.throwException("注销失败！节点下存在子节点！");
		}
		List<String> strList = new ArrayList<>();
		strList.add(entity.getFdId());
		Assert.isTrue(orgUserService.isUpdate(strList),"注销失败！节点下还存在在职人员！");
		try {
			String remark = "注销【"+entity.getDescrshort()+"】";
			orgLogService.save(new OrgLog("delete", "org_dept", 
					version+"",
					"/orgDept/delete", 
					entity.getDescrshort(),
					entity.getDescrshort(), 
					remark,entity.getFdId(),entity.getFdId(),
					entity.getTypeCode(),entity.getCompany(),null,state));
		} catch (Exception e) {
			logger.info("修改数据-日志信息异常，异常信息："+e.getMessage());
		}
	}

	/**
	 * 
	 * 按节点ID查询子节点，不传默认查询根节点
	 * 
	 */
	@Override
	public List<OrgDeptDto> findByNodeId(String fdId) throws CustomException {
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		if(StringUtil.isNotNull(fdId)){
			wrapper.eq("parent_id", fdId);
		}else{
			wrapper.isNull("parent_id");
		}
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptDto.class);
	}

	@Override
	public String splitDept(String fdId, String names,String state) throws CustomException {
		Assert.notBlank(fdId, "请传入要拆分的ID");
		Assert.notBlank(names, "拆分后的名称为空");
		String[] name = names.split(";");
		Assert.isTrue(name.length>1,"请至少拆分成两个");
		OrgDeptPro proEntity = orgDeptProService.selectById(fdId);
		if(proEntity==null){
			Assert.throwException("未审核数据，不能拆分！");
		}
		OrgDept entity = super.selectById(fdId);
		Assert.notNull(entity, "拆分对象为空");
		List<OrgDept> list = new ArrayList<OrgDept>();
		OrgDept dept = null;
		String userid = UserUtils.getUserId();
		Date date = new Date();
		Integer version = orgDeptHistoryService.findMaxVersion(entity.getCompany())+1;
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("dept");
		OrgDept defaultDept = null;
		String ids = "";
		for (int i = 0; i < name.length; i++) {
			dept = new OrgDept();
			dept.setCompany(entity.getCompany());
			dept.setFdId(String.valueOf(IdWorker.getId()));
			dept.setDeptid(String.valueOf(config.getNumber()+i+1));
			dept.setDescr(disposeHierarchy(entity.getDescr())+name[i]);
			dept.setDescrshort(name[i]);
			dept.setDocCreateTime(date);
			dept.setDocCreatorId(userid);
			dept.setHierarchyId(entity.getHierarchyId().replace(entity.getFdId(), dept.getFdId()));
			dept.setParentDEPTID(entity.getParentDEPTID());
			dept.setParentId(entity.getParentId());
			dept.setTypeCode(entity.getTypeCode());
			dept.setVersion(version);
			dept.setOrderValue(entity.getOrderValue());
			dept.setJobId(entity.getJobId());
			list.add(dept);
			if(i==0)
				defaultDept = dept;
			ids+= ";"+dept.getFdId();
		}
		if(list!=null&&list.size()>0){
			orgDeptMapper.insertOrgDeptBatch(list);
		}
		
		//注销原先部门
		this.deleteByIds(fdId);
		
		List<String> strList = new ArrayList<>();
		strList.add(entity.getFdId());
		//修改子部门所属上级
		orgDeptMapper.updateParentId(defaultDept.getFdId(), defaultDept.getDeptid(), strList);
		
		//修改原先部门职位，转移到新的部门
		orgJobService.updateJobDeptIdByDeptId(defaultDept.getFdId(), defaultDept.getDeptid(), new String[] {entity.getFdId()});
		
		Integer number = name.length+1;
		config.setNumber(config.getNumber()+number);
		orgNumberConfigService.updateById(config);
		try {
			orgLogService.save(new OrgLog("split", "org_dept", 
					entity.getVersion()+"",
					"/orgDept/splitDept", 
					entity.getDescrshort(),
					names, 
					"由【"+entity.getDescrshort()+"】拆分成【"+names+"】",
					entity.getFdId(),ids.substring(1),entity.getTypeCode(),
					entity.getCompany(),JSONArray.toJSONString(list),state));
		} catch (Exception e) {
			logger.info("拆分"+entity.getDescrshort()+"-日志信息异常，异常信息："+e.getMessage());
		}
		//asyncTask.dataCorrection();
		return "保存成功";
	}
	
	private String disposeHierarchy(String hierarchy){
		try {
			if(hierarchy.indexOf("-")>0){
				return hierarchy.substring(0,hierarchy.lastIndexOf("-")+1);
			}
		} catch (Exception e) {
			logger.info("处理部门层级字符串异常，异常内容："+e.getMessage());
		}
		return "";
	}

	@Override
	public List<OrgDeptDto> findAllByCompany(String company,String type) throws CustomException {
		Assert.notBlank(company, "公司Code不能为空");
		Assert.notBlank(type, "类型Code不能为空");
		String[] code = type.split(";");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("company", company);
		wrapper.in("type_code", code);
		wrapper.eq("is_delete", false);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptDto.class);
	}
	
	@Override
	public List<OrgDept> findAllByCompany(String company) throws CustomException {
		Assert.notBlank(company, "公司Code不能为空");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("company", company);
		wrapper.eq("is_delete", false);
		return super.selectList(wrapper);
	}

	@Override
	public List<OrgDept> findByNode(String fdId,String company) throws CustomException {
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		if(StringUtil.isNotNull(fdId)){
			wrapper.eq("parent_id", fdId);
		}else{
			wrapper.isNull("parent_id");
		}
		wrapper.eq("company", company);
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		return super.selectList(wrapper);
	}
	
	private void disposeDept(List<OrgDept> listAll,OrgDept dept,String company){
		List<OrgDept> list = this.findByNode(dept!=null?dept.getFdId():null,company);
		for (int i = 0; i < list.size(); i++) {
			String hId = dept!=null?dept.getHierarchyId()+list.get(i).getFdId()+"x":"x"+list.get(i).getFdId()+"x";
			list.get(i).setHierarchyId(hId);
			String descr = dept!=null?dept.getDescr()+"-"+list.get(i).getDescrshort():list.get(i).getDescrshort();
			list.get(i).setDescr(descr);
			listAll.add(list.get(i));
			disposeDept(listAll,list.get(i),null);
		}
	}

	@Override
	public List<OrgDeptDto> findDeptParentNode(String fdId,String code) throws CustomException {
		Assert.notBlank(fdId, "ID为空");
		OrgDept entity = super.selectById(fdId);
		Assert.notNull(entity, "对象为空");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("parent_id", entity.getParentId());
		wrapper.eq("is_delete", 0);
		if(StringUtil.isNotNull(code)){
			wrapper.eq("type_code", code);
		}
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptDto.class);
	}

	@Override
	public String dataCorrection(String company) throws CustomException {
		try {
			List<OrgDept> listAll = new ArrayList<>();
			this.disposeDept(listAll, null,company);
			if(listAll!=null&&listAll.size()>0){
				for (OrgDept orgDept : listAll) {
					super.updateById(orgDept);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("数据修正异常");
		}
		return "修正成功";
	}

	/**
	 * 合并部门
	 */
	@Override
	public String mergeDept(String fdIds, String name,String state) throws CustomException {
		Assert.notBlank(fdIds, "合并的ID不能为空");
		String[] ids = fdIds.split(";");
		Assert.isTrue(ids.length>1,"至少合并两个");
		Assert.notBlank(name, "合并之后的名称不能为空");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.in("fd_id", ids);
		wrapper.eq("is_delete", false);
		List<OrgDept> list = super.selectList(wrapper);
		if(list==null||list.size()!=ids.length){
			Assert.throwException("合并项不存在或者不匹配");
		}
		
		List<OrgDeptPro> proEntity = orgDeptProService.findByIds(ids);
		if(proEntity==null||proEntity.size()<1){
			Assert.throwException("存在未审核数据，不能合并！");
		}
		
		StringBuffer names = new StringBuffer("");
		list.forEach(dept->{
			names.append(","+dept.getDescrshort());
		});
		OrgNumberConfig config = orgNumberConfigService.findOneBytype("dept");
		//新建合并项
		OrgDept entity = new OrgDept();
		entity.setDescrshort(name);
		entity.setVersion(orgDeptHistoryService.findMaxVersion(entity.getCompany())+1);
		entity.setCompany(list.get(0).getCompany());
		entity.setDeptid(String.valueOf(config.getNumber()+1));
		entity.setDocCreateTime(new Date());
		entity.setDocCreatorId(UserUtils.getUserId());
		entity.setFdId(String.valueOf(IdWorker.getId()));
		entity.setParentDEPTID(list.get(0).getParentDEPTID());
		entity.setParentId(list.get(0).getParentId());
		entity.setTypeCode(list.get(0).getTypeCode());
		super.insert(entity);
		//修改
		config.setNumber(config.getNumber()+1);
		orgNumberConfigService.updateById(config);
		//注销原先项
		this.deleteByIds(fdIds);
		//修改原先项所有子集
		orgDeptMapper.updateParentId(entity.getFdId(), entity.getDeptid(), Arrays.asList(ids));
		//原先部门职位转移到新部门
		orgJobService.updateJobDeptIdByDeptId(entity.getFdId(), entity.getDeptid(), ids);
		try {
			orgLogService.save(new OrgLog("merge", "org_dept", 
					entity.getVersion()+"",
					"/orgDept/mergeDept", 
					names.substring(1),
					name, 
					"【"+names.substring(1)+"】合并成【"+name+"】",
					fdIds,entity.getFdId(),
					entity.getTypeCode(),entity.getCompany(),JSONArray.toJSONString(list),state));
		} catch (Exception e) {
			logger.info("拆分"+entity.getDescrshort()+"-日志信息异常，异常信息："+e.getMessage());
		}
		asyncTask.dataCorrection(entity.getCompany());
		return "保存成功";
	}

	@Override
	public List<OrgDeptDto> findDeptByIds(String ids) {
		Assert.notBlank(ids, "ID为空");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("is_delete", false);
		wrapper.in("fd_id", ids.split(";"));
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptDto.class);
	}

	@Override
	public String saveBatchByRegroup(String regroups) throws CustomException {
		Assert.notBlank(regroups, "不能为空");
		List<OrgRegroup> list = null;
		try {
			list = JSONArray.parseArray(regroups,OrgRegroup.class);
		} catch (Exception e) {
			Assert.throwException("数据格式错误");
		}
		String fdId = "";
		try {
			for (OrgRegroup rg : list) {
				if(StringUtil.isNotNull(rg.getDeptIds())){
					fdId = rg.getId();
					orgDeptMapper.updateBatchByIds(rg.getId(),rg.getDeptIds().split(";"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("保存失败");
		}
		if(StringUtil.isNotNull(fdId)){
			OrgDept entity = super.selectById(fdId);
			asyncTask.dataCorrection(entity.getCompany());
		}
		return "保存成功";
	}

	@Override
	public List<OrgRegroup> findRegroup(String company) {
		Assert.notBlank(company, "公司Code不能为空");
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("company", company);
		wrapper.eq("is_delete", false);
		wrapper.in("type_code", new String[] {TypeConstant.ORG_TX,TypeConstant.ORG_BM});
		List<OrgDept> list = super.selectList(wrapper);
		List<OrgRegroup> orList = list.stream().map(od -> {
			return new OrgRegroup(od.getFdId(),od.getDescrshort(),od.getParentId(),od.getTypeCode());
		}).collect(Collectors.toList());
		List<OrgRegroup> txList = orList.stream().filter(or -> TypeConstant.ORG_TX.equals(or.getTypeCode())).collect(Collectors.toList());
		List<OrgRegroup> bmList = orList.stream().filter(or -> TypeConstant.ORG_BM.equals(or.getTypeCode())).collect(Collectors.toList());
		txList.forEach(tx -> {
			StringBuffer ids = new StringBuffer("");
			StringBuffer names = new StringBuffer("");
			bmList.stream().filter(bj -> tx.getId().equals(bj.getParentId()))
			.forEach(bm -> {
				ids.append(";"+bm.getId());
				names.append(";"+bm.getName());
			});
			if(ids.length()>0&&names.length()>0){
				tx.setDeptIds(ids.toString().substring(1));
				tx.setDeptNames(names.toString().substring(1));
			}
			List<OrgJobDto> jobs = orgJobService.findOrgJobByDeptId(tx.getId(), "1");
			if(jobs!=null&&jobs.size()>0){
				tx.setJosName(jobs.get(0).getJobName());
			}
		});
		return txList;
	}

	@Override
	public Integer updateJobIdByIds(String jobId,String deptIds) throws CustomException {
		Assert.notBlank(deptIds, "部门ID不能为空");
		return orgDeptMapper.updateJobIdByIds(jobId, deptIds.split(";"));
	}

	@Override
	public String findNodeNameByCode(String codes) {
		if(StringUtil.isNull(codes)){
			return "";
		}
		return orgDeptMapper.findNodeNameByCode(codes.split(";"));
	}

	@Override
	public Integer updateDeptVersion(String company,Integer version,Date date) {
		return orgDeptMapper.updateDeptVersion(company,version,date);
	}

	@Override
	public Integer updateJobVersion(String company,Integer version) {
		return orgDeptMapper.updateJobVersion(company,version);
	}

	@Override
	public Integer updateCompanyVersion(String company,Integer version) {
		return orgDeptMapper.updateCompanyVersion(company,version);
	}

	public void deleteByIds(String ids) throws CustomException {
		orgDeptMapper.deleteBatchByIds(ids.split(";"));
	}
	
	private void jobsaveUser(List<OrgExcel> joblist){
		joblist.forEach(jobAll -> {
			if(redisUtil.exists(RedisConstant.ORG_USER_ALL)){
				String obj =  (String)redisUtil.get(RedisConstant.ORG_USER_ALL);
				List<OrgUserJobDto> user = JSONArray.parseArray(obj, OrgUserJobDto.class);
				jobAll.getUser().addAll(user.stream().filter(us -> us.getJobCode().equals(jobAll.getId())).collect(Collectors.toList()));
			}
			
		});
	}
	
	@Override
	public OrgExcel findExcel(String fdId) throws CustomException {
		OrgDept entity = super.selectById(fdId);
		Assert.notNull(entity,"公司不存在");
		OrgExcel gs = new OrgExcel(entity.getFdId(),entity.getDescrshort());
		gs.setCompanyCode(entity.getCompany());
		List<OrgExcel> tx = orgDeptMapper.findDeptByParam(entity.getCompany(), new String[] {TypeConstant.ORG_TX});
		tx = tx.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
		List<OrgExcel> list = orgDeptMapper.findDeptByParam(entity.getCompany(), new String[] {TypeConstant.ORG_BM,TypeConstant.ORG_GC});
		List<OrgExcel> gc = list.stream().filter(oe -> TypeConstant.ORG_GC.equals(oe.getTypeCode())).collect(Collectors.toList());
		List<OrgExcel> bm = list.stream().filter(oe -> TypeConstant.ORG_BM.equals(oe.getTypeCode())).collect(Collectors.toList());;
		bm.addAll(gc);
		List<OrgExcel> joblist = orgJobService.findJobByParam(entity.getCompany());
		this.jobsaveUser(joblist);
		//主要职务
		List<OrgExcel> job = joblist.stream().filter(oe -> TypeConstant.JOB_ZZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//分管职务
		List<OrgExcel> jobs = joblist.stream().filter(oe -> TypeConstant.JOB_FGZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		
		//部门/工厂插入主职位
		for (OrgExcel orgExcel : bm) {
			List<OrgExcel> newjob = job.stream().filter(oe -> oe.getParentId().equals(orgExcel.getId())).collect(Collectors.toList());
			if(newjob!=null&&newjob.size()>0){
				orgExcel.setJob(newjob.get(0).getLabel());
				orgExcel.setUser(newjob.get(0).getUser());
			}
		}
		
		//分管职位插入部门/工厂
		for (OrgExcel orgExcel : jobs) {
			List<OrgExcel> newbm = bm.stream().filter(oe -> orgExcel.getId().equals(oe.getJobId())).collect(Collectors.toList());
			bm.removeAll(newbm);
			if(newbm==null||newbm.size()<1){
				newbm = new ArrayList<>();
				newbm.add(new OrgExcel("12",""));//部门
			}
			orgExcel.setChildren(newbm);
		}
		Boolean isBG = false;
		Boolean isFZC = false;
		Boolean isZJL = false;
		if(tx!=null&&tx.size()>0){
			isBG = true;
			//插入体系
			for (OrgExcel txorg : tx) {
				List<OrgExcel> newjob = job.stream().filter(oe -> oe.getParentId().equals(txorg.getId())).collect(Collectors.toList());
				if(newjob!=null&&newjob.size()>0){
					txorg.setJob(newjob.get(0).getLabel());
					txorg.setUser(newjob.get(0).getUser());
				}
				List<OrgExcel> newjobs = jobs.stream().filter(oe -> oe.getParentId().equals(txorg.getId())).collect(Collectors.toList());
				if(newjobs!=null&&newjobs.size()>0){
					txorg.setChildren(newjobs);
					jobs.removeAll(newjobs);
				}
				List<OrgExcel> txbms = bm.stream().filter(ob -> ob.getParentId().equals(txorg.getId())).collect(Collectors.toList());
				if(!isNullList(txbms)){
					OrgExcel oe = new OrgExcel("23","");//分管职位
					oe.setChildren(txbms);
					bm.removeAll(txbms);
					txorg.getChildren().add(oe);
				}
				if(isNullList(newjobs)&&isNullList(txbms)){
					OrgExcel oe1 = new OrgExcel("23","");// 分管副总
					OrgExcel oe2 = new OrgExcel("12","");// 部门
					oe1.getChildren().add(oe2);
					txorg.getChildren().add(oe1);
				}
			}
			gs.setChildren(tx);
			if(jobs!=null&&jobs.size()>0){
				List<OrgExcel> newjobs = jobs.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
				if(newjobs!=null&&newjobs.size()>0){
					OrgExcel oe1 = new OrgExcel("11","");// 体系
					oe1.setChildren(newjobs);
					gs.getChildren().add(oe1);
				}
			}
			if(bm!=null&&bm.size()>0){
				OrgExcel oe1 = new OrgExcel("11","");// 体系
				OrgExcel oe2 = new OrgExcel("23","");// 分管副总
				oe2.setChildren(bm);
				oe1.getChildren().add(oe2);
				gs.getChildren().add(oe1);
			}
			//插入公司主职位
			List<OrgExcel> newjob = job.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
			if(newjob!=null&&newjob.size()>0){
				gs.setJob(newjob.get(0).getLabel());
				gs.setUser(newjob.get(0).getUser());
			}
		}else{
			//副总裁
			List<OrgExcel> fzcjobs = joblist.stream().filter(oe -> TypeConstant.JOB_FZC.equals(oe.getTypeCode())&&oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
			//总经理
			List<OrgExcel> zjljobs = joblist.stream().filter(oe -> TypeConstant.JOB_ZJL.equals(oe.getTypeCode())&&oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
			isFZC = !isNullList(fzcjobs);
			isZJL = !isNullList(zjljobs);
			//总经理插入分管副总
			for (OrgExcel zjljob : zjljobs) {
				List<OrgExcel> gsfgzw = jobs.stream().filter(oe -> oe.getParentCode().equals(zjljob.getId())).collect(Collectors.toList());
				if(gsfgzw!=null&&gsfgzw.size()>0){
					zjljob.setChildren(gsfgzw);
					jobs.removeAll(gsfgzw);
				}
				List<OrgExcel> newbm = bm.stream().filter(oe -> zjljob.getId().equals(oe.getJobId())).collect(Collectors.toList());
				if(newbm!=null&&newbm.size()>0){
					OrgExcel oe1 = new OrgExcel("23","");// 分管副总
					oe1.setChildren(newbm);
					zjljob.getChildren().add(oe1);
					bm.removeAll(newbm);
				}
				if(isNullList(gsfgzw)&&isNullList(newbm)){
					OrgExcel oe1 = new OrgExcel("23","");// 分管副总
					OrgExcel oe2 = new OrgExcel("12","");// 部门
					oe1.getChildren().add(oe2);
					zjljob.getChildren().add(oe1);
				}
			}
			
			for (OrgExcel fzcjob : fzcjobs) {
				//副总裁插入总经理
				List<OrgExcel> fzczjljobs = zjljobs.stream().filter(oe -> oe.getParentCode().equals(fzcjob.getId())).collect(Collectors.toList());
				if(fzczjljobs!=null&&fzczjljobs.size()>0){
					fzcjob.setChildren(fzczjljobs);
					zjljobs.removeAll(fzczjljobs);
				}
				//副总裁插入分管副总
				List<OrgExcel> fzcfgfz = jobs.stream().filter(oe -> oe.getParentCode().equals(fzcjob.getId())).collect(Collectors.toList());
				if(fzcfgfz!=null&&fzcfgfz.size()>0){
					OrgExcel oe1 = new OrgExcel("27","");// 总经理
					oe1.setChildren(fzcfgfz);
					jobs.removeAll(fzcfgfz);
				}
				List<OrgExcel> newbm = bm.stream().filter(oe -> fzcjob.getId().equals(oe.getJobId())).collect(Collectors.toList());
				if(newbm!=null&&newbm.size()>0){
					OrgExcel oe1 = new OrgExcel("27","");// 总经理
					OrgExcel oe2 = new OrgExcel("23","");// 分管副总
					oe2.setChildren(newbm);
					oe1.getChildren().add(oe2);
					fzcjob.getChildren().add(oe1);
					bm.removeAll(newbm);
				}
				if(fzczjljobs.isEmpty()&&fzcfgfz.isEmpty()&&newbm.isEmpty()){
					OrgExcel oe1 = new OrgExcel("27","");// 总经理
					OrgExcel oe2 = new OrgExcel("23","");// 分管副总
					OrgExcel oe3 = new OrgExcel("12","");// 部门
					oe2.getChildren().add(oe3);
					oe1.getChildren().add(oe2);
					fzcjob.getChildren().add(oe1);
				}
			}
			gs.setChildren(fzcjobs);
			//公司插入主职务
			List<OrgExcel> newjob = job.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
			if(newjob!=null&&newjob.size()>0){
				gs.setJob(newjob.get(0).getLabel());
				gs.setUser(newjob.get(0).getUser());
			}
			
			//公司插入总经理
			if(zjljobs!=null&&zjljobs.size()>0){
				List<OrgExcel> gszjljobs = zjljobs.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
				if(gszjljobs!=null&&gszjljobs.size()>0){
					OrgExcel oe1 = new OrgExcel("26","");// 副总裁
					oe1.setChildren(gszjljobs);
					gs.getChildren().add(oe1);
				}
			}
			//公司插入分管副总
			if(jobs!=null&&jobs.size()>0){
				List<OrgExcel> gsfgfz = jobs.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
				if(gsfgfz!=null&&gsfgfz.size()>0){
					OrgExcel oe1 = new OrgExcel("26","");// 副总裁
					OrgExcel oe2 = new OrgExcel("27","");// 总经理
					oe2.setChildren(gsfgfz);
					oe1.getChildren().add(oe2);
					gs.getChildren().add(oe1);
				}
			}
			//公司插入部门
			if(bm!=null&&bm.size()>0){
				List<OrgExcel> newbm = bm.stream().filter(oe -> gs.getId().equals(oe.getParentId())).collect(Collectors.toList());
				if(newbm!=null&&newbm.size()>0){
					OrgExcel oe1 = new OrgExcel("27","");// 副总裁
					OrgExcel oe2 = new OrgExcel("27","");// 总经理
					OrgExcel oe3 = new OrgExcel("23","");// 分管副总
					oe3.setChildren(newbm);
					oe2.getChildren().add(oe3);
					oe1.getChildren().add(oe2);
					gs.getChildren().add(oe1);
				}
			}
		}
		JSONObject json = new JSONObject();
		json.put("isBG", isBG);
		json.put("isFZC", isFZC);
		json.put("isZJL", isZJL);
		gs.setJson(json.toJSONString());
		return gs;
	}
	
	@Override
	public String findCompanyId(String company){
		Wrapper<OrgDept> wrapper = new CriterionWrapper<OrgDept>(OrgDept.class);
		wrapper.eq("company", company);
		wrapper.eq("type_code", TypeConstant.ORG_GS);
		List<OrgDept> list = super.selectList(wrapper);
		return this.isNullList(list)?"":(String)list.get(0).getFdId();
	}
	
	@Override
	public OrgExcel findDeptExcel(String fdId) throws CustomException {
		OrgDept entity = super.selectById(fdId);
		Assert.notNull(entity,"部门不存在");
		OrgExcel bm = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany()));
		bm.setCompanyCode(entity.getCompany());
		List<OrgExcel> list = orgDeptMapper.findDeptByParam(entity.getCompany(),  new String[] {TypeConstant.ORG_YWZ,TypeConstant.ORG_BJ});
		List<OrgExcel> ywzs = list.stream().filter(oe -> TypeConstant.ORG_YWZ.equals(oe.getTypeCode())&&oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
		List<OrgExcel> joblist = orgJobService.findJobByParam(entity.getCompany());
		this.jobsaveUser(joblist);
		//主要职务
		List<OrgExcel> job = joblist.stream().filter(oe -> TypeConstant.JOB_ZZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//分管职务
		List<OrgExcel> jobs = joblist.stream().filter(oe -> TypeConstant.JOB_FGZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//岗位
		List<OrgExcel> gws = joblist.stream().filter(oe -> TypeConstant.JOB_GW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//倒班岗位
		List<OrgExcel> sdbgws = joblist.stream().filter(oe -> TypeConstant.JOB_DBGW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//顶班岗位
		List<OrgExcel> dbgws = joblist.stream().filter(oe -> TypeConstant.JOB_DBBZ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//班长岗位
		List<OrgExcel> bzjobs = joblist.stream().filter(oe -> TypeConstant.JOB_BZ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//部门分管职位
		List<OrgExcel> bmjobs = jobs.stream().filter(oe -> oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		//部门岗位
		List<OrgExcel> bmgws = joblist.stream().filter(oe -> TypeConstant.JOB_GW.equals(oe.getTypeCode())&&oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		Boolean isClass = false;
		Boolean isBG = false;
		Boolean isFGZW = false;
		Boolean isGW = false;
		//班级插入岗位
		for (OrgExcel bj : bjs) {
			List<OrgExcel> bjgws = gws.stream().filter(oe -> oe.getParentId().equals(bj.getId())).collect(Collectors.toList());
			if(bjgws!=null&&bjgws.size()>0){
				bj.setChildren(bjgws);
				List<OrgExcel> bjzyzz = job.stream().filter(oe -> oe.getParentId().equals(bj.getId())).collect(Collectors.toList());
				if(bjzyzz!=null&&bjzyzz.size()>0){
					bj.setJob(bjzyzz.get(0).getLabel());
					bj.setUser(bjzyzz.get(0).getUser());
				}else{
					bj.setJob("");
				}
			}else{
				List<OrgExcel> ywzsdbgws = sdbgws.stream().filter(oe -> oe.getParentId().equals(bj.getParentId())).collect(Collectors.toList());
				if(ywzsdbgws==null||ywzsdbgws.size()<1){
					OrgExcel oe = new OrgExcel("18","");
					ywzsdbgws.add(oe);
				}
				bj.setChildren(ywzsdbgws);
				List<OrgExcel> bjzyzz = bzjobs.stream().filter(oe -> oe.getParentId().equals(bj.getParentId())).collect(Collectors.toList());
				if(bjzyzz!=null&&bjzyzz.size()>0){
					List<OrgUserJobDto> users  = bjzyzz.get(0).getUser().stream().filter(us -> bj.getCode().equals(us.getClassId())).collect(Collectors.toList());
					if(!isNullList(users)){
						bj.setUser(users);
					}
					bj.setJob(bjzyzz.get(0).getLabel());
				}else{
					bj.setJob("");
				}
			}
		}
		
		for (OrgExcel ywz : ywzs) {
			isBG = true;
			List<OrgExcel> ywzjob = job.stream().filter(oe -> oe.getParentId().equals(ywz.getId())).collect(Collectors.toList());
			if(ywzjob!=null&&ywzjob.size()>0){
				ywz.setJob(ywzjob.get(0).getLabel());
				ywz.setUser(ywzjob.get(0).getUser());
			}else{
				ywz.setJob("");
			}
			
			List<OrgExcel> newbj = bjs.stream().filter(oe -> oe.getParentId().equals(ywz.getId())).collect(Collectors.toList());
			if(newbj!=null&&newbj.size()>0){
				isClass = true;
				ywz.setChildren(newbj);
			}
			
			List<OrgExcel> ywzjobs = dbgws.stream().filter(oe -> oe.getParentId().equals(ywz.getId())&&TypeConstant.JOB_DBBZ.equals(oe.getTypeCode())).collect(Collectors.toList());
			List<OrgExcel> tree = TreeUtil.tree(ywzjobs, "id", "parentCode", "children");
			tree.forEach(yj -> {
				OrgExcel oe1 = new OrgExcel("16","");//班级  16
				OrgExcel oe2 = new OrgExcel("18","");//岗位  18
				if(yj.getChildren().isEmpty()){
					oe1.getChildren().add(oe2);
				}else{
					oe1.setChildren(yj.getChildren());
				}
				oe1.setJob(yj.getLabel());
				oe1.setUser(yj.getUser());
				ywz.getChildren().add(oe1);
			});
			List<OrgExcel> ywzgws = gws.stream().filter(oe -> oe.getParentId().equals(ywz.getId())).collect(Collectors.toList());
			if(ywzgws!=null&&ywzgws.size()>0){
				isGW = true;
				OrgExcel oe1 = new OrgExcel("16","");//班级  16
				oe1.setChildren(ywzgws);
				ywz.getChildren().add(oe1);
			}
			
			if(isNullList(newbj)&&isNullList(ywzjobs)&&isNullList(ywzgws)){
				OrgExcel oe1 = new OrgExcel("16","");//班级  16
				OrgExcel oe2 = new OrgExcel("18","");//岗位  18
				oe1.getChildren().add(oe2);
				ywz.getChildren().add(oe1);
			}
		}
		Boolean isOther = false;
		if(bmjobs.isEmpty()&&bmgws.isEmpty()){
			isOther = true;
		}
		//部门分管职务插入业务组
		for (OrgExcel orgExcel : bmjobs) {
			isFGZW = true;
			List<OrgExcel> newywz = ywzs.stream().filter(oe -> orgExcel.getId().equals(oe.getJobId())).collect(Collectors.toList());
			if(newywz!=null&&newywz.size()>0){
				ywzs.removeAll(newywz);
				orgExcel.setChildren(newywz);
			}
			
			List<OrgExcel> newbj = bjs.stream().filter(oe -> orgExcel.getId().equals(oe.getJobId())).collect(Collectors.toList());
			if(newbj!=null&&newbj.size()>0){
				isClass = true;
				OrgExcel oe1 = new OrgExcel("14","");
				oe1.setChildren(newbj);
				orgExcel.getChildren().add(oe1);
				bjs.removeAll(newbj);
			}
			
			List<OrgExcel> fgxjgw = bmgws.stream().filter(oe -> oe.getParentCode().equals(orgExcel.getId())).collect(Collectors.toList());
			if(fgxjgw!=null&&fgxjgw.size()>0){
				isGW = true;
				OrgExcel oe1 = new OrgExcel("14","");//业务组
				OrgExcel oe2 = new OrgExcel("16","");//班级
				oe2.setChildren(fgxjgw);
				oe1.getChildren().add(oe2);
				orgExcel.getChildren().add(oe1);
				bmgws.removeAll(fgxjgw);
			}
		}
		bm.setChildren(bmjobs);
		//插入部门主要职务
		List<OrgExcel> newjob = job.stream().filter(oe -> oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		if(newjob!=null&&newjob.size()>0){
			bm.setJob(newjob.get(0).getLabel());
			bm.setUser(newjob.get(0).getUser());
		}
		
		List<OrgExcel> bmdbjobs = dbgws.stream().filter(oe -> oe.getParentId().equals(bm.getId())&&TypeConstant.JOB_DBBZ.equals(oe.getTypeCode())).collect(Collectors.toList());
		if(!isNullList(bmdbjobs)){
			isGW = true;
			List<OrgExcel> tree = TreeUtil.tree(bmdbjobs, "id", "parentCode", "children");
			tree.forEach(yj -> {
				OrgExcel oe1 = new OrgExcel("23","");//部门分管职位
				OrgExcel oe2 = new OrgExcel("14","");//业务组
				OrgExcel oe3 = new OrgExcel("16","");//班级  16
				OrgExcel oe4 = new OrgExcel("18","");//岗位  18
				if(yj.getChildren().isEmpty()){
					oe3.getChildren().add(oe4);
				}else{
					oe3.setChildren(yj.getChildren());
				}
				oe3.setJob(yj.getLabel());
				oe3.setUser(yj.getUser());
				oe2.getChildren().add(oe3);
				oe1.getChildren().add(oe2);
				bm.getChildren().add(oe1);
			});
		}
		
		if(bmgws!=null&&bmgws.size()>0){
			isGW = true;
			OrgExcel oe1 = new OrgExcel("23","");//部门分管职位
			OrgExcel oe2 = new OrgExcel("14","");//业务组
			OrgExcel oe3 = new OrgExcel("16","");//班级
			oe3.setChildren(bmgws);
			oe2.getChildren().add(oe3);
			oe1.getChildren().add(oe2);
			bm.getChildren().add(oe1);
		}
		
		if(!isNullList(bjs)){
			List<OrgExcel> bmbjs = bjs.stream().filter(oe -> oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
			if(!isNullList(bmbjs)){
				isClass = true;
				OrgExcel oe1 = new OrgExcel("23","");//部门分管职位
				OrgExcel oe2 = new OrgExcel("14","");//业务组
				oe2.setChildren(bmbjs);
				oe1.getChildren().add(oe2);
				bm.getChildren().add(oe1);
			}
		}
		
		if(ywzs!=null&&ywzs.size()>0){
			OrgExcel bmjob = new OrgExcel("13","");
			bmjob.setChildren(ywzs);
			bm.getChildren().add(bmjob);
		}
		if(isOther&&!isClass&&!isBG&&!isGW){
			OrgExcel oe1 = new OrgExcel("23","");//部门分管职位
			OrgExcel oe2 = new OrgExcel("14","");//业务组
			OrgExcel oe3 = new OrgExcel("16","");//班级
			OrgExcel oe4 = new OrgExcel("18","");//岗位
			oe3.getChildren().add(oe4);
			oe2.getChildren().add(oe3);
			oe1.getChildren().add(oe2);
			bm.getChildren().add(oe1);
		}
		JSONObject json = new JSONObject();
		json.put("companyName", orgCompanyService.findNameByCode(entity.getCompany()));
		json.put("isBG", isBG);
		json.put("isClass", isClass);
		json.put("isFGZW", isFGZW);
		bm.setJson(json.toJSONString());
		return bm;
	}

	@Override
	public OrgExcel findFactoryExcel(String fdId) throws CustomException {
		OrgDept entity = super.selectById(fdId);
		Assert.notNull(entity,"工厂不存在");
		OrgExcel gc = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany()));
		gc.setCompanyCode(entity.getCompany());
		List<OrgExcel> list = orgDeptMapper.findDeptByParam(entity.getCompany(),  new String[] {"15","16","17"});
		
		//车间
		List<OrgExcel> cjs = list.stream().filter(oe -> TypeConstant.ORG_CJ.equals(oe.getTypeCode())&&oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		//班级
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//工段
		List<OrgExcel> gds = list.stream().filter(oe -> TypeConstant.ORG_GD.equals(oe.getTypeCode())).collect(Collectors.toList());
		
		List<OrgExcel> joblist = orgJobService.findJobByParam(entity.getCompany());
		this.jobsaveUser(joblist);
		//主要职务
		List<OrgExcel> zzws = joblist.stream().filter(oe -> TypeConstant.JOB_ZZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//分管职务
		List<OrgExcel> fgzws = joblist.stream().filter(oe -> TypeConstant.JOB_FGZW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//岗位
		List<OrgExcel> gws = joblist.stream().filter(oe -> TypeConstant.JOB_GW.equals(oe.getTypeCode())).collect(Collectors.toList());
		//顶班岗位
		List<OrgExcel> dbgws = joblist.stream().filter(oe -> TypeConstant.JOB_DBBZ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//班长岗位
		List<OrgExcel> bzjobs = joblist.stream().filter(oe -> TypeConstant.JOB_BZ.equals(oe.getTypeCode())).collect(Collectors.toList());
		
		//设备、工艺主管
		List<OrgExcel> sbgyzgs = joblist.stream().filter(oe -> TypeConstant.JOB_SBGY.equals(oe.getTypeCode())&&oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		//设备、工艺协管
		List<OrgExcel> sbgyxgs = joblist.stream().filter(oe -> TypeConstant.JOB_XG.equals(oe.getTypeCode())&&oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		Boolean isFGZW = false;
		//协管插入岗位
		for (OrgExcel sbgyxg : sbgyxgs) {
			sbgyxg.setJob(sbgyxg.getLabel());
			sbgyxg.setLabel("");
			OrgExcel oe1 = new OrgExcel("177","");//工段  17
			OrgExcel oe2 = new OrgExcel("18","");//岗位  18
			List<OrgExcel> sbgyxggw = gws.stream().filter(oe -> sbgyxg.getId().equals(oe.getParentCode())).collect(Collectors.toList());
			if(sbgyxggw!=null&&sbgyxggw.size()>0){
				oe1.setChildren(sbgyxggw);
			}else{
				oe1.getChildren().add(oe2);
			}
			sbgyxg.getChildren().add(oe1);
		}
		//设置、工艺主管插入协管
		for (OrgExcel sbgyzg : sbgyzgs) {
			sbgyzg.setJob(sbgyzg.getLabel());
			sbgyzg.setLabel("");
			OrgExcel oe1 = new OrgExcel("16","");//班级  16
			OrgExcel oe2 = new OrgExcel("17","");//工段  17
			OrgExcel oe3 = new OrgExcel("18","");//岗位  18
			
			List<OrgExcel> sbgyzgxgs = sbgyxgs.stream().filter(oe -> sbgyzg.getId().equals(oe.getParentCode())).collect(Collectors.toList());
			List<OrgExcel> sbgyzggws = gws.stream().filter(oe -> sbgyzg.getId().equals(oe.getParentCode())).collect(Collectors.toList());
			if(sbgyzgxgs!=null&&sbgyzgxgs.size()>0){
				sbgyzg.setChildren(sbgyzgxgs);
			}
			if(sbgyzggws!=null&&sbgyzggws.size()>0){
				oe2.setChildren(sbgyzggws);
				oe1.getChildren().add(oe2);
				sbgyzg.getChildren().add(oe1);
			}
			if(sbgyzgxgs.isEmpty()&&sbgyzggws.isEmpty()){
				oe2.getChildren().add(oe3);
				oe1.getChildren().add(oe2);
				sbgyzg.getChildren().add(oe1);
			}
		}
		
		//工段插入职务
		for (OrgExcel gd : gds) {
			List<OrgExcel> gdgws = gws.stream().filter(oe -> gd.getId().equals(oe.getParentId())).collect(Collectors.toList());
			if(gdgws==null||gdgws.size()<1){
				gdgws = new ArrayList<>();
				gdgws.add(new OrgExcel("0",""));
			}
			gd.setChildren(gdgws);
		}
		//班级插入工段和主职务
		for (OrgExcel bj : bjs) {
			List<OrgExcel> bjgd = gds.stream().filter(oe -> oe.getParentId().equals(bj.getParentId())).collect(Collectors.toList());
			if(bjgd==null||bjgd.size()<1){
				bjgd = new ArrayList<>();
				bjgd.add(new OrgExcel("0",""));
			}
			bj.setChildren(bjgd);
			List<OrgExcel> bjbz = bzjobs.stream().filter(oe -> oe.getParentId().equals(bj.getParentId())).collect(Collectors.toList());
			if(bjbz!=null&&bjbz.size()>0){
				List<OrgUserJobDto> users  = bjbz.get(0).getUser().stream().filter(us -> bj.getCode().equals(us.getClassId())).collect(Collectors.toList());
				if(!isNullList(users)){
					bj.setUser(users);
				}
				bj.setJob(bjbz.get(0).getLabel());
			}else{
				bj.setJob("");
			}
		}
		
		//车间插入班级和主职务
		for (OrgExcel cj : cjs) {
			List<OrgExcel> cjjob = zzws.stream().filter(oe -> cj.getId().equals(oe.getParentId())).collect(Collectors.toList());
			if(cjjob!=null&&cjjob.size()>0){
				cj.setJob(cjjob.get(0).getLabel());
				cj.setUser(cjjob.get(0).getUser());
			}else{
				cj.setJob("");
			}
			
			List<OrgExcel> cjbj = bjs.stream().filter(oe -> oe.getParentId().equals(cj.getId())).collect(Collectors.toList());
			if(cjbj==null||cjbj.size()<1){
				cjbj = new ArrayList<>();
				cjbj.add(new OrgExcel("0",""));
			}
			cj.setChildren(cjbj);
			
			List<OrgExcel> cjdbgw = dbgws.stream().filter(oe -> oe.getParentId().equals(cj.getId())).collect(Collectors.toList());
			List<OrgExcel> tree = TreeUtil.tree(cjdbgw, "id", "parentCode", "children");
			for (OrgExcel dbtree : tree) {
				OrgExcel oe1 = new OrgExcel("16","");//班级  16
				OrgExcel oe2 = new OrgExcel("17","");//工段  17
				OrgExcel oe3 = new OrgExcel("18","");//岗位  18
				oe1.setJob(dbtree.getLabel());
				oe1.setUser(dbtree.getUser());
				if(dbtree.getChildren().isEmpty()){
					oe2.getChildren().add(oe3);
				}else{
					oe2.setChildren(dbtree.getChildren());
				}
				oe1.getChildren().add(oe2);
				cj.getChildren().add(oe1);
			}
			List<OrgExcel> cjgws = gws.stream().filter(oe -> oe.getParentId().equals(cj.getId())).collect(Collectors.toList());
			if(cjgws!=null&&cjgws.size()>0){
				OrgExcel oe1 = new OrgExcel("16","");//班级  16
				OrgExcel oe2 = new OrgExcel("17","");//工段  17
				oe2.setChildren(cjgws);
				oe1.getChildren().add(oe2);
				cj.getChildren().add(oe1);
			}
		}
		
		//工厂插入岗位
		if(sbgyzgs!=null&&sbgyzgs.size()>0){
			OrgExcel oe1 = new OrgExcel("23","");
			oe1.setChildren(sbgyzgs);
			gc.getChildren().add(oe1);
		}
		
		//工厂分管职务插入车间
		List<OrgExcel> gcfgzws = fgzws.stream().filter(oe -> oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		for (OrgExcel gcfgzw : gcfgzws) {
			isFGZW = true;
			List<OrgExcel> gccj = cjs.stream().filter(oe -> oe.getParentId().equals(gcfgzw.getId())).collect(Collectors.toList());
			if(gccj!=null&&gccj.size()>0){
				cjs.removeAll(gccj);
				gcfgzw.setChildren(gccj);
			}else{
				OrgExcel oe1 = new OrgExcel("15","");//车间  15
				OrgExcel oe2 = new OrgExcel("16","");//班级  16
				OrgExcel oe3 = new OrgExcel("17","");//工段  17
				OrgExcel oe4 = new OrgExcel("18","");//岗位  18
				oe3.getChildren().add(oe4);
				oe2.getChildren().add(oe3);
				oe1.getChildren().add(oe2);
				gcfgzw.getChildren().add(oe1);
			}
			gc.getChildren().add(gcfgzw);
		}
		
		if(cjs!=null&&cjs.size()>0){
			OrgExcel fgzw = new OrgExcel("0","");
			fgzw.setChildren(cjs);
			gc.getChildren().add(fgzw);
		}
		
		//插入工厂主要职务
		List<OrgExcel> newjob = zzws.stream().filter(oe -> oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		if(newjob!=null&&newjob.size()>0){
			gc.setJob(newjob.get(0).getLabel());
			gc.setUser(newjob.get(0).getUser());
		}
		JSONObject json = new JSONObject();
		json.put("company", orgCompanyService.findNameByCode(entity.getCompany()));
		json.put("isFGZW", isFGZW);
		gc.setJson(json.toJSONString());
		return gc;
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean isNullList(List list){
		if(list!=null&&list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
