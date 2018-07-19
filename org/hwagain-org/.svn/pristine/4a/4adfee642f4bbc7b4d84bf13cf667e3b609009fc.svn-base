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
import com.hwagain.org.dept.api.IOrgDeptProApi;
import com.hwagain.org.dept.dto.OrgDeptHistoryDto;
import com.hwagain.org.dept.dto.OrgDeptJobDto;
import com.hwagain.org.dept.dto.OrgDeptProDto;
import com.hwagain.org.dept.dto.OrgExcel;
import com.hwagain.org.dept.dto.OrgJobSelectors;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.mapper.OrgDeptProMapper;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.job.service.IOrgJobProService;
import com.hwagain.org.user.dto.OrgUserJobDto;
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
 * @since 2018-03-12
 */
@Service("orgDeptProService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgDeptProServiceImpl extends ServiceImpl<OrgDeptProMapper, OrgDeptPro> implements IOrgDeptProService,IOrgDeptProApi {
	
	private static Logger logger = LoggerFactory.getLogger(OrgDeptProServiceImpl.class);
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;
	
	@Autowired RedisUtil redisUtil;
	
	@Autowired IOrgJobProService orgJobProService;
	
	@Autowired OrgDeptProMapper orgDeptProMapper;
	
	@Autowired IOrgCompanyService orgCompanyService;
	
	@Autowired
	IOrgDeptHistoryService orgDeptHistoryService;
	
	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgDeptPro.class, OrgDeptProDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgDeptProDto.class, OrgDeptPro.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public OrgDeptProDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), OrgDeptProDto.class);
	}

	@Override
	public List<OrgDeptProDto> findTreeAll() throws CustomException {
		List<OrgDeptProDto> dtoList = null;
		try {
			if(redisUtil.exists(RedisConstant.DEPT_ALL_TREE)){
				String deptAll =  (String) redisUtil.get(RedisConstant.DEPT_ALL_TREE);
				if(StringUtil.isNotNull(deptAll)){
					dtoList = JSONArray.parseArray(deptAll, OrgDeptProDto.class);
					return dtoList;
				}
			}
		} catch (Exception e) {
			logger.info("从redis中查询全部部门数据异常，异常信息："+e.getMessage());
		}
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		dtoList = entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptProDto.class);
		dtoList = TreeUtil.tree(dtoList, "fdId", "parentId", "children");
		if(dtoList!=null&&dtoList.size()>0){
			redisUtil.set(RedisConstant.DEPT_ALL_TREE, JSONArray.toJSONString(dtoList));
		}
		return dtoList;
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
		OrgDeptPro entity = super.selectById(fdId);
		Assert.notNull(entity,"公司不存在");
		OrgExcel gs = new OrgExcel(entity.getFdId(),entity.getDescrshort(),null,entity.getCompany());
		gs.setCompanyCode(entity.getCompany());
		List<OrgExcel> tx = orgDeptProMapper.findDeptByParam(entity.getCompany(), new String[] {TypeConstant.ORG_TX});
		tx = tx.stream().filter(oe -> oe.getParentId().equals(gs.getId())).collect(Collectors.toList());
		List<OrgExcel> list = orgDeptProMapper.findDeptByParam(entity.getCompany(), new String[] {TypeConstant.ORG_BM,TypeConstant.ORG_GC});
		List<OrgExcel> gc = list.stream().filter(oe -> TypeConstant.ORG_GC.equals(oe.getTypeCode())).collect(Collectors.toList());
		List<OrgExcel> bm = list.stream().filter(oe -> TypeConstant.ORG_BM.equals(oe.getTypeCode())).collect(Collectors.toList());;
		bm.addAll(gc);
		List<OrgExcel> joblist = orgJobProService.findJobByParam(entity.getCompany());
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
				System.err.println(txorg.getLabel()+"====");
				System.err.println(txbms.size());
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
	
	private String findCompanyId(String company){
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("company", company);
		wrapper.eq("type_code", TypeConstant.ORG_GS);
		List<OrgDeptPro> list = super.selectList(wrapper);
		return this.isNullList(list)?"":(String)list.get(0).getFdId();
	}
	
	@Override
	public OrgExcel findDeptExcel(String fdId) throws CustomException {
		OrgDeptPro entity = super.selectById(fdId);
		Assert.notNull(entity,"部门不存在");
		OrgExcel bm = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany()));
		bm.setCompanyCode(entity.getCompany());
		List<OrgExcel> list = orgDeptProMapper.findDeptByParam(entity.getCompany(),  new String[] {TypeConstant.ORG_YWZ,TypeConstant.ORG_BJ});
		List<OrgExcel> ywzs = list.stream().filter(oe -> TypeConstant.ORG_YWZ.equals(oe.getTypeCode())&&oe.getParentId().equals(bm.getId())).collect(Collectors.toList());
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
		List<OrgExcel> joblist = orgJobProService.findJobByParam(entity.getCompany());
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
		OrgDeptPro entity = super.selectById(fdId);
		Assert.notNull(entity,"工厂不存在");
		OrgExcel gc = new OrgExcel(entity.getFdId(),entity.getDescrshort(),this.findCompanyId(entity.getCompany()));
		gc.setCompanyCode(entity.getCompany());
		List<OrgExcel> list = orgDeptProMapper.findDeptByParam(entity.getCompany(),  new String[] {"15","16","17"});
		
		//车间
		List<OrgExcel> cjs = list.stream().filter(oe -> TypeConstant.ORG_CJ.equals(oe.getTypeCode())&&oe.getParentId().equals(gc.getId())).collect(Collectors.toList());
		//班级
		List<OrgExcel> bjs = list.stream().filter(oe -> TypeConstant.ORG_BJ.equals(oe.getTypeCode())).collect(Collectors.toList());
		//工段
		List<OrgExcel> gds = list.stream().filter(oe -> TypeConstant.ORG_GD.equals(oe.getTypeCode())).collect(Collectors.toList());
		
		List<OrgExcel> joblist = orgJobProService.findJobByParam(entity.getCompany());
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

	@Override
	public void backupCompany(Long time) {
		
	}

	@Override
	public void backupDept(Long time) {
		//orgDeptProMapper.backupDept(time);
	}

	@Override
	public void backupJob(Long time) {
		//orgDeptProMapper.backupJob(time);
	}

	@Override
	public Integer deleteDeptByCompany(String company) {
		return orgDeptProMapper.deleteDeptByCompany(company);
	}

	@Override
	public Integer deleteJobByCompany(String company) {
		return orgDeptProMapper.deleteJobByCompany(company);
	}

	@Override
	public List<OrgDeptPro> findByIds(String[] ids) throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.in("fd_id", ids);
		wrapper.eq("is_delete", false);
		return super.selectList(wrapper);
	}
	
	@Override
	public String dataCorrection() throws CustomException {
		try {
			List<OrgDeptPro> listAll = new ArrayList<>();
			this.disposeDept(listAll, null);
			if(listAll!=null&&listAll.size()>0){
				for (OrgDeptPro orgDept : listAll) {
					super.updateById(orgDept);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.throwException("数据修正异常");
		}
		return "修正成功";
	}
	
	private void disposeDept(List<OrgDeptPro> listAll,OrgDeptPro dept){
		List<OrgDeptPro> list = this.findByNode(dept!=null?dept.getFdId():null);
		for (int i = 0; i < list.size(); i++) {
			String hId = dept!=null?dept.getHierarchyId()+list.get(i).getFdId()+"x":"x"+list.get(i).getFdId()+"x";
			list.get(i).setHierarchyId(hId);
			String descr = dept!=null?dept.getDescr()+"-"+list.get(i).getDescrshort():list.get(i).getDescrshort();
			list.get(i).setDescr(descr);
			listAll.add(list.get(i));
			disposeDept(listAll,list.get(i));
		}
	}
	
	public List<OrgDeptPro> findByNode(String fdId) throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		if(StringUtil.isNotNull(fdId)){
			wrapper.eq("parent_id", fdId);
		}else{
			wrapper.isNull("parent_id");
		}
		wrapper.eq("is_delete", false);
		wrapper.orderBy("order_value", true);
		return super.selectList(wrapper);
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean isNullList(List list){
		if(list!=null&&list.size()>0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Integer insertDeptByCompany(String company, Integer version) {
		return orgDeptProMapper.insertDeptByCompany(company, version);
	}

	@Override
	public Integer insertJobByCompany(String company, Integer version) {
		return orgDeptProMapper.insertJobByCompany(company, version);
	}

	@Override
	public List<OrgDeptJobDto> findNodeJob(String company) throws CustomException {
		Assert.notBlank(company, "公司编号不能为空");
		List<OrgDeptJobDto> list = orgDeptProMapper.findNodeJobByCompany(company);
		return list;
	}

	@Override
	public String syncDeptPS() throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		List<OrgDeptPro> list = super.selectList(wrapper);
		try {
			for (OrgDeptPro org : list) {
				ResultMessage result = PsUtils.getPsDept(org.getDeptid());
				if(result.getStatus()==500){
					PsUtils.addPsDept(org);
				}else{
					PsUtils.updatePsDept(org);
				}
			}
		} catch (Exception e) {
			logger.error("同步异常，异常信息："+e.getMessage());
		}
		return "同步成功";
	}

	@Override
	public List<String> findDeptAllById(String depid) throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.eq("deptid", depid);
		OrgDeptPro entity = super.selectOne(wrapper);
		Assert.notNull(entity, "部门不存在或者已注销");
		wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.in("fd_id", entity.getHierarchyId().split("x"));
		wrapper.in("type_code", new String[]{TypeConstant.ORG_BM,TypeConstant.ORG_GC});
		entity = super.selectOne(wrapper);
		wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.like("hierarchy_id", "%"+entity.getFdId()+"%");
		List<OrgDeptPro> list = super.selectList(wrapper);
		return list.stream().map(OrgDeptPro::getDeptid).collect(Collectors.toList());
	}

	@Override
	public List<OrgDeptProDto> findOrgRootNode(String companycode) throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		if(StringUtil.isNotNull(companycode)){
			wrapper.eq("company", companycode);
		}
		wrapper.isNull("parent_id");
		wrapper.orderBy("order_value", true);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptProDto.class);
	}

	@Override
	public List<OrgDeptProDto> findOrgChildNode(String nodeid) throws CustomException {
		Assert.notBlank(nodeid, "节点ID不能为空");
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.eq("parent_id", nodeid);
		wrapper.orderBy("order_value", true);
		return entityToDtoMapper.mapAsList(super.selectList(wrapper), OrgDeptProDto.class);
	}

	@Override
	public List<OrgJobSelectors> findOrgOrJobByParam(Integer type, String keyword) throws CustomException {
		List<OrgJobSelectors> list = new ArrayList<>();
		if(StringUtil.isNull(keyword)){
			return list;
		}
		if(type==null){
			type = 0;
		}
		switch (type) {
		case 1:
			findDeptByKeyword(keyword, list);
			break;
		case 2:
			findJobByKeywork(keyword, list);
			break;
		default:
			findDeptByKeyword(keyword, list);
			findJobByKeywork(keyword, list);
			break;
		}
		return list;
	}
	//模糊查询部门数据
	private void findDeptByKeyword(String keyword,List<OrgJobSelectors> list){
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		if(StringUtil.isNotNull(keyword)){
			Wrapper<OrgDeptPro> wrapper1 = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
			wrapper.or(wrapper1.like("descrshort", "%"+keyword+"%").like("deptid", "%"+keyword+"%"));
		}
		List<OrgDeptPro> prolist = super.selectList(wrapper);
		if(prolist!=null&&prolist.size()>0){
			list.addAll(prolist.stream().map(pro -> {
				return new OrgJobSelectors(pro.getFdId(), 
						pro.getDescr(), pro.getDescrshort(), 
						pro.getParentId(), pro.getDeptid(), "dept", "");
			}).collect(Collectors.toList()));
		}
	}
	
	private void findJobByKeywork(String keyword,List<OrgJobSelectors> list){
		list.addAll(orgJobProService.findJobByKeywork(keyword));
	}

	@Override
	public String syncPro(String company, Integer version) {
		Assert.notBlank(company, "公司为空");
		Assert.notNull(version, "版本为空");
		List<OrgDeptHistoryDto> list = orgDeptHistoryService.findAll(String.valueOf(version), company);
		if(isNullList(list)){
			Assert.throwException("公司数据为空");
		}
		//同步前先删除相关数据
		this.deleteDeptByCompany(company);
		this.deleteJobByCompany(company);
		
		this.insertDeptByCompany(company, version);
		this.insertJobByCompany(company, version);
		return "同步成功";
	}

	@Override
	public List<OrgDeptJobDto> findUserNodeJob(String company) throws CustomException {
		Assert.notBlank(company, "公司编号不能为空");
		List<OrgDeptJobDto> list = orgDeptProMapper.findUserNodeJob(company);
		return list;
	}

	@Override
	public List<String> findDeptNodeAndNextIds(String depid) throws CustomException {
		Wrapper<OrgDeptPro> wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.eq("deptid", depid);
		OrgDeptPro entity = super.selectOne(wrapper);
		Assert.notNull(entity, "部门不存在或者已注销");
		wrapper = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapper.eq("is_delete", false);
		wrapper.like("hierarchy_id", "%"+entity.getFdId()+"%");
		List<OrgDeptPro> list = super.selectList(wrapper);
		return list.stream().map(OrgDeptPro::getDeptid).collect(Collectors.toList());
	}
}
