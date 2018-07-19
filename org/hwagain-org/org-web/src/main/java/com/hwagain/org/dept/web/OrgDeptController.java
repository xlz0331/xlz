package com.hwagain.org.dept.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.annotation.DataSecurityValidation;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.dept.service.IOrgDeptService;

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
@RequestMapping(value="/orgDept",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="部门/体系/工厂等管理",description="部门/体系/工厂等管理")
public class OrgDeptController extends BaseController{
	
	@Autowired
	IOrgDeptService orgDeptService;
	
	/**
	 * 查询全部部门/体系/工厂等
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢部门/体系/工厂等列表", notes = "查詢部门/体系/工厂等列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgDeptService.findAll(),null,this.getValidation(new OrgDeptDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(OrgDeptDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/orgDept", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询部门/体系/工厂等
	 * 
	 * @param dto
	 * @return
	 */
	@DataSecurityValidation
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询部门/体系/工厂等", notes = "按ID查询部门/体系/工厂等",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "部门/体系/工厂等ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(orgDeptService.findOne(fdId));
	}
	
	/**
	 * 新增部门/体系/工厂等
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增部门/体系/工厂等", notes = "新增部门/体系/工厂等",httpMethod="POST")
	public Response save(@RequestBody OrgDeptDto dto){
		return SuccessResponseData.newInstance(orgDeptService.save(dto));
	}
	
	/**
	 * 修改部门/体系/工厂等
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改部门/体系/工厂等", notes = "修改部门/体系/工厂等",httpMethod="POST")
	public Response update(@RequestBody OrgDeptDto dto){
		return SuccessResponseData.newInstance(orgDeptService.update(dto));
	}
	
	/**
	 * 删除部门/体系/工厂等，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除部门/体系/工厂等", notes = "删除部门/体系/工厂等",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "部门/体系/工厂等集", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "说明", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids,String state){
		return SuccessResponse.newInstance(orgDeptService.deleteById(ids,state));
	}
	
	/**
	 * 按节点ID查询子节点，不传默认查询根节点
	 * 
	 * @return
	 */
	@RequestMapping("/findByNodeId")
	@ApiOperation(value = "按节点ID查询子节点，不传默认查询根节点", notes = "按节点ID查询子节点，不传默认查询根节点",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "部门/体系/工厂等ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findByNodeId(String fdId){
		return SuccessResponseData.newInstance(orgDeptService.findByNodeId(fdId));
	}
	
	/**
	 * 选择分管体系、部门、业务组等
	 * 
	 * @return
	 */
	@RequestMapping("/findAllByCompany")
	@ApiOperation(value = "选择分管体系、部门、业务组等", notes = "选择分管体系、部门、业务组等",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "typeCode", value = "部门编号", paramType = "query", required = true, dataType = "String")
	})
	public Response findAllByCompany(String company,String typeCode){
		return SuccessResponseData.newInstance(orgDeptService.findAllByCompany(company, typeCode));
	}
	
	/**
	 * 部门拆分
	 * 
	 * @return
	 */
	@RequestMapping("/splitDept")
	@ApiOperation(value = "部门拆分", notes = "部门拆分",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "要拆分的部门ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "names", value = "拆分后的部门名称，分号分割，最少两个", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "调整说明", paramType = "query", required = false, dataType = "String")
	})
	public Response splitDept(String fdId,String names,String state){
		return SuccessResponseData.newInstance(orgDeptService.splitDept(fdId, names,state));
	}
	
	/**
	 * 根据工段ID查询工段列表
	 * 
	 * @return
	 */
	@RequestMapping("/findDeptParentNode")
	@ApiOperation(value = "根据工段ID查询工段列表", notes = "根据工段ID查询工段列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "工段ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "typeCode", value = "类型编号", paramType = "query", required = false, dataType = "String")
	})
	public Response findDeptParentNode(String fdId,String typeCode){
		return SuccessResponseData.newInstance(orgDeptService.findDeptParentNode(fdId,typeCode));
	}
	
	/**
	 * 数据修正
	 * 
	 * @return
	 */
	@RequestMapping("/dataCorrection")
	@ApiOperation(value = "数据修正", notes = "数据修正",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String")
	})
	public Response dataCorrection(String company){
		return SuccessResponseData.newInstance(orgDeptService.dataCorrection(company));
	}
	
	/**
	 * 部门合并
	 * 
	 * @return
	 */
	@RequestMapping("/mergeDept")
	@ApiOperation(value = "部门合并", notes = "部门合并",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdIds", value = "要合并的部门ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "合并后的名称", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "调整说明", paramType = "query", required = false, dataType = "String")
	})
	public Response mergeDept(String fdIds,String name,String state){
		return SuccessResponseData.newInstance(orgDeptService.mergeDept(fdIds, name,state));
	}
	
	/**
	 * 按ID查询部门/体系/工厂等
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findDeptByIds",method={RequestMethod.GET})
	@ApiOperation(value = "按ID集合查询部门/体系/工厂等", notes = "按ID集合查询部门/体系/工厂等",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "部门/体系/工厂等ID集合", paramType = "query", required = true, dataType = "String")
	})
	public Response findDeptByIds(String ids){
		return SuccessResponseData.newInstance(orgDeptService.findDeptByIds(ids));
	}
	
	/**
	 * 查询体系列表和下级节点
	 * 
	 * @return
	 */
	@RequestMapping("/findRegroup")
	@ApiOperation(value = "查询体系列表和下级节点", notes = "查询体系列表和下级节点",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String")
	})
	public Response findRegroup(String company){
		return SuccessResponseData.newInstance(orgDeptService.findRegroup(company));
	}
	
	/**
	 * 体系、部门重组
	 * 
	 * @return
	 */
	@RequestMapping("/saveBatchByRegroup")
	@ApiOperation(value = "体系、部门重组", notes = "体系、部门重组",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "regroups", value = "json数据", paramType = "query", required = true, dataType = "String")
	})
	public Response saveBatchByRegroup(String regroups){
		return SuccessResponseData.newInstance(orgDeptService.saveBatchByRegroup(regroups));
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
		@ApiImplicitParam(name = "fdId", value = "公司ID", paramType = "query", required = true, dataType = "String")
	})
	public Response findExcel(String fdId){
		return SuccessResponseData.newInstance(orgDeptService.findExcel(fdId));
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
		@ApiImplicitParam(name = "fdId", value = "公司ID", paramType = "query", required = true, dataType = "String")
	})
	public Response findDeptExcel(String fdId){
		return SuccessResponseData.newInstance(orgDeptService.findDeptExcel(fdId));
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
		@ApiImplicitParam(name = "fdId", value = "工厂ID", paramType = "query", required = true, dataType = "String")
	})
	public Response findFactoryExcel(String fdId){
		return SuccessResponseData.newInstance(orgDeptService.findFactoryExcel(fdId));
	}
}
