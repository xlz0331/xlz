package com.hwagain.org.dept.service.impl;

import java.util.ArrayList;
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
import com.hwagain.org.company.service.IOrgCompanyService;
import com.hwagain.org.constant.RedisConstant;
import com.hwagain.org.constant.TypeConstant;
import com.hwagain.org.dept.dto.OrgDeptHistoryDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.entity.OrgDeptHistory;
import com.hwagain.org.dept.mapper.OrgDeptHistoryMapper;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.job.service.IOrgJobHistoryService;
import com.hwagain.org.log.entity.OrgLog;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.user.dto.OrgUserJobDto;

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
@Service("orgDeptHistoryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgDeptHistoryServiceImpl extends ServiceImpl<OrgDeptHistoryMapper, OrgDeptHistory> implements IOrgDeptHistoryService {
	
	private static Logger logger = LoggerFactory.getLogger(OrgDeptHistoryServiceImpl.class);
	
	@Autowired RedisUtil redisUtil;
	
	@Autowired OrgDeptHistoryMapper orgDeptHistoryMapper;
	
	@Autowired IOrgLogService orgLogService;
	
	@Autowired IOrgJobHistoryService orgJobHistoryService;
	
	
	@Autowired IOrgCompanyService orgCompanyService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgDeptHistory.class, OrgDeptHistoryDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgDeptHistoryDto.class, OrgDeptHistory.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgDeptHistoryDto> findAll(String version,String company) {
		Assert.notBlank(version, "版本号不能为空");
		Assert.notBlank(company, "公司编号不能为空");
		List<OrgDeptHistoryDto> dtoList = null;
		try {
			if(redisUtil.exists(RedisConstant.DEPT_HISTORY_ALL_TREE+company+"_"+version)){
				String deptAll =  (String) redisUtil.get(RedisConstant.DEPT_HISTORY_ALL_TREE+company+"_"+version);
				if(StringUtil.isNotNull(deptAll)){
					dtoList = JSONArray.parseArray(deptAll, OrgDeptHistoryDto.class);
					return dtoList;
				}
			}
		} catch (Exception e) {
			logger.info("从redis中查询全部部门数据异常，异常信息："+e.getMessage());
		}
		
		//该公司版本下所有修改记录
		List<OrgLog> loglist = orgLogService.findAllByCompany(company, Integer.valueOf(version));
		
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.eq("version", version);
		wrapper.eq("company", company);
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		List<OrgDeptHistory> list = super.selectList(wrapper);
		dtoList = entityToDtoMapper.mapAsList(list, OrgDeptHistoryDto.class);
		for (OrgDeptHistoryDto dto : dtoList) {
			List<OrgLog> dtolog = loglist.stream().filter(log->log.getOperationBeforeId().indexOf(dto.getFdId())>-1).collect(Collectors.toList());
			if(dtolog!=null&&dtolog.size()>0){
				dto.setLog(JSONObject.toJSONString(dtolog.get(0)));
			}
		}
		List<OrgDeptHistoryDto> list1 = dtoList.stream().filter(dh->!dh.getTypeCode().equals(TypeConstant.ORG_GD)).collect(Collectors.toList());
		List<OrgDeptHistoryDto> list2 = dtoList.stream().filter(dh->dh.getTypeCode().equals(TypeConstant.ORG_GD)).collect(Collectors.toList());
		for (OrgDeptHistoryDto dto : list1) {
			if(dto.getTypeCode().equals(TypeConstant.ORG_BJ)){
				dto.setChildren(list2.stream().filter(hd -> hd.getParentId().equals(dto.getParentId())).collect(Collectors.toList()));
			}else if(dto.getTypeCode().equals(TypeConstant.ORG_RB)){
				dto.setChildren(list2.stream().filter(hd -> hd.getParentId().equals(dto.getFdId())).collect(Collectors.toList()));
			}
		}
		
		dtoList = TreeUtil.tree(list1, "fdId", "parentId", "children");
		if(dtoList!=null&&dtoList.size()>0){
			redisUtil.set(RedisConstant.DEPT_HISTORY_ALL_TREE+company+"_"+version, JSONArray.toJSONString(dtoList));
		}
		return dtoList;
	}

	@Override
	public Integer findMaxVersion(String company) {
		return orgDeptHistoryMapper.findMaxVersion(company);
	}

	@Override
	public Integer saveDeptHistory(String company) {
		return orgDeptHistoryMapper.saveDeptHistory(company);
	}

	@Override
	public Integer saveJobHistory(String company) {
		return orgDeptHistoryMapper.saveJobHistory(company);
	}

	@Override
	public String cleanRedis(String version,String company) throws CustomException {
		
		try {
			redisUtil.remove(RedisConstant.DEPT_HISTORY_ALL_TREE+company+"_"+version);
		} catch (Exception e) {
			return "清除异常";
		}
		return "清除成功";
	}
	
	/**
	 * 插入修改日志
	 * 
	 * @param list
	 * @param joblist
	 * @param oe
	 * @param version
	 */
	private void addNodeLog(List<OrgExcel> list,List<OrgExcel> joblist,OrgExcel oe,Integer version){
		//该公司版本下所有修改记录
		List<OrgLog> loglist = orgLogService.findAllByCompany(oe.getCompanyCode(), version);
		System.err.println(111);
		//公司所有部门/工厂ID
		List<String> orgids = new ArrayList<>();
		orgids.add(oe.getId());
		switch (oe.getTypeCode()) {
		case TypeConstant.ORG_GS:
			orgids.addAll(list.stream().map(OrgExcel::getId).collect(Collectors.toList()));
			break;
		case TypeConstant.ORG_BM : case TypeConstant.ORG_GC:
			orgids.addAll(this.findDeptIds(oe.getId(),version));
			break;
		}
		System.err.println(JSONObject.toJSONString(loglist));
		System.err.println(orgids);
		List<OrgLog> logs = new ArrayList<>();
		logs.addAll(loglist.stream().filter(log->orgids.contains(log.getOperationAfterId())||orgids.contains(log.getOperationBeforeId())).collect(Collectors.toList()));
		
		//取出所有部门/工厂下的所有职位ID集合
		List<String> jobids = joblist.stream().filter(djob->orgids.contains(djob.getParentId())).map(OrgExcel::getDataid).collect(Collectors.toList());
		System.err.println(jobids);
		logs.addAll(loglist.stream().filter(log->jobids.contains(log.getOperationAfterId())||jobids.contains(log.getOperationBeforeId())).collect(Collectors.toList()));
		oe.setLogs(logs);
		
		for (OrgExcel dept : list) {
			logs = new ArrayList<>();
			logs.addAll(loglist.stream().filter(log->log.getOperationBeforeId().indexOf(dept.getId())>-1).collect(Collectors.toList()));
			dept.setLogs(logs);
			if(!this.isNullList(logs)){
				dept.setType(logs.get(0).getType());
			}
		}
	}
	
	@Override
	public OrgExcel findExcel(String fdId,Integer version) throws CustomException {
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.eq("fd_id", fdId);
		wrapper.eq("version", version);
		OrgDeptHistory entity = super.selectOne(wrapper);
		Assert.notNull(entity,"公司不存在");
		OrgExcel gs = new OrgExcel(entity.getFdId(),entity.getDescrshort(),null,entity.getCompany());
		gs.setCompanyCode(entity.getCompany());
		gs.setTypeCode(entity.getTypeCode());
		List<OrgExcel> tx = orgDeptHistoryMapper.findDeptByParam(entity.getCompany(),version, new String[] {TypeConstant.ORG_TX});
		tx = tx.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
		List<OrgExcel> list = orgDeptHistoryMapper.findDeptByParam(entity.getCompany(),version, new String[] {TypeConstant.ORG_BM,TypeConstant.ORG_GC});
		List<OrgExcel> joblist = orgJobHistoryService.findJobByParam(entity.getCompany(),version);
		List<OrgExcel> txgcbmlist = new ArrayList<>();
		txgcbmlist.addAll(tx);
		txgcbmlist.addAll(list);
		//插入修改日志
		addNodeLog(txgcbmlist, joblist, gs, version);
		List<OrgExcel> gc = list.stream().filter(oe -> TypeConstant.ORG_GC.equals(oe.getTypeCode())).collect(Collectors.toList());
		List<OrgExcel> bm = list.stream().filter(oe -> TypeConstant.ORG_BM.equals(oe.getTypeCode())).collect(Collectors.toList());;
		bm.addAll(gc);
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
	public OrgExcel findDeptExcel(String fdId,Integer version) throws CustomException {
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.eq("fd_id", fdId);
		wrapper.eq("version", version);
		OrgDeptHistory entity = super.selectOne(wrapper);
		Assert.notNull(entity,"部门不存在");
		OrgExcel bm = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany(),version));
		bm.setCompanyCode(entity.getCompany());
		bm.setTypeCode(entity.getTypeCode());
		List<OrgExcel> list = orgDeptHistoryMapper.findDeptByParam(entity.getCompany(), version, new String[] {TypeConstant.ORG_YWZ,TypeConstant.ORG_BJ});
		List<OrgExcel> joblist = orgJobHistoryService.findJobByParam(entity.getCompany(), version);
		//插入修改日志
		addNodeLog(list, joblist, bm, version);
		
		List<OrgExcel> ywzs = list.stream().filter(oe -> TypeConstant.ORG_YWZ.equals(oe.getTypeCode())&&oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
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
	
	private List<String> findDeptIds(String fdId,Integer version){
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.like("hierarchy_id", "%"+fdId+"%");
		wrapper.eq("version", version);
		List<OrgDeptHistory> list = super.selectList(wrapper);
		return list.stream().map(OrgDeptHistory::getFdId).collect(Collectors.toList());
	}

	@Override
	public OrgExcel findFactoryExcel(String fdId,Integer version) throws CustomException {
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.eq("fd_id", fdId);
		wrapper.eq("version", version);
		OrgDeptHistory entity = super.selectOne(wrapper);
		Assert.notNull(entity,"工厂不存在");
		OrgExcel gc = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany(),version));
		gc.setCompanyCode(entity.getCompany());
		gc.setTypeCode(entity.getTypeCode());
		List<OrgExcel> list = orgDeptHistoryMapper.findDeptByParam(entity.getCompany(), version, new String[] {"15","16","17"});
		List<OrgExcel> joblist = orgJobHistoryService.findJobByParam(entity.getCompany(),version);
		//插入修改日志
		addNodeLog(list, joblist, gc, version);
		//车间
		List<OrgExcel> cjs = list.stream().filter(oe -> TypeConstant.ORG_CJ.equals(oe.getTypeCode())&&oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		//班级
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//工段
		List<OrgExcel> gds = list.stream().filter(oe -> TypeConstant.ORG_GD.equals(oe.getTypeCode())).collect(Collectors.toList());
		
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
			List<OrgExcel> cjjob = zzws.stream().filter(oe -> oe.getParentId().equals(cj.getId())).collect(Collectors.toList());
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
	
	private String findCompanyId(String company,Integer version){
		Wrapper<OrgDeptHistory> wrapper = new CriterionWrapper<OrgDeptHistory>(OrgDeptHistory.class);
		wrapper.eq("company", company);
		wrapper.eq("version", version);
		wrapper.eq("type_code", TypeConstant.ORG_GS);
		List<OrgDeptHistory> list = super.selectList(wrapper);
		return this.isNullList(list)?"":(String)list.get(0).getFdId();
	}
}
