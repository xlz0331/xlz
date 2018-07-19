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
import com.hwagain.org.register.dto.RegJobDto;
import com.hwagain.org.register.service.IRegJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  入职注册-岗位信息 前端控制器
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@RestController
@RequestMapping(value="/register/regJob",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="入职注册-岗位信息",description="入职注册-岗位信息")
public class RegJobController extends BaseController{
	
	@Autowired
	IRegJobService regJobService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(regJobService.findAll(),null,this.getValidation(new RegJobDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(RegJobDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/register/regJob", dto);
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
		return SuccessResponseData.newInstance(regJobService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增数据", notes = "新增数据",httpMethod="POST")
	public Response save(@RequestBody RegJobDto dto){
		return SuccessResponseData.newInstance(regJobService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改数据", notes = "修改数据",httpMethod="POST")
	public Response update(@RequestBody RegJobDto dto){
		return SuccessResponseData.newInstance(regJobService.update(dto));
	}
	
	/**
	 * 删除数据，可批量
	 * 
	 * @param ids  格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value="/delete",method={RequestMethod.GET})
	@ApiOperation(value = "删除数据", notes = "删除数据",httpMethod="GET")
		@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String")
	})
	public Response delete(String ids){
		Boolean isOk = regJobService.deleteByIds(ids);
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
	public Response findByPage(@RequestBody RegJobDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(regJobService.findByPage(dto,pageVo));
	}
	
	/**
	 * 初始化岗位信息录入页面
	 * @param personalId
	 * @return
	 */
	@RequestMapping(value="/getAndInit",method={RequestMethod.GET})
	@ApiOperation(value = "初始化数据", notes = "初始化数据",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "fdId",value = "登记注册人员fdId", paramType = "query", required = true, dataType = "String")
	})
	public Response init(String fdId) {
		return SuccessResponseData.newInstance(regJobService.getAndInit(fdId));
	}
	/**
	 * 获取岗位上级信息
	 * @param personalId
	 * @return
	 */
	@RequestMapping(value="/getUpperInfo",method={RequestMethod.GET})
	@ApiOperation(value = "获取岗位上级信息", notes = "获取岗位上级信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "jobcode",value = "jobcode", paramType = "query", required = true, dataType = "String")
	})
	public Response getUppers(String jobcode) {
		return SuccessResponseData.newInstance(regJobService.getUpperInfo(jobcode));
	}
}
