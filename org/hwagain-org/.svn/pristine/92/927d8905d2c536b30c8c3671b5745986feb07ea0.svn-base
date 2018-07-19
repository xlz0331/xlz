package com.hwagain.org.log.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.org.constant.TypeConstant;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.dept.service.IOrgDeptService;
import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.job.entity.OrgJobHistory;
import com.hwagain.org.job.entity.OrgJobPro;
import com.hwagain.org.job.service.IOrgJobHistoryService;
import com.hwagain.org.job.service.IOrgJobProService;
import com.hwagain.org.job.service.IOrgJobService;
import com.hwagain.org.log.dto.OrgCjJobLogDto;
import com.hwagain.org.log.dto.OrgClassJobLogDto;
import com.hwagain.org.log.dto.OrgClassLogDto;
import com.hwagain.org.log.dto.OrgGdJobLogDto;
import com.hwagain.org.log.dto.OrgGdLogDto;
import com.hwagain.org.log.dto.OrgJobLogDto;
import com.hwagain.org.log.dto.OrgLogAuditDto;
import com.hwagain.org.log.dto.OrgLogDto;
import com.hwagain.org.log.dto.OrgYwzJobLogDto;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.mapper.OrgLogMapper;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.task.service.IOrgSyncPsService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-13
 */
