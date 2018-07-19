package com.hwagain.org.register.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.register.service.IRegPsJobService;

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
 * @since 2018-06-19
 */
@RestController
@RequestMapping(value="/register/regPsJob",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="数据管理",description="数据管理")
public class RegPsJobController extends BaseController{
	
	@Autowired
	IRegPsJobService regPsJobService;
	
	/**
	 * 获取ps职务列表
	 * @param nameLike
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/listPsJobs",method={RequestMethod.GET})
	@ApiOperation(value = "ps职务列表", notes = "ps职务列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "nameLike", value = "职务名称", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name="page",value="第几页",paramType="query",required=true,dataType="Integer"),
		@ApiImplicitParam(name="pageSize",value="每页条数",paramType="query",required=true,dataType="Integer")
	})
	public Response listPsJobs(String nameLike,int page,int pageSize) {	
		PageVO pageVo = new PageVO();
		pageVo.setPage(page + 1);
		pageVo.setPageSize(pageSize);
		return SuccessResponseData.newInstance(regPsJobService.listPsJobs(nameLike,pageVo));
	}
}
