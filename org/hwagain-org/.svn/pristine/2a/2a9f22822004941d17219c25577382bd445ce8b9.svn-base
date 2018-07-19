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
import com.hwagain.org.register.dto.RegBaseDataDto;
import com.hwagain.org.register.service.IRegBaseDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@RestController
@RequestMapping(value="/register/regBaseData",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="入职注册-基础数据",description="入职注册-基础数据")
public class RegBaseDataController extends BaseController{
	
	@Autowired
	IRegBaseDataService regBaseDataService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(regBaseDataService.findAll(),null,this.getValidation(new RegBaseDataDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(RegBaseDataDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/register/regBaseData", dto);
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
		return SuccessResponseData.newInstance(regBaseDataService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增数据", notes = "新增数据",httpMethod="POST")
	public Response save(@RequestBody RegBaseDataDto dto){
		return SuccessResponseData.newInstance(regBaseDataService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改数据", notes = "修改数据",httpMethod="POST")
	public Response update(@RequestBody RegBaseDataDto dto){
		return SuccessResponseData.newInstance(regBaseDataService.update(dto));
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
		Boolean isOk = regBaseDataService.deleteByIds(ids);
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
	public Response findByPage(@RequestBody RegBaseDataDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(regBaseDataService.findByPage(dto,pageVo));
	}
	
	
	/**
	 * 按类型（中文）查询
	 * 
	 * @return
	 */
	@RequestMapping("/findByTypeCn")
	@ApiOperation(value = "按类型（中文）查询", notes = "按类型（中文）查询",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "typeCn", value = "类型：性别;婚姻状况;省份;学历;概要学历;学位;出生地类型;血型;政治面貌;招聘来源地;求职途径;求职网站;培养类别;试用期限;关系;民族;宗教信仰;户口类型;户口所在地;职务等级;员工类别;试用期限;员工属性;", paramType = "query", required = false, dataType = "String")
	})
	public Response findByTypeCn(String typeCn){
		return SuccessResponseData.newInstance(regBaseDataService.findByTypeCn(typeCn));
	}

}
