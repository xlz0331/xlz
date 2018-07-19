package com.hwagain.org.dept.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.dept.dto.OrgDeptProDto;
import com.hwagain.org.dept.service.IOrgDeptHistoryService;
import com.hwagain.org.dept.service.IOrgDeptProService;

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
@RequestMapping(value="/dept/orgDeptPro",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="部门生产数据管理",description="部门生产数据管理")
public class OrgDeptProController extends BaseController{
	
	@Autowired
	IOrgDeptProService orgDeptProService;
	@Autowired
	IOrgDeptHistoryService orgDeptHistoryService;
	
	/**
	 * 查询全部部门生产数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢部门生产数据列表", notes = "查詢部门生产数据列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgDeptProService.findTreeAll());
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(OrgDeptProDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/dept/orgDeptPro", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询部门生产数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询部门生产数据", notes = "按ID查询部门生产数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "部门生产数据ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(orgDeptProService.findOne(fdId));
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
		return SuccessResponseData.newInstance(orgDeptProService.findExcel(fdId));
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
		return SuccessResponseData.newInstance(orgDeptProService.findDeptExcel(fdId));
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
		return SuccessResponseData.newInstance(orgDeptProService.findFactoryExcel(fdId));
	}
	
	/**
	 * 数据修正
	 * 
	 * @return
	 */
	@RequestMapping("/dataCorrection")
	@ApiOperation(value = "数据修正", notes = "数据修正",httpMethod="POST")
	public Response dataCorrection(){
		return SuccessResponseData.newInstance(orgDeptProService.dataCorrection());
	}
	
	/**
	 * 数据同步到PS
	 * 
	 * @return
	 */
	@RequestMapping("/syncDeptPS")
	@ApiOperation(value = "全量同步组织数据同步到PS", notes = "全量同步组织数据同步到PS",httpMethod="POST")
	public Response syncDeptPS(){
		return SuccessResponseData.newInstance(orgDeptProService.syncDeptPS());
	}
	
	/**
	 * 按公司编号查询根节点
	 * 
	 * @param companycode
	 * @return
	 */
	@RequestMapping(value="/findOrgRootNode",method={RequestMethod.GET})
	@ApiOperation(value = "按工厂ID查找Excel表数据", notes = "按工厂ID查找Excel表数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "companycode", value = "公司编号", paramType = "query", required = false, dataType = "String")
	})
	public Response findOrgRootNode(String companycode){
		return SuccessResponseData.newInstance(orgDeptProService.findOrgRootNode(companycode));
	}
	
	/**
	 * 按公司编号查询根节点
	 * 
	 * @param companycode
	 * @return
	 */
	@RequestMapping(value="/findOrgChildNode",method={RequestMethod.GET})
	@ApiOperation(value = "按工厂ID查找Excel表数据", notes = "按工厂ID查找Excel表数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "nodeid", value = "节点FDID", paramType = "query", required = true, dataType = "String")
	})
	public Response findOrgChildNode(@RequestParam("nodeid")String nodeid){
		return SuccessResponseData.newInstance(orgDeptProService.findOrgChildNode(nodeid));
	}
	
	/**
	 * 按公司编号查询根节点
	 * 
	 * @param companycode
	 * @return
	 */
	@RequestMapping(value="/findOrgOrJobByParam",method={RequestMethod.GET})
	@ApiOperation(value = "模糊查询部门或者职位", notes = "模糊查询部门或者职位",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "查询类型，1：只查部门，2：只查职位，不传默认查全部", paramType = "query", required = false, dataType = "Integer"),
		@ApiImplicitParam(name = "keyword", value = "模糊查询，按节点名称或者编码", paramType = "query", required = false, dataType = "String")
	})
	public Response findOrgOrJobByParam(@RequestParam(name="type",required=false,defaultValue="0")Integer type,String keyword){
		return SuccessResponseData.newInstance(orgDeptProService.findOrgOrJobByParam(type, keyword));
	}
	
	/**
	 * 零时数据同步到历史表
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/syncPro",method={RequestMethod.GET})
	@ApiOperation(value = "历史数据同步到正式表（慎用）", notes = "历史数据同步到正式表（慎用）",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "Integer")
	})
	public Response syncPro(String company,Integer version){
		return SuccessResponseData.newInstance(orgDeptProService.syncPro(company, version));
	}
}
