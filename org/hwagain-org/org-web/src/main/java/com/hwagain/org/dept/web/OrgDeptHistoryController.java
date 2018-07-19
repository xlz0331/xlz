package com.hwagain.org.dept.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;

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
 * @since 2018-03-12
 */
@RestController
@RequestMapping(value="/dept/orgDeptHistory",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="部门历史数据管理",description="部门历史数据管理")
public class OrgDeptHistoryController extends BaseController{
	
	@Autowired
	IOrgDeptHistoryService orgDeptHistoryService;
	
	/**
	 * 查询全部部门历史数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAllByVersion")
	@ApiOperation(value = "查詢部门历史数据列表", notes = "查詢部门历史数据列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "version", value = "版本号1、2、3等", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String")
	})
	public Response findAll(String version,String company){
		return SuccessResponseData.newInstance(orgDeptHistoryService.findAll(version,company));
	}
	
	/**
	 * 查询全部部门历史数据
	 * 
	 * @return
	 */
	@RequestMapping("/cleanRedis")
	@ApiOperation(value = "清除缓存", notes = "清除缓存",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "version", value = "版本号1、2、3等", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String")
	})
	public Response cleanRedis(String version,String company){
		return SuccessResponse.newInstance(orgDeptHistoryService.cleanRedis(version, company));
	}
	
	/**
	 * 按ID查询公司-部门生产数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findExcel",method={RequestMethod.GET})
	@ApiOperation(value = "按公司ID查找Excel表数据", notes = "按公司ID查找Excel表数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "公司ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "Integer")
	})
	public Response findExcel(String fdId,Integer version){
		return SuccessResponseData.newInstance(orgDeptHistoryService.findExcel(fdId,version));
	}
	
	/**
	 * 按ID查询部门-岗位生产数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findDeptExcel",method={RequestMethod.GET})
	@ApiOperation(value = "按部门ID查找Excel表数据", notes = "按部门ID查找Excel表数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "公司ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "Integer")
	})
	public Response findDeptExcel(String fdId,Integer version){
		return SuccessResponseData.newInstance(orgDeptHistoryService.findDeptExcel(fdId,version));
	}
	
	/**
	 * 按ID查询工厂-岗位生产数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findFactoryExcel",method={RequestMethod.GET})
	@ApiOperation(value = "按工厂ID查找Excel表数据", notes = "按工厂ID查找Excel表数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "工厂ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "Integer")
	})
	public Response findFactoryExcel(String fdId,Integer version){
		return SuccessResponseData.newInstance(orgDeptHistoryService.findFactoryExcel(fdId,version));
	}
	
	/**
	 * 零时数据同步到历史表
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/syncHistory",method={RequestMethod.GET})
	@ApiOperation(value = "零时数据同步到历史表（慎用）", notes = "零时数据同步到历史表（慎用）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String")
	})
	public Response syncHistory(String company){
		//同步数据到历史表
		orgDeptHistoryService.saveDeptHistory(company);
		orgDeptHistoryService.saveJobHistory(company);
		return SuccessResponseData.newInstance("同步成功");
	}
}
