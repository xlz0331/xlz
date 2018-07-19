package com.hwagain.org.job.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.job.dto.OrgJobDto;
import com.hwagain.org.job.service.IOrgJobService;

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
 * @since 2018-03-15
 */
@RestController
@RequestMapping(value="/orgJob",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="职务管理",description="职务管理")
public class OrgJobController extends BaseController{
	
	@Autowired
	IOrgJobService orgJobService;
	
	/**
	 * 查询全部职务
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢职务列表", notes = "查詢职务列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgJobService.findAll());
	}
	
	/**
	 * 按ID查询职务
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询职务", notes = "按ID查询职务",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "职务ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(orgJobService.findOne(fdId));
	}
	
	/**
	 * 新增职位
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("/save")
	@ApiOperation(value = "新增职位", notes = "新增职位",httpMethod="POST")
	public Response save(@RequestBody OrgJobDto dto){
		return SuccessResponseData.newInstance(orgJobService.saveJob(dto));
	}
	
	/**
	 * 修改职位
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("/update")
	@ApiOperation(value = "修改职位", notes = "修改职位",httpMethod="POST")
	public Response update(@RequestBody OrgJobDto dto){
		return SuccessResponseData.newInstance(orgJobService.updateJob(dto));
	}
	
	/**
	 * 删除职务数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除职务历史数据", notes = "删除职务历史数据",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "职务历史数据集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "说明", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids,String state){
		return SuccessResponse.newInstance(orgJobService.deleteByIds(ids,state));
	}
	/**
	 * 
	 * 按类型查询职位
	 * @param deptId
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/findOrgJobByParam",method={RequestMethod.GET})
	@ApiOperation(value = "按类型查询职位", notes = "按类型查询职位",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "nodeId", value = "节点ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "typeCode", value = "职位类型，分号分割如：1;2;3", paramType = "query", required = true, dataType = "String")
	})
	public Response findOrgJobByParam(String nodeId,String typeCode){
		return SuccessResponseData.newInstance(orgJobService.findOrgJobByParam(nodeId, typeCode));
	}
	
	/**
	 * 
	 * 按类型查询职位
	 * @param deptId
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/findOrgJobByCompany",method={RequestMethod.GET})
	@ApiOperation(value = "按类型查询职位", notes = "按类型查询职位",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编码", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "typeCode", value = "职位类型，分号分割如：1;2;3", paramType = "query", required = true, dataType = "String")
	})
	public Response findOrgJobByCompany(String company,String typeCode){
		return SuccessResponseData.newInstance(orgJobService.findOrgJobByCompany(company, typeCode));
	}
}
