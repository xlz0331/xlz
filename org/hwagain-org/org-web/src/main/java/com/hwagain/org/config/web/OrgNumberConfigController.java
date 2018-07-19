package com.hwagain.org.config.web;

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
import com.hwagain.org.config.dto.OrgNumberConfigDto;
import com.hwagain.org.config.service.IOrgNumberConfigService;

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
@RequestMapping(value="/config/orgNumberConfig",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="编码初始管理",description="编码初始管理")
public class OrgNumberConfigController extends BaseController{
	
	@Autowired
	IOrgNumberConfigService orgNumberConfigService;
	
	/**
	 * 查询全部编码初始
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢编码初始列表", notes = "查詢编码初始列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgNumberConfigService.findAll(),null,this.getValidation(new OrgNumberConfigDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(OrgNumberConfigDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/config/orgNumberConfig", dto);
		return validURIMaps;
	}
	
	/**
	 * 新增编码初始
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增编码初始", notes = "新增编码初始",httpMethod="POST")
	public Response save(@RequestBody OrgNumberConfigDto dto){
		return SuccessResponseData.newInstance(orgNumberConfigService.save(dto));
	}
	
	/**
	 * 修改编码初始
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改编码初始", notes = "修改编码初始",httpMethod="POST")
	public Response update(@RequestBody OrgNumberConfigDto dto){
		return SuccessResponseData.newInstance(orgNumberConfigService.update(dto));
	}
	
	/**
	 * 删除编码初始，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除编码初始", notes = "删除编码初始",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "编码初始集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		Boolean isOk = orgNumberConfigService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk?"删除成功！":"删除失败！");
	}
}
