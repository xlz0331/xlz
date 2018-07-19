package com.hwagain.org.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.hwagain.framework.core.exception.CustomException;
import com.hwagain.framework.core.redis.RedisUtil;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.org.constant.RedisConstant;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.user.api.IOrgUserApi;
import com.hwagain.org.user.dto.OrgUserDto;
import com.hwagain.org.user.dto.OrgUserJobDeptDto;
import com.hwagain.org.user.dto.OrgUserJobDto;
import com.hwagain.org.user.entity.OrgUser;
import com.hwagain.org.user.mapper.OrgUserMapper;
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
 * @since 2018-03-17
 */
@Service("orgUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgUserServiceImpl extends ServiceImpl<OrgUserMapper, OrgUser> implements IOrgUserService,IOrgUserApi {
	
	private final static Logger logger = LoggerFactory.getLogger(OrgUserServiceImpl.class);
	
	@Autowired
	private OrgUserMapper orgUserMapper;
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private IOrgDeptProService orgDeptProService;
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgUser.class, OrgUserDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgUserDto.class, OrgUser.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public Boolean isUpdate(List<String> deptCode) throws CustomException {
		if(deptCode==null||deptCode.size()<1){
			return true;
		}
		Wrapper<OrgUser> wrapper = new CriterionWrapper<OrgUser>(OrgUser.class);
		wrapper.eq("status", "1");
		wrapper.in("dept_id", deptCode);
		return super.selectCount(wrapper)>0?false:true;
	}

	@Override
	public List<OrgUserJobDto> findUserAllByJob() {
		return orgUserMapper.findUserAllByJob();
	}

	@Override
	public void updateUserRedis() throws CustomException {
		List<OrgUserJobDto> list = this.findUserAllByJob();
		long stime = System.currentTimeMillis();
		redisUtil.set(RedisConstant.ORG_USER_ALL, JSONArray.toJSONString(list));
		long etime = System.currentTimeMillis();
		logger.info("项目启动自动查询数据库视图，把用户职位信息插入到redis。耗时："+(etime-stime)+"ms");
	}

	@Override
	public List<OrgUserDto> findUserAllByDeptId() throws CustomException {
		Wrapper<OrgUser> wrapper = new CriterionWrapper<OrgUser>(OrgUser.class);
		wrapper.eq("emplid", UserUtils.getUserCode());
		wrapper.ne("status", "0");
		OrgUser user = super.selectOne(wrapper);
		Assert.notNull(user,"用户不存在");
		List<String> deptids = orgDeptProService.findDeptAllById(user.getDeptId());
		wrapper = new CriterionWrapper<OrgUser>(OrgUser.class);
		wrapper.in("dept_id", deptids);
		wrapper.ne("status", "0");
		List<OrgUser> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgUserDto.class);
	}

	@Override
	public List<OrgUserJobDeptDto> findUserAllByDeptId(String deptcode) throws CustomException {
		List<String> deptids = orgDeptProService.findDeptNodeAndNextIds(deptcode);
		return orgUserMapper.findUserJobByDeptIds(deptids);
	}

}