@Service("orgLogService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgLogServiceImpl extends ServiceImpl<OrgLogMapper, OrgLog> implements IOrgLogService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired
	private IOrgJobService orgJobService;
	@Autowired
	private IOrgJobProService orgJobProService;
	@Autowired
	private IOrgDeptService orgDeptService;
	@Autowired
	private IOrgDeptProService orgDeptProService;
	@Autowired
	private IOrgJobHistoryService orgJobHistoryService;
	@Autowired
	private IOrgDeptHistoryService orgDeptHistoryService;
	@Autowired IOrgSyncPsService orgSyncPsService;
	
	@Autowired OrgLogMapper orgLogMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgLog.class, OrgLogDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgLogDto.class, OrgLog.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public void save(OrgLog entity) {
		Integer version = orgDeptHistoryService.findMaxVersion(entity.getCompany());
		entity.setVersion((version+1)+"");
		Wrapper<OrgLog> wrapper = new CriterionWrapper<OrgLog>(OrgLog.class);
		wrapper.eq("operation_before_id", entity.getOperationBeforeId());
		wrapper.eq("version", entity.getVersion());
		OrgLog orgLog = super.selectOne(wrapper);
		String remark = "";
		if(orgLog==null){
			if("save".equals(entity.getType())){
				remark = "新增【"+entity.getOperationBefore()+"】";
			}else if("update".equals(entity.getType())){
				remark = "由【"+entity.getOperationBefore()+"】修改为【"+entity.getOperationAfter()+"】";
			}else{
				remark = entity.getRemark();
			}
			entity.setRemark(remark);
			super.insert(entity);
		}else{
			if("delete".equals(entity.getType())){
				super.deleteById(orgLog.getFdId());
			}
			orgLog.setOperationAfter(entity.getOperationAfter());
			if("save".equals(orgLog.getType())){
				remark = "新增【"+orgLog.getOperationBefore()+"】再次修改为【"+entity.getOperationAfter()+"】";
			}else if("update".equals(orgLog.getType())){
				remark = "由【"+orgLog.getOperationBefore()+"】修改为【"+entity.getOperationAfter()+"】";
			}else{
				remark = entity.getRemark();
			}
			orgLog.setRemark(remark);
			orgLog.setState(entity.getState());
			orgLog.setJson(entity.getJson());
			super.updateById(orgLog);
		}
	}

	@Override
	public void update(OrgLog entity) {
		super.updateById(entity);
	}

	@Override
	public JSONObject findListByCompany(String company) {
		JSONObject json = new JSONObject();
		Integer version = orgDeptHistoryService.findMaxVersion(company);
		Wrapper<OrgLog> wrapper = new CriterionWrapper<OrgLog>(OrgLog.class);
		wrapper.eq("company", company);
		wrapper.eq("version", version+1);
		List<OrgLog> list = super.selectList(wrapper);
		//注销组织
		List<OrgLog> orgDelete = list.stream().filter(log->"delete".equals(log.getType())&&"org_dept".equals(log.getOperationTbl())).collect(Collectors.toList());
		//增加组织
		List<OrgLog> orgSave = list.stream().filter(log->"save".equals(log.getType())&&"org_dept".equals(log.getOperationTbl())).collect(Collectors.toList());
		//修改组织
		List<OrgLog> orgUpdate = list.stream().filter(log->"update".equals(log.getType())&&"org_dept".equals(log.getOperationTbl())).collect(Collectors.toList());
		//拆分组织
		List<OrgLog> orgSplit = list.stream().filter(log->"split".equals(log.getType())&&"org_dept".equals(log.getOperationTbl())).collect(Collectors.toList());
		//合并组织
		List<OrgLog> orgMerge = list.stream().filter(log->"merge".equals(log.getType())&&"org_dept".equals(log.getOperationTbl())).collect(Collectors.toList());
		json.put("orgDelete", orgDelete);
		json.put("orgSave", orgSave);
		json.put("orgUpdate", orgUpdate);
		json.put("orgSplit", orgSplit);
		json.put("orgMerge", orgMerge);
		
		List<OrgLog> jobList = list.stream().filter(log->"org_job".equals(log.getOperationTbl())).collect(Collectors.toList());
		List<OrgJobLogDto> jobListDto = jobList.stream().map(job -> {
			OrgJob oj = orgJobService.selectById(job.getOperationBeforeId());
			OrgJobHistory hjob = null;
			String afterparentJobName = null;
			String beforparentJobNmae = null;
			OrgDept dept = null;
			if(oj!=null){
				if(StringUtil.isNotNull(oj.getParentId())){
					afterparentJobName = orgJobService.findOrgJobName(oj.getParentId());
				}
				hjob = orgJobHistoryService.findOne(oj.getFdId(), version);
				if(hjob!=null&&StringUtil.isNotNull(hjob.getParentId())){
					if(oj.getParentId().equals(hjob.getParentId())){
						beforparentJobNmae = afterparentJobName;
					}else{
						beforparentJobNmae = orgJobService.findOrgJobName(hjob.getParentId());
					}
				}
				dept = orgDeptService.selectById(oj.getDeptId());
			}
			return new OrgJobLogDto(job.getFdId(), job.getType(), job.getOrgCode(), job.getOperationBefore(), job.getOperationAfter(), dept.getDescrshort(), afterparentJobName, beforparentJobNmae);
		}).collect(Collectors.toList());
		
		//注销职位
		List<OrgJobLogDto> jobDelete = jobListDto.stream().filter(log->"delete".equals(log.getType())).collect(Collectors.toList());
		//增加职位
		List<OrgJobLogDto> jobSave = jobListDto.stream().filter(log->"save".equals(log.getType())).collect(Collectors.toList());
		//修改职位
		List<OrgJobLogDto> jobUpdate = jobListDto.stream().filter(log->"update".equals(log.getType())).collect(Collectors.toList());
		json.put("jobDelete", jobDelete);
		json.put("jobSave", jobSave);
		json.put("jobUpdate", jobUpdate);
		return json;
	}

	@Override
	public JSONObject findOrgLogByCompany(String company,Integer version) {
		Assert.notBlank(company, "ID为空");
		//所有组织数据
		List<OrgDept> list = orgDeptService.findAllByCompany(company);
		Assert.notEmpty(list,"公司数据不存在！");
		
		//所有职位数据
		List<OrgJob> joblist = orgJobService.findOrgJobByCompany(company);
		
		JSONObject json = new JSONObject();
		//体系数据
		List<OrgDept> txList = list.stream().filter(dept -> TypeConstant.ORG_TX.equals(dept.getTypeCode())).collect(Collectors.toList());
		List<String> txids = txList.stream().map(OrgDept::getFdId).collect(Collectors.toList());
		
		//部门、工厂数据
		List<OrgDept> bmgcList = list.stream().filter(dept -> TypeConstant.ORG_BM.equals(dept.getTypeCode())||TypeConstant.ORG_GC.equals(dept.getTypeCode())).collect(Collectors.toList());
		List<String> bmgcids = bmgcList.stream().map(OrgDept::getFdId).collect(Collectors.toList());
		
		//车间数据
		List<OrgDept> cjList = list.stream().filter(dept -> TypeConstant.ORG_CJ.equals(dept.getTypeCode())).collect(Collectors.toList());
		
		//业务组数据
		List<OrgDept> ywzList = list.stream().filter(dept -> TypeConstant.ORG_YWZ.equals(dept.getTypeCode())).collect(Collectors.toList());
		
		//班级数据
		List<OrgDept> bjList = list.stream().filter(dept -> TypeConstant.ORG_BJ.equals(dept.getTypeCode())||TypeConstant.ORG_RB.equals(dept.getTypeCode())).collect(Collectors.toList());
		
		//班级数据(日班)
		List<OrgDept> rbList = list.stream().filter(dept -> TypeConstant.ORG_RB.equals(dept.getTypeCode())).collect(Collectors.toList());
		
		//工段数据
		List<OrgDept> gdList = list.stream().filter(dept -> TypeConstant.ORG_GD.equals(dept.getTypeCode())).collect(Collectors.toList());
		
		//查询版本改变记录
		Wrapper<OrgLog> log = new CriterionWrapper<OrgLog>(OrgLog.class);
		log.eq("version", version);
		log.eq("company", company);
		List<OrgLog> loglist = super.selectList(log);
		//体系日志
		List<OrgLog> txlogs = loglist.stream().filter(orglog->isExist(txids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
		
		//部门、工厂日志
		List<OrgLog> bmgclogs = loglist.stream().filter(orglog->isExist(bmgcids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
		
		List<OrgLog> txjoblogs = new ArrayList<>();
		//体系职位调整
		for (OrgDept tx : txList) {
			List<String> txjobsids = joblist.stream().filter(job -> job.getDeptId().equals(tx.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
			txjoblogs.addAll(loglist.stream().filter(orglog->isExist(txjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList()));
		}
		
		json.put("txlogs", txlogs);
		json.put("txjoblogs", txjoblogs);
		json.put("bmgclogs", bmgclogs);
		List<OrgLogAuditDto> dtolist = new ArrayList<OrgLogAuditDto>();
		OrgLogAuditDto dto = null;
		for (OrgDept org : bmgcList) {
			dto = new OrgLogAuditDto();
			dto.setName(org.getDescrshort());
			dto.setTypeCode(org.getTypeCode());
			//部门/工厂级职位调整
			List<String> gcjobsids = joblist.stream().filter(job -> job.getDeptId().equals(org.getFdId())&&gcbmJob(job.getJobType())).map(OrgJob::getFdId).collect(Collectors.toList());
			
			dto.setGcjoblogs(loglist.stream().filter(orglog->isExist(gcjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList()));
			
			if(TypeConstant.ORG_GC.equals(org.getTypeCode())){
				//设备、工艺级职位调整
				List<String> sbgyjobsids = joblist.stream().filter(job -> job.getDeptId().equals(org.getFdId())&&sbgyJob(job.getJobType())).map(OrgJob::getFdId).collect(Collectors.toList());
				dto.setSbgyjoblogs(loglist.stream().filter(orglog->isExist(sbgyjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList()));
				
				//车间级调整
				List<OrgDept> cjs = cjList.stream().filter(cj -> cj.getParentId().equals(org.getFdId())).collect(Collectors.toList());
				List<String> cjids = cjs.stream().map(OrgDept::getFdId).collect(Collectors.toList());
				dto.setCjlogs(loglist.stream().filter(orglog->isExist(cjids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList()));
				List<OrgClassLogDto> classdtolist = new ArrayList<>();
				List<OrgGdLogDto> gddtolist = new ArrayList<>();
				List<OrgGdJobLogDto> gdjobdtolist = new ArrayList<>();
				List<OrgCjJobLogDto> cjjobdtolist = new ArrayList<>();
				OrgClassLogDto classdto = null;
				OrgGdLogDto gddto = null;
				OrgGdJobLogDto gdjobdto = null;
				OrgCjJobLogDto cjjobdto = null;
				for (OrgDept cj : cjs) {
					classdto = new OrgClassLogDto(); 
					classdto.setParentName(cj.getDescrshort());
					
					//车间职位调整
					cjjobdto = new OrgCjJobLogDto();
					cjjobdto.setParentName(cj.getDescrshort());
					List<String> cjjobsids = joblist.stream().filter(job -> job.getDeptId().equals(cj.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
					List<OrgLog> cjlogs =loglist.stream().filter(orglog->isExist(cjjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(cjlogs!=null&&cjlogs.size()>0){
						cjjobdto.setLogs(cjlogs);
						cjjobdtolist.add(cjjobdto);
					}
					//班级调整
					List<String> bjids = bjList.stream().filter(bj -> bj.getParentId().equals(cj.getFdId())).map(OrgDept::getFdId).collect(Collectors.toList());
					List<OrgLog> bjlogs = loglist.stream().filter(orglog->isExist(bjids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(!bjlogs.isEmpty()){
						classdto.setLogs(bjlogs);
						classdtolist.add(classdto);
					}
					gddto = new OrgGdLogDto();
					gddto.setParentName(cj.getDescrshort());
					//工段调整
					List<OrgDept> gds = gdList.stream().filter(gd -> gd.getParentId().equals(cj.getFdId())).collect(Collectors.toList());
					List<String> gdids = gds.stream().map(OrgDept::getFdId).collect(Collectors.toList());
					List<OrgLog> gdlogs = loglist.stream().filter(orglog->isExist(gdids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(!gdlogs.isEmpty()){
						gddto.setLogs(gdlogs);
						gddtolist.add(gddto);
					}
					//工段职位调整
					for (OrgDept gd : gds) {
						gdjobdto = new OrgGdJobLogDto();
						gdjobdto.setParentName(gd.getDescrshort());
						List<String> gdjobsids = joblist.stream().filter(job -> job.getDeptId().equals(gd.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
						List<OrgLog> gdjoblogs = loglist.stream().filter(orglog->isExist(gdjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
						if(gdjoblogs!=null&&gdjoblogs.size()>0){
							gdjobdto.setLogs(gdjoblogs);
							gdjobdtolist.add(gdjobdto);
						}
					}
					
					//日班工段调整
					List<OrgDept> rbs = rbList.stream().filter(bj -> bj.getParentId().equals(cj.getFdId())).collect(Collectors.toList());
					for (OrgDept rb : rbs) {
						gddto = new OrgGdLogDto();
						gddto.setParentName(rb.getDescrshort());
						//班级调整
						List<String> rbgdids = gdList.stream().filter(gd -> gd.getParentId().equals(rb.getFdId())).map(OrgDept::getFdId).collect(Collectors.toList());
						List<OrgLog> rbgdlogs = loglist.stream().filter(orglog->isExist(rbgdids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
						if(!rbgdlogs.isEmpty()){
							gddto.setLogs(rbgdlogs);
							gddtolist.add(gddto);
						}
					}
				}
				dto.setCjjoblos(cjjobdtolist);
				dto.setGdlogs(gddtolist);
				dto.setGdjoblogs(gdjobdtolist);
				dto.setBjlogs(classdtolist);
			}else if(TypeConstant.ORG_BM.equals(org.getTypeCode())){
				//业务组调整
				List<OrgDept> ywzs = ywzList.stream().filter(ywz -> ywz.getParentId().equals(org.getFdId())).collect(Collectors.toList());
				List<String> ywzsids = ywzs.stream().map(OrgDept::getFdId).collect(Collectors.toList());
				if(ywzsids!=null&&ywzsids.size()>0){
					dto.setYwzlogs(loglist.stream().filter(orglog->isExist(ywzsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList()));
				}
				List<OrgYwzJobLogDto> ywzjobloglist = new ArrayList<>();
				List<OrgClassJobLogDto> classjobloglist = new ArrayList<>();
				OrgClassLogDto calsslogs = null;
				OrgYwzJobLogDto ywzjoblog = null;
				OrgClassJobLogDto classjoblog = null;
				//业务组下班级调整
				for (OrgDept ywz : ywzs) {
					calsslogs = new OrgClassLogDto();
					calsslogs.setParentName(ywz.getDescrshort());
					List<OrgDept> bjs = bjList.stream().filter(bj -> bj.getParentId().equals(ywz.getFdId())).collect(Collectors.toList());
					List<String> bjids = bjs.stream().map(OrgDept::getFdId).collect(Collectors.toList());
					List<OrgLog> bjlogs = loglist.stream().filter(orglog->isExist(bjids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(bjlogs!=null&&bjlogs.size()>0){
						calsslogs.setLogs(bjlogs);
						dto.getBjlogs().add(calsslogs);
					}
					
					for (OrgDept bj : bjs) {
						classjoblog = new OrgClassJobLogDto();
						classjoblog.setParentName(bj.getDescrshort());
						classjoblog.setTypeCode(bj.getTypeCode());
						List<String> bjjobsids = joblist.stream().filter(job -> job.getDeptId().equals(bj.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
						List<OrgLog> bjjoblogs = loglist.stream().filter(orglog->isExist(bjjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
						if(bjjoblogs!=null&&bjjoblogs.size()>0){
							classjoblog.setLogs(bjjoblogs);
							classjobloglist.add(classjoblog);
						}
					}
					
					//业务组职位调整
					ywzjoblog = new OrgYwzJobLogDto();
					ywzjoblog.setParentName(ywz.getDescrshort());
					List<String> ywzjobsids = joblist.stream().filter(job -> job.getDeptId().equals(ywz.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
					List<OrgLog> ywzjoblogs = loglist.stream().filter(orglog->isExist(ywzjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(ywzjoblogs!=null&&ywzjoblogs.size()>0){
						ywzjoblog.setLogs(ywzjoblogs);
						ywzjobloglist.add(ywzjoblog);
					}
				}
				
				//部门下直接班级
				List<OrgDept> bjs = bjList.stream().filter(bj -> bj.getParentId().equals(org.getFdId())).collect(Collectors.toList());
				List<String> bjids = bjs.stream().map(OrgDept::getFdId).collect(Collectors.toList());
				calsslogs = new OrgClassLogDto();
				calsslogs.setParentName(org.getDescrshort());
				List<OrgLog> bjlogs = loglist.stream().filter(orglog->isExist(bjids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
				if(bjlogs!=null&&bjlogs.size()>0){
					calsslogs.setLogs(bjlogs);
					dto.getBjlogs().add(calsslogs);
				}
				
				for (OrgDept bj : bjs) {
					classjoblog = new OrgClassJobLogDto();
					classjoblog.setParentName(bj.getDescrshort());
					classjoblog.setTypeCode(bj.getTypeCode());
					List<String> bjjobsids = joblist.stream().filter(job -> job.getDeptId().equals(bj.getFdId())).map(OrgJob::getFdId).collect(Collectors.toList());
					List<OrgLog> bjjoblogs = loglist.stream().filter(orglog->isExist(bjjobsids, orglog.getOperationBeforeId(),orglog.getOperationAfterId())).collect(Collectors.toList());
					if(bjjoblogs!=null&&bjjoblogs.size()>0){
						classjoblog.setLogs(bjjoblogs);
						classjobloglist.add(classjoblog);
					}
				}
				
				dto.setBjjoblogs(classjobloglist);
				dto.setYwzjoblogs(ywzjobloglist);
			}
			if(isNullList(dto.getGcjoblogs())&&isNullList(dto.getSbgyjoblogs())&&isNullList(dto.getCjlogs())&&isNullList(dto.getCjjoblos())&&isNullList(dto.getBjlogs())&&isNullList(dto.getBjjoblogs())&&isNullList(dto.getYwzlogs())&&isNullList(dto.getYwzjoblogs())&&isNullList(dto.getGdlogs())&&isNullList(dto.getGdjoblogs())){
				continue;
			}
			dtolist.add(dto);
		}
		json.put("list", dtolist);
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean isNullList(List list){
		if(list!=null&&list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	private Boolean isExist(List<String> ids,String beforids,String afterids){
		
		List<String> strids = new ArrayList<>();
		if(StringUtil.isNotNull(beforids)){
			String[] beforid = beforids.split(";");
			strids.addAll(Arrays.asList(beforid));
		}
		
		if(StringUtil.isNotNull(afterids)){
			String[] afterid = afterids.split(";");
			strids.addAll(Arrays.asList(afterid));
		}
		int i = 0;
		for (String string : strids) {
			for (String str : ids) {
				if(string.equals(str)){
					i++;
				}
			}
		}
		if(i>0){
			return true;
		}
		return false;
	}
	
	private Boolean gcbmJob(String typeCode){
		switch (typeCode) {
		case TypeConstant.JOB_ZZW:
			return true;
		case TypeConstant.JOB_FGZW:
			return true;
		default:
			return false;
		}
	}
	
	private Boolean sbgyJob(String typeCode){
		switch (typeCode) {
		case TypeConstant.JOB_SBGY:
			return true;
		case TypeConstant.JOB_XG:
			return true;
		case TypeConstant.JOB_GW:
			return true;
		default:
			return false;
		}
	}

	@Override
	public OrgLog findByDeptId(String deptid,Integer version) throws CustomException {
		Wrapper<OrgLog> wrapper = new CriterionWrapper<OrgLog>(OrgLog.class);
		wrapper.like("operation_before_id", deptid);
		wrapper.eq("version", version);
		wrapper.orderBy("doc_create_time", false);
		List<OrgLog> list = super.selectList(wrapper);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<OrgLog> findAllByCompany(String company, Integer version) throws CustomException {
		Wrapper<OrgLog> wrapper = new CriterionWrapper<OrgLog>(OrgLog.class);
		wrapper.eq("company", company);
		wrapper.eq("version", version);
		List<OrgLog> list = super.selectList(wrapper);
		return list;
	}

	@Override
	public String backoutLog(String fdId) throws CustomException {
		Assert.notBlank(fdId,"撤销日志ID不能为空");
		OrgLog entity = super.selectById(fdId);
		Assert.notNull(entity,"撤销日志为空");
		
		if(entity.getStatus().equals("1")){
			Assert.throwException("数据已处理，不能操作！");
		}
		switch (entity.getType()) {
		case "save":
			if("org_dept".equals(entity.getOperationTbl())){
				//删除节点
				orgDeptService.deleteById(fdId, "撤销");
			}else if("org_job".equals(entity.getOperationTbl())){
				orgJobService.deleteByIds(fdId, "撤销");
			}
			break;
		case "update":
			if("org_dept".equals(entity.getOperationTbl())){
				OrgDept dept = new OrgDept();
				dept.setFdId(entity.getOperationAfterId());
				dept.setDescrshort(entity.getOperationAfter());
				orgDeptService.updateById(dept);
			}else if("org_job".equals(entity.getOperationTbl())){
				OrgJob job = new OrgJob();
				job.setFdId(entity.getOperationAfterId());
				job.setJobName(entity.getOperationAfter());
				orgJobService.updateById(job);
			}
			break;
		case "delete":
			if("org_dept".equals(entity.getOperationTbl())){
				OrgDept dept = new OrgDept();
				dept.setFdId(entity.getOperationAfterId());
				dept.setIsDelete(0);
				orgDeptService.updateById(dept);
			}else if("org_job".equals(entity.getOperationTbl())){
				OrgJob job = new OrgJob();
				job.setFdId(entity.getOperationAfterId());
				job.setIsDelete(0);
				orgJobService.updateById(job);
			}
			break;
		case "merge":
			{
				String[] ids = entity.getOperationBeforeId().split(";");
				OrgDept dept = new OrgDept();
				dept.setFdId(entity.getOperationAfterId());
				dept.setIsDelete(1);
				orgDeptService.updateById(dept);
				for (String id : ids) {
					dept = new OrgDept();
					dept.setFdId(id);
					dept.setIsDelete(0);
					orgDeptService.updateById(dept);
				}
				Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
				wrapper.in("parent_id", ids);
				List<OrgDeptPro> list = orgDeptProService.selectList(wrapper);
				List<OrgDept> entitylist = list.stream().map(pro -> {
					OrgDept org = new OrgDept();
					org.setFdId(pro.getFdId());
					org.setParentId(pro.getParentId());
					org.setParentDEPTID(pro.getParentDEPTID());
					return org;
				}).collect(Collectors.toList());
				entitylist.forEach(org -> {
					orgDeptService.updateById(org);
				});
				
				Wrapper<OrgJobPro> wrapper1 = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
				wrapper1.in("dept_id", ids);
				List<OrgJobPro> list1 = orgJobProService.selectList(wrapper1);
				List<OrgJob> joblist = list1.stream().map(job -> {
					OrgJob oj = new OrgJob();
					oj.setFdId(job.getFdId());
					oj.setDeptId(job.getDeptId());
					oj.setDeptCode(job.getDeptCode());
					return oj;
				}).collect(Collectors.toList());
				joblist.forEach(job -> {
					orgJobService.updateById(job);
				});
			}
			break;
		case "split":
			{
				String[] ids = entity.getOperationAfterId().split(";");
				OrgDept dept = new OrgDept();
				dept.setFdId(entity.getOperationBeforeId());
				dept.setIsDelete(0);
				orgDeptService.updateById(dept);
				for (String id : ids) {
					dept = new OrgDept();
					dept.setFdId(id);
					dept.setIsDelete(1);
					orgDeptService.updateById(dept);
				}
				Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
				wrapper.in("parent_id", entity.getOperationBeforeId());
				List<OrgDeptPro> list = orgDeptProService.selectList(wrapper);
				List<OrgDept> entitylist = list.stream().map(pro -> {
					OrgDept org = new OrgDept();
					org.setFdId(pro.getFdId());
					org.setParentId(pro.getParentId());
					org.setParentDEPTID(pro.getParentDEPTID());
					return org;
				}).collect(Collectors.toList());
				entitylist.forEach(org -> {
					orgDeptService.updateById(org);
				});
				
				Wrapper<OrgJobPro> wrapper1 = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
				wrapper1.in("dept_id", entity.getOperationBeforeId());
				List<OrgJobPro> list1 = orgJobProService.selectList(wrapper1);
				List<OrgJob> joblist = list1.stream().map(job -> {
					OrgJob oj = new OrgJob();
					oj.setFdId(job.getFdId());
					oj.setDeptId(job.getDeptId());
					oj.setDeptCode(job.getDeptCode());
					return oj;
				}).collect(Collectors.toList());
				joblist.forEach(job -> {
					orgJobService.updateById(job);
				});
			}
			break;
		}
		//删除操作记录
		super.deleteById(fdId);
		//废除同步记录
		orgSyncPsService.deleteSync(fdId);
		return "撤销成功";
	}

	@Override
	public Integer updateLogStatus(Integer version) throws CustomException {
		return orgLogMapper.updateLogStatus(version);
	}
}
