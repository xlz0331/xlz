package com.hwagain.org.register.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.register.dto.RegPersonalEmployWayDto;
import com.hwagain.org.register.service.IRegPersonalEmployWayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  入职注册-求职途径 前端控制器
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@RestController
@RequestMapping(value="/register/regPersonalEmployWay",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="入职注册-求职途径",description="入职注册-求职途径")
public class RegPersonalEmployWayController extends BaseController{
	
	@Autowired
	IRegPersonalEmployWayService regPersonalEmployWayService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(regPersonalEmployWayService.findAll(),null,this.getValidation(new RegPersonalEmployWayDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(RegPersonalEmployWayDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/register/regPersonalEmployWay", dto);
		return validURIMaps;
	}
	
	/**
	 * 按ID查询数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/findOne",method={RequestMethod.GET})
	@ApiOperation(value = "按ID查询数据", notes = "按ID查询数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "数据ID", paramType = "query", required = false, dataType = "String")
	})
	public Response findOne(String fdId){
		return SuccessResponseData.newInstance(regPersonalEmployWayService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	//@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增数据", notes = "新增数据",httpMethod="POST")
	public Response save(@RequestBody RegPersonalEmployWayDto dto){
		return SuccessResponseData.newInstance(regPersonalEmployWayService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改数据", notes = "修改数据",httpMethod="POST")
	public Response update(@RequestBody RegPersonalEmployWayDto dto){
		return SuccessResponseData.newInstance(regPersonalEmployWayService.update(dto));
	}
	
	/**
	 * 删除数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	//@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除数据", notes = "删除数据",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		Boolean isOk = regPersonalEmployWayService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk?"删除成功！":"删除失败！");
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	@RequestMapping(value="/findByPage",method={RequestMethod.POST})
	@ApiOperation(value = "分页查询数据", notes = "分页查询数据",httpMethod="POST")
	public Response findByPage(@RequestBody RegPersonalEmployWayDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(regPersonalEmployWayService.findByPage(dto,pageVo));
	}
}
