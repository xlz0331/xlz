package com.hwagain.org.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.user.service.IOrgUserJobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hanj
 * @since 2018-04-25
 */
@RestController
@RequestMapping(value="/user/orgUserJob",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="人员岗位信息管理",description="人员岗位信息管理")
public class OrgUserJobController extends BaseController{
	
	@Autowired
	IOrgUserJobService orgUserJobService;
	
	
	/**
	 * 通过岗位id查找在岗人员
	 */
	@RequestMapping("/findByJobCode")
	@ApiOperation(value = "查找岗位在岗人员", notes = "查找岗位在岗人员",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "jobCode",value = "岗位ID", paramType = "query", required = true, dataType = "String")
	})
	public Response findByJobCode(String jobCode){
		return SuccessResponseData.newInstance(orgUserJobService.findByJobCode(jobCode));
	}
}
