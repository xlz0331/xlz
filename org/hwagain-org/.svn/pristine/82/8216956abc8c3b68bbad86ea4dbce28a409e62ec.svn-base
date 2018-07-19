package com.hwagain.org.config.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.config.dto.OrgVersionAuditDto;
import com.hwagain.org.config.entity.OrgVersionAudit;
import com.hwagain.org.config.mapper.OrgVersionAuditMapper;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.constant.AuditStatusConstant;
import com.hwagain.org.dept.mapper.OrgDeptHistoryMapper;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.dept.service.IOrgDeptService;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.task.AsyncTask;
import com.hwagain.org.util.JDBCUtils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanj
 * @since 2018-03-14
 */
@Service("orgVersionAuditService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgVersionAuditServiceImpl extends ServiceImpl<OrgVersionAuditMapper, OrgVersionAudit> implements IOrgVersionAuditService {
	
	@Autowired
	private IOrgDeptService orgDeptService;
	
	@Autowired
	private OrgDeptHistoryMapper orgDeptHistoryMapper;
	
	@Autowired
	private IOrgDeptProService orgDeptProService;
	
	@Autowired
	private AsyncTask asyncTask;
	
	@Autowired
	private IOrgLogService orgLogService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgVersionAudit.class, OrgVersionAuditDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgVersionAuditDto.class, OrgVersionAudit.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgVersionAuditDto> findAll(String code) {
		Wrapper<OrgVersionAudit> wrapper = new CriterionWrapper<OrgVersionAudit>(OrgVersionAudit.class);
		wrapper.eq("company", code);
		wrapper.orderBy("version", true);
		List<OrgVersionAudit> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgVersionAuditDto.class);
	}

	@Override
	public void isActive(String company) throws CustomException {
		String[] status = {"1"};
		Wrapper<OrgVersionAudit> wrapper = new CriterionWrapper<OrgVersionAudit>(OrgVersionAudit.class);
		wrapper.in("status", status);
		wrapper.eq("company", company);
		Integer i = super.selectCount(wrapper);
		Assert.isTrue(i<1,"有待审批版本，不能修改！");
	}

	@Override
	public void syncOrg(String company,Integer version) throws CustomException {
		//公司历史数据同步到生产
		Long time = System.currentTimeMillis();
		//备份表
		orgDeptProService.backupCompany(time);
		orgDeptProService.backupDept(time);
		orgDeptProService.backupJob(time);
		
		//同步前先删除相关数据
		orgDeptProService.deleteDeptByCompany(company);
		orgDeptProService.deleteJobByCompany(company);
		
		orgDeptProService.insertDeptByCompany(company, version);
		orgDeptProService.insertJobByCompany(company, version);
	}

	@Override
	public String auditOrg(String fdId,String status) throws CustomException {
		//状态；0：待提交审核，1：已提交审核（待审核），2：已审核并通过(待同步)，3：已审核未通过（不同步），4：已同步完成(已生效)
		Assert.notNull(fdId, "版本ID不能为空");
		Assert.notNull(status, "状态码不能为空");
		OrgVersionAudit entity = super.selectById(fdId);
		if(!AuditStatusConstant.ORG_WAIT_AUDIT.equals(entity.getStatus())){
			Assert.throwException("暂时不能审核");
		}
		if(status.equals(AuditStatusConstant.ORG_WAIT_SYNC)||status.equals(AuditStatusConstant.ORG_NOTPASS_AUDIT)){
			entity.setStatus(status);
		}else{
			Assert.throwException("状态码错误");
		}
		super.updateById(entity);
		if(status.equals(AuditStatusConstant.ORG_WAIT_SYNC)){
			asyncTask.syncPS(entity.getVersion(),entity.getEffectTime());
		}
		return "审核成功";
	}

	@Override
	public String submitAuditOrg(String company,String time) throws CustomException {
		Assert.notBlank(company, "公司编号为空");
		Assert.notBlank(time, "时间为空");
		Date date = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			date = sf.parse(time);
		} catch (Exception e) {
			Assert.throwException("时间格式错误");
		}
		Integer version = orgDeptHistoryMapper.findMaxVersion(company);
		List<OrgLog> loglist = orgLogService.findAllByCompany(company, version+1);
		if(loglist==null||loglist.size()==0){
			Assert.throwException("未修改组织数据，不能提交审核！");
		}
		Wrapper<OrgVersionAudit> wrapper = new CriterionWrapper<OrgVersionAudit>(OrgVersionAudit.class);
		wrapper.eq("version", version);
		wrapper.eq("company", company);
		List<OrgVersionAudit> list = super.selectList(wrapper);
		OrgVersionAudit entity = null;
		if(list!=null&&list.size()>0){
			if(list.size()>1){
				Assert.throwException("数据重复");
			}
			entity = list.get(0);
			if(AuditStatusConstant.ORG_WAIT_AUDIT.equals(entity.getStatus())){
				Assert.throwException("上个版本还未审核，不能提交审核");
			}
		}
		entity = new OrgVersionAudit();
		entity.setCompany(company);
		entity.setEffectTime(date);
		entity.setDocCreatorId(UserUtils.getUserId());
		entity.setDocCreateTime(new Date());
		entity.setStatus(AuditStatusConstant.ORG_WAIT_AUDIT);
		entity.setVersion(version+1);
		super.insert(entity);
		
		
		try {
			//更新当前数据版本号和生效时间
			orgDeptService.updateCompanyVersion(company,version+1);
			orgDeptService.updateDeptVersion(company,version+1,date);
			orgDeptService.updateJobVersion(company,version+1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("同步数据异常");
		}
		try {
			int i = JDBCUtils.submitOrgAuditProcess(entity.getFdId(), company, entity.getVersion());
			if(i<1){
				Assert.throwException("提交OA审批失败");
			}
			
			//更新修改记录状态
			orgLogService.updateLogStatus(version+1);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("提交OA审批异常");
		}
		return "提交审核成功";
	}

	@Override
	public List<OrgVersionAudit> findWaitSyncAll() throws CustomException {
		Wrapper<OrgVersionAudit> wrapper = new CriterionWrapper<OrgVersionAudit>(OrgVersionAudit.class);
		wrapper.eq("status", 2);
		return super.selectList(wrapper);
	}
}
