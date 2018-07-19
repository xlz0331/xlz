package com.hwagain.org.job.service.impl;

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
import com.hwagain.org.config.entity.OrgNumberConfig;
import com.hwagain.org.config.service.IOrgNumberConfigService;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgJobSelectors;
import com.hwagain.org.job.api.IOrgJobProApi;
import com.hwagain.org.job.dto.OrgJobProDto;
import com.hwagain.org.job.entity.OrgJob;
import com.hwagain.org.job.entity.OrgJobPro;
import com.hwagain.org.job.mapper.OrgJobProMapper;
import com.hwagain.org.job.service.IOrgJobProService;
import com.hwagain.org.register.entity.RegPsJob;
import com.hwagain.org.register.service.IRegPsJobService;
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
 * @since 2018-03-15
 */
@Service("orgJobProService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgJobProServiceImpl extends ServiceImpl<OrgJobProMapper, OrgJobPro> implements IOrgJobProService,IOrgJobProApi {
	
	@Autowired IRegPsJobService regPsJobService;
	
	@Autowired IOrgNumberConfigService orgNumberConfigService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired 
	OrgJobProMapper orgJobProMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgJobPro.class, OrgJobProDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgJobProDto.class, OrgJobPro.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgJobProDto> findJobsByDeptId(String deptId) {
		Wrapper<OrgJobPro> wrapper = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
		wrapper.eq("dept_id", deptId);
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgJobProDto.class);
	}

	@Override
	public List<OrgExcel> findJobByParam(String company) throws CustomException {
		return orgJobProMapper.findJobByParam(company);
	}

	/**
	 * 岗位当前是否未满员
	 */
	@Override
	public Boolean isJobUnfilled(String jobCode) throws CustomException {
		OrgJobProDto job = orgJobProMapper.getJobCurrentNum(jobCode);
		if (job.getCurrentNum()==null || job.getCurrentNum() < job.getMaxPeople()) {
			return true;
		}
		return false;
	}

	@Override
	public List<OrgJobSelectors> findJobByKeywork(String keyword) throws CustomException {
		return orgJobProMapper.findJobByKeywork(null,keyword);
	}

	@Override
	public List<OrgJobSelectors> findJobsByNodeId(String nodeid) throws CustomException {
		Assert.notBlank(nodeid, "节点ID不能为空");
		return orgJobProMapper.findJobByKeywork(nodeid,null);
	}

	@Override
	public List<OrgJobSelectors> findJobsByIds(String jobids) throws CustomException {
		Assert.notBlank(jobids, "参数为空");
		return orgJobProMapper.findJobByJobIds(jobids.split(";"));
	}

	@Override
	public String syncPS() throws CustomException {
		Wrapper<OrgJobPro> wrapper = new CriterionWrapper<OrgJobPro>(OrgJobPro.class);
		List<OrgJobPro> list = super.selectList(wrapper);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int i = 0;
			for (OrgJobPro op : list) {
				i++;
				System.err.println(i);
				ResultMessage result = PsUtils.getPSPosition(op.getJobCode());
				if(result.getStatus()==500){
					RegPsJob regPsJob = regPsJobService.findNameIsExist(op.getJobName());
					String jobcode = "";
					if(regPsJob==null){
						OrgNumberConfig config = orgNumberConfigService.findOneBytype("psjob");
						jobcode = String.valueOf(config.getNumber()+1);
						PsUtils.addPSJobCode(jobcode, sf.parse("2018-04-29"), op.getJobName(), op.getJobName());
						config.setNumber(config.getNumber()+1);
						orgNumberConfigService.updateById(config);
					}else{
						jobcode = regPsJob.getCode();
					}
					OrgJob job = new OrgJob();
					job.setJobCode(op.getJobCode());
					job.setCompany(op.getCompany());
					job.setDeptCode(op.getDeptCode());
					job.setIsDelete(op.getIsDelete());
					job.setIsManage(op.getIsManage());
					job.setMaxCompile(5);
					job.setJobName(op.getJobName());
					PsUtils.addPSPosition(job,sf.parse("2018-04-29"),jobcode);
				}else{
					OrgJob job = new OrgJob();
					job.setJobCode(op.getJobCode());
					job.setCompany(op.getCompany());
					job.setDeptCode(op.getDeptCode());
					job.setIsDelete(op.getIsDelete());
					job.setIsManage(op.getIsManage());
					job.setMaxCompile(5);
					job.setJobName(op.getJobName());
					PsUtils.updatePSPosition(job,sf.parse("2018-04-29"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "同步完成";
	}

}
