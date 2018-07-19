package com.hwagain.org.log.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.task.service.IOrgSyncPsService;

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
 * @since 2018-03-13
 */
@RestController
@RequestMapping(value="/log/orgLog",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="日志管理",description="日志管理")
public class OrgLogController extends BaseController{
	
	@Autowired
	IOrgLogService orgLogService;
	
	@Autowired
	private IOrgSyncPsService orgSyncPsService;
	/**
	 * 按公司查询变更记录
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/findListByCompany",method={RequestMethod.GET})
	@ApiOperation(value = "按公司查询变更记录", notes = "按公司查询变更记录",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = false, dataType = "String")
	})
	public Response findListByCompany(String company){
		return SuccessResponseData.newInstance(orgLogService.findListByCompany(company));
	}
	
	/**
	 * 按节点查询修改记录
	 * 
	 * @param deptid
	 * @param version
	 * @return
	 */
	@RequestMapping(value="/findByDeptId",method={RequestMethod.GET})
	@ApiOperation(value = "按节点查询修改记录", notes = "按节点查询修改记录",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptid", value = "节点ID", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本", paramType = "query", required = false, dataType = "Integer")
	})
	public Response findByDeptId(String deptid,Integer version){
		return SuccessResponseData.newInstance(orgLogService.findByDeptId(deptid, version));
	}
	
	@RequestMapping(value="/backoutLog",method={RequestMethod.GET})
	@ApiOperation(value = "撤销记录", notes = "撤销记录",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "记录ID", paramType = "query", required = false, dataType = "String")
	})
	public Response backoutLog(String fdId){
		return SuccessResponseData.newInstance(orgLogService.backoutLog(fdId));
	}
	
	@RequestMapping(value="/disposeMaxSyncPSException",method={RequestMethod.GET})
	@ApiOperation(value = "手动处理同步PS异常记录", notes = "手动处理同步PS异常记录",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = false, dataType = "String")
	})
	public Response disposeMaxSyncPSException(String company){
		orgSyncPsService.disposeMaxSyncPSException(company);
		return SuccessResponseData.newInstance("处理成功");
	}
}
