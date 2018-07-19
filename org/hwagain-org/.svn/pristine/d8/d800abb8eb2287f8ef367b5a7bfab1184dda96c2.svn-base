package com.hwagain.org.job.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.org.job.service.IOrgJobProService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *  <p>
 *  前端控制器
 * </p>
 * 
 * @author wangct
 *
 */

@RestController
@RequestMapping(value="/orgJobPro",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="职务生产数据管理",description="职务生产数据管理")
public class OrgJobProController {

	@Autowired
	IOrgJobProService orgJobProService;
	
	
	
	@RequestMapping(value="/isJobUnfilled",method={RequestMethod.GET})
	@ApiOperation(value = "当前岗位是否未满员", notes = "当前岗位是否未满员",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "jobCode", value = "岗位编码", paramType = "query", required = true, dataType = "String"),
	})
	public Response isJobUnfilled(String jobCode){
		return SuccessResponseData.newInstance(orgJobProService.isJobUnfilled(jobCode));
	}
	
	@RequestMapping(value="/findJobsByDeptId",method={RequestMethod.GET})
	@ApiOperation(value = "按部门ID查询岗位", notes = "按部门ID查询岗位",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", required = true, dataType = "String"),
	})
	public Response findJobsByDeptId(String deptId){
		return SuccessResponseData.newInstance(orgJobProService.findJobsByDeptId(deptId));
	}
	
	@RequestMapping(value="/findJobsByNodeId",method={RequestMethod.GET})
	@ApiOperation(value = "按部门ID查询岗位（岗位选择器）", notes = "按部门ID查询岗位（岗位选择器）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "nodeid", value = "部门ID", paramType = "query", required = true, dataType = "String"),
	})
	public Response findJobsByNodeId(String nodeid){
		return SuccessResponseData.newInstance(orgJobProService.findJobsByNodeId(nodeid));
	}
	
	@RequestMapping(value="/findJobsByIds",method={RequestMethod.GET})
	@ApiOperation(value = "按职位ID集合查询职位", notes = "按职位ID集合查询职位",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "jobids", value = "职位ID，分号分割。如：1;2;3", paramType = "query", required = true, dataType = "String"),
	})
	public Response findJobsByIds(String jobids){
		return SuccessResponseData.newInstance(orgJobProService.findJobsByIds(jobids));
	}
	
	@RequestMapping(value="/syncPS",method={RequestMethod.GET})
	@ApiOperation(value = "全量同步职位数据到PS系统", notes = "全量同步职位数据到PS系统",httpMethod="GET")
	public Response syncPS(){
		return SuccessResponse.newInstance(orgJobProService.syncPS());
	}
}
