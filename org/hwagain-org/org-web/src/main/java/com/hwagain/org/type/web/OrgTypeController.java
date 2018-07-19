package com.hwagain.org.type.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.security.common.util.UserUtils;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.type.dto.OrgTypeDto;
import com.hwagain.org.type.service.IOrgTypeService;

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
@RequestMapping(value="/orgType",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="类型管理",description="类型管理")
public class OrgTypeController extends BaseController{
	
	@Autowired
	IOrgTypeService orgTypeService;
	
	/**
	 * 查询全部类型
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢类型列表", notes = "查詢类型列表",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keyword", value = "名称", paramType = "query", required = false, dataType = "String")
	})
	public Response findAll(String keyword){
		return SuccessResponseData.newInstance(orgTypeService.findAll(keyword));
	}
	
	/**
	 * 新增类型
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增类型", notes = "新增类型",httpMethod="POST")
	public Response save(@RequestBody OrgTypeDto dto){
		return SuccessResponse.newInstance(orgTypeService.save(dto));
	}
	
	/**
	 * 修改类型
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改类型", notes = "修改类型",httpMethod="POST")
	public Response update(@RequestBody OrgTypeDto dto){
		return SuccessResponse.newInstance(orgTypeService.update(dto));
	}
	
	/**
	 * 删除类型，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除类型", notes = "删除类型",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "类型集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		return SuccessResponse.newInstance(orgTypeService.delete(ids));
	}
}
