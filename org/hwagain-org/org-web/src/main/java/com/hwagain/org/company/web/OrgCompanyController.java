package com.hwagain.org.company.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.company.dto.OrgCompanyDto;
import com.hwagain.org.company.service.IOrgCompanyService;

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
@RequestMapping(value="/orgCompany",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="公司管理",description="公司管理")
public class OrgCompanyController extends BaseController{
	
	@Autowired
	IOrgCompanyService orgCompanyService;
	
	/**
	 * 查询全部公司
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢公司列表", notes = "查詢公司列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgCompanyService.findAll(),null,this.getValidation(new OrgCompanyDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(OrgCompanyDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/company/orgCompany", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询公司
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询公司", notes = "按ID查询公司",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "公司ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(orgCompanyService.findOne(fdId));
	}
	
	/**
	 * 新增公司
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增公司", notes = "新增公司",httpMethod="POST")
	public Response save(@RequestBody OrgCompanyDto dto){
		return SuccessResponseData.newInstance(orgCompanyService.save(dto));
	}
	
	/**
	 * 修改公司
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改公司", notes = "修改公司",httpMethod="POST")
	public Response update(@RequestBody OrgCompanyDto dto){
		return SuccessResponseData.newInstance(orgCompanyService.update(dto));
	}
	
	/**
	 * 删除公司，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除公司", notes = "删除公司",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "公司集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		return SuccessResponse.newInstance(orgCompanyService.deleteByIds(ids));
	}
}
