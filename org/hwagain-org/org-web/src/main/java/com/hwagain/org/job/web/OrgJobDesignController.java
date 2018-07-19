package com.hwagain.org.job.web;

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
import com.hwagain.org.job.dto.OrgJobDesignDto;
import com.hwagain.org.job.service.IOrgJobDesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangyj
 * @since 2018-06-13
 */
@RestController
@RequestMapping(value="/orgJobDesign",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="岗位定编定员管理",description="岗位定编定员管理")
public class OrgJobDesignController extends BaseController{
	
	@Autowired
	IOrgJobDesignService orgJobDesignService;
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表",httpMethod="GET")
	public Response findAll(){
		return SuccessResponseData.newInstance(orgJobDesignService.findAll(),null,this.getValidation(new OrgJobDesignDto()),null);
	}
	
	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(OrgJobDesignDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath, "/register/orgJobDesign", dto);
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
		return SuccessResponseData.newInstance(orgJobDesignService.findOne(fdId));
	}
	
	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增数据", notes = "新增数据",httpMethod="POST")
	public Response save(@RequestBody OrgJobDesignDto dto){
		return SuccessResponseData.newInstance(orgJobDesignService.save(dto));
	}
	
	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改数据", notes = "修改数据",httpMethod="POST")
	public Response update(@RequestBody OrgJobDesignDto dto){
		return SuccessResponseData.newInstance(orgJobDesignService.update(dto));
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
		Boolean isOk = orgJobDesignService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk?"删除成功！":"删除失败！");
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	@RequestMapping(value="/findByPage",method={RequestMethod.GET})
	@ApiOperation(value = "分页查询数据", notes = "分页查询数据",httpMethod="GET")
	public Response findByPage(OrgJobDesignDto dto,PageVO pageVo){
		return SuccessResponseData.newInstance(orgJobDesignService.findByPage(dto,pageVo));
	}
	

	/**
	 * 查询定编定员信息
	 * 
	 * @return
	 */
	@RequestMapping("/getDetails")
	@ApiOperation(value = "查询定编定员信息", notes = "查询定编定员信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "typeCode", value = "类型编码", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", required = false, dataType = "String"),
	})
	public Response getDetails(String company,String typeCode,String deptId){
		return SuccessResponseData.newInstance(orgJobDesignService.getDetails(company,typeCode,deptId));
	}
	
	/**
	 * 查询菜单
	 * 
	 * @return
	 */
	@RequestMapping("/findJobMenu")
	@ApiOperation(value = "查询菜单", notes = "查询菜单",httpMethod="GET")
	public Response findJobMenu(){
		return SuccessResponseData.newInstance(orgJobDesignService.findJobMenu());
	}
}
