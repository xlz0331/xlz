package com.hwagain.org.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.user.service.IOrgUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hanj
 * @since 2018-03-17
 */
@RestController
@RequestMapping(value="/user/orgUser",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="数据管理",description="数据管理")
public class OrgUserController extends BaseController{
	
	@Autowired
	IOrgUserService orgUserService;
	
	/**
	 * 更新用户信息到redis
	 * 
	 * @return
	 */
	@RequestMapping("/updateUserRedis")
	@ApiOperation(value = "更新用户信息到redis", notes = "更新用户信息到redis",httpMethod="GET")
	public Response findAll(){
		orgUserService.updateUserRedis();
		return SuccessResponseData.newInstance("更新成功");
	}
	
	/**
	 * 更新用户信息到redis
	 * 
	 * @return
	 */
	@RequestMapping("/findUserAllByDeptId")
	@ApiOperation(value = "测试获取登入用户所属部门的所有用户", notes = "测试获取登入用户所属部门的所有用户",httpMethod="GET")
	public Response findUserAllByDeptId(){
		return SuccessResponseData.newInstance(orgUserService.findUserAllByDeptId());
	}
}
