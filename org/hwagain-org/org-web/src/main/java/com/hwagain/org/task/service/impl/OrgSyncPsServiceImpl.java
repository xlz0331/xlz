package com.hwagain.org.task.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.SpringBeanUtil;
import com.hwagain.framework.core.util.StringUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.org.config.entity.OrgVersionAudit;
import com.hwagain.org.config.service.IOrgNumberConfigService;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.dept.dto.OrgPsDataDto;
import com.hwagain.org.dept.entity.OrgDept;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.register.entity.RegPsJob;
import com.hwagain.org.register.service.IRegPsJobService;
import com.hwagain.org.task.dto.OrgSyncPsDto;
import com.hwagain.org.task.entity.OrgSyncPs;
import com.hwagain.org.task.mapper.OrgSyncPsMapper;
import com.hwagain.org.task.service.IOrgSyncPsService;
import com.hwagain.org.util.JDBCUtils;
import com.hwagain.org.util.PsUtils;
import com.hwagain.org.util.ResultMessage;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-05-29
 */
@Service("orgSyncPsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgSyncPsServiceImpl extends ServiceImpl<OrgSyncPsMapper, OrgSyncPs> implements IOrgSyncPsService {
	
	@Autowired IRegPsJobService regPsJobService;
	
	@Autowired IOrgNumberConfigService orgNumberConfigService;
	
	@Autowired IOrgDeptHistoryService orgDeptHistoryService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgSyncPs.class, OrgSyncPsDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgSyncPsDto.class, OrgSyncPs.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public void save(String type, String formType, String dataId, String json,Integer version) throws CustomException {
		Wrapper<OrgSyncPs> wrapper = new CriterionWrapper<OrgSyncPs>(OrgSyncPs.class);
		wrapper.eq("dataId", dataId);
		wrapper.eq("dataType", "0");
		wrapper.eq("version", version);
		OrgSyncPs entity = super.selectOne(wrapper);
		if(entity!=null){
			entity.setDocLastUpdateId(UserUtils.getUserCode());
			entity.setDocLastUpdateTime(new Date());
			entity.setJson(json);
			super.updateById(entity);
		}else{
			entity = new OrgSyncPs(type, formType, dataId, json);
			entity.setVersion(version);
			entity.setDocCreateTime(new Date());
			entity.setDocCreatorId(UserUtils.getUserCode());
			super.insert(entity);
		}
	}

	@Override
	public void deleteSync(String dataid) throws CustomException {
		Wrapper<OrgSyncPs> wrapper = new CriterionWrapper<OrgSyncPs>(OrgSyncPs.class);
		wrapper.eq("dataId", dataid);
		wrapper.eq("dataType", "0");
		OrgSyncPs entity = super.selectOne(wrapper);
		if(entity!=null){
			entity.setDataType("2");
			entity.setDocLastUpdateId(UserUtils.getUserCode());
			entity.setDocLastUpdateTime(new Date());
			super.updateById(entity);
		}
	}

	@Override
	public void sycnPStree(List<OrgSyncPs> synclist,Date effdt) throws CustomException {
		List<OrgPsDataDto> list = JDBCUtils.getPSTreeNode();
		OrgDept dept = null;
		OrgPsDataDto op = null;
		
		if(synclist!=null&&synclist.size()>0){
			for (OrgSyncPs os : synclist) {
				for (int i = 0; i < list.size(); i++) {
					if(StringUtil.isNotNull(os.getJson())){
						dept = JSONObject.parseObject(os.getJson(), OrgDept.class);
						if(list.get(i).getDeptCode().equals(dept.getParentDEPTID())){
							op = new OrgPsDataDto("", dept.getDeptid(), "", "", dept.getParentDEPTID());
							op.setNodeCode(String.valueOf((Long.valueOf(list.get(i).getNodeCode())+Long.valueOf(list.get(i+1).getNodeCode()))/2));
							op.setNodeCodeEnd(String.valueOf(Long.valueOf(list.get(i+1).getNodeCode())-1));
							op.setParentNodeCode(list.get(i).getNodeCode());
							list.add(i+1, op);
//							try {
//								int num = JDBCUtils.savePSTreeNode(op,effdt);
//								if(num>0){
//									os.setDataType("1");
//								}else{
//									os.setDataType("-1");
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//								os.setDataType("-1");
//							}
							os.setDataType("1");
						}
					}else{
						os.setDataType("-1");
					}
				}
				super.updateById(os);
			}
			
			int i = JDBCUtils.savePSTree(effdt,list.size());
			if(i>0){
				for (OrgPsDataDto opd : list) {
					JDBCUtils.savePSTreeNode(opd, effdt);
				}
			}
		}
	}

	@Override
	public void syncPSDept(Integer version,Date effdt) throws CustomException {
		Wrapper<OrgSyncPs> wrapper = new CriterionWrapper<OrgSyncPs>(OrgSyncPs.class);
		wrapper.eq("formType", "dept");
		wrapper.eq("dataType", "0");
		wrapper.eq("version", version);
		List<OrgSyncPs> list = super.selectList(wrapper);
		if(list!=null&&list.size()>0){
			this.disposePSDept(list, effdt);
		}
	}
	
	private void disposePSDept(List<OrgSyncPs> list,Date effdt){

		OrgDeptPro dept = null;
		List<OrgSyncPs> savelist = list.stream().filter(os->"save".equals(os.getType())).collect(Collectors.toList());
		//先修改/删除PS数据
		for (OrgSyncPs os : list) {
			ResultMessage result = new ResultMessage();
			if("delete".equals(os.getType())){
				dept = new OrgDeptPro();
				dept.setEffdt(effdt);
				dept.setFdId(os.getDataId());
				dept.setIsDelete(1);
				result = PsUtils.updatePsDept(dept);
				if(result.getStatus()==200){
					os.setDataType("1");
				}else{
					os.setDataType("-1");
				}
			}else if("update".equals(os.getType())){
				if(StringUtil.isNotNull(os.getJson())){
					dept = JSONObject.parseObject(os.getJson(), OrgDeptPro.class);
					dept.setEffdt(effdt);
					result = PsUtils.updatePsDept(dept);
					if(result.getStatus()==200){
						os.setDataType("1");
					}else{
						os.setDataType("-1");
					}
				}else{
					os.setDataType("-1");
				}
			}else if("save".equals(os.getType())){
				if(StringUtil.isNotNull(os.getJson())){
					dept = JSONObject.parseObject(os.getJson(), OrgDeptPro.class);
					dept.setEffdt(effdt);
					result = PsUtils.addPsDept(dept);
					if(result.getStatus()==200){
						os.setDataType("1");
					}else{
						os.setDataType("-1");
					}
				}else{
					os.setDataType("-1");
				}
			}
			os.setResultXml(result.getResultContent());
			os.setRequestXml(result.getRequestContent());
			super.updateById(os);
		}
		this.sycnPStree(savelist,effdt);
	}

	@Override
	public void syncPSJob(Integer version, Date effdt) throws CustomException {
		Wrapper<OrgSyncPs> wrapper = new CriterionWrapper<OrgSyncPs>(OrgSyncPs.class);
		wrapper.eq("formType", "job");
		wrapper.eq("dataType", "0");
		wrapper.eq("version", version);
		List<OrgSyncPs> list = super.selectList(wrapper);
		if(list!=null&&list.size()>0){
			this.disposePSJob(list, effdt);
		}
	}
	
	private void disposePSJob(List<OrgSyncPs> list,Date effdt){
		OrgJob job = null;
		for (OrgSyncPs os : list) {
			ResultMessage result = new ResultMessage();
			if("delete".equals(os.getType())){
				job = JSONObject.parseObject(os.getJson(), OrgJob.class);
				job.setJobCode(os.getDataId());
				job.setIsDelete(1);
				result = PsUtils.updatePSPosition(job,effdt);
				if(result.getStatus()==200){
					os.setDataType("1");
				}else{
					os.setDataType("-1");
				}
			}else if("update".equals(os.getType())){
				if(StringUtil.isNotNull(os.getJson())){
					job = JSONObject.parseObject(os.getJson(), OrgJob.class);
					result = PsUtils.updatePSPosition(job,effdt);
					if(result.getStatus()==200){
						os.setDataType("1");
					}else{
						os.setDataType("-1");
					}
				}else{
					os.setDataType("-1");
				}
			}else if("save".equals(os.getType())){
				if(StringUtil.isNotNull(os.getJson())){
					
					job = JSONObject.parseObject(os.getJson(), OrgJob.class);
					RegPsJob regPsJob = regPsJobService.findNameIsExist(job.getJobName());
					String jobcode = "";
					if(regPsJob==null){
						OrgNumberConfig config = orgNumberConfigService.findOneBytype("psjob");
						jobcode = String.valueOf(config.getNumber()+1);
						PsUtils.addPSJobCode(jobcode, effdt, job.getJobName(), job.getJobName());
						config.setNumber(config.getNumber()+1);
						orgNumberConfigService.updateById(config);
					}else{
						jobcode = regPsJob.getCode();
					}
					result = PsUtils.addPSPosition(job,effdt,jobcode);
					if(result.getStatus()==200){
						os.setDataType("1");
					}else{
						os.setDataType("-1");
					}
				}else{
					os.setDataType("-1");
				}
			}
			os.setResultXml(result.getResultContent());
			os.setRequestXml(result.getRequestContent());
			super.updateById(os);
		}
	}

	@Override
	public void disposeMaxSyncPSException(String company) throws CustomException {
		Integer version = orgDeptHistoryService.findMaxVersion(company);
		Wrapper<OrgVersionAudit> wrapperOV = new CriterionWrapper<OrgVersionAudit>(OrgVersionAudit.class);
		wrapperOV.eq("version", version);
		IOrgVersionAuditService orgVersionAuditService = SpringBeanUtil.getBean("orgVersionAuditService");
		OrgVersionAudit ov =orgVersionAuditService.selectOne(wrapperOV);
		Assert.notNull(ov,"版本不存在");
		Wrapper<OrgSyncPs> wrapper = new CriterionWrapper<OrgSyncPs>(OrgSyncPs.class);
		wrapper.eq("version", version);
		wrapper.eq("dataType", "-1");
		List<OrgSyncPs> list = super.selectList(wrapper);
		List<OrgSyncPs> deptlist = list.stream().filter(op->"dept".equals(op.getFormType())).collect(Collectors.toList());
		List<OrgSyncPs> joblist = list.stream().filter(op->"job".equals(op.getFormType())).collect(Collectors.toList());
		if(deptlist!=null&&deptlist.size()>0){
			this.disposePSDept(deptlist, ov.getEffectTime());
		}
		if(joblist!=null&&joblist.size()>0){
			this.disposePSJob(joblist, ov.getEffectTime());
		}
	}
}