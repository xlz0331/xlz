package com.hwagain.org.register.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.register.dto.RegWorkerInterviewDto;
import com.hwagain.org.register.service.IRegWorkerInterviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 入职注册-岗位工面试体检 前端控制器
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@RestController
@RequestMapping(value = "/register/regWorkerInterview", method = { RequestMethod.GET, RequestMethod.POST })
@Api(value = "入职注册-岗位工面试体检", description = "入职注册-岗位工面试体检")
public class RegWorkerInterviewController extends BaseController {

	@Autowired
	IRegWorkerInterviewService regWorkerInterviewService;

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表", httpMethod = "GET")
	public Response findAll() {
		return SuccessResponseData.newInstance(regWorkerInterviewService.findAll(), null,
				this.getValidation(new RegWorkerInterviewDto()), null);
	}

	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(RegWorkerInterviewDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath,
				"/register/regWorkerInterview", dto);
		return validURIMaps;
	}

	/**
	 * 按ID查询数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/findOne", method = { RequestMethod.GET })
	@ApiOperation(value = "按ID查询数据", notes = "按ID查询数据", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fdId", value = "数据ID", paramType = "query", required = false, dataType = "String") })
	public Response findOne(String fdId) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.findOne(fdId));
	}

	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
	public Response save(@RequestBody RegWorkerInterviewDto dto) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.save(dto));
	}

	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "POST")
	public Response update(@RequestBody RegWorkerInterviewDto dto) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.update(dto));
	}

	/**
	 * 删除数据，可批量
	 * 
	 * @param ids
	 *            格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	@ApiOperation(value = "删除数据", notes = "删除数据", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = true, dataType = "String") })
	public Response delete(String ids) {
		Boolean isOk = regWorkerInterviewService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk ? "删除成功！" : "删除失败！");
	}

	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	@RequestMapping(value = "/findByPage", method = { RequestMethod.POST })
	@ApiOperation(value = "分页查询数据", notes = "分页查询数据", httpMethod = "POST")
	public Response findByPage(@RequestBody RegWorkerInterviewDto dto, PageVO pageVo) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.findByPage(dto, pageVo));
	}

	/**
	 * 按公司分页列出岗位工列表
	 * 
	 * @param companyId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findByCompanyPage", method = { RequestMethod.GET })
	@ApiOperation(value = "公司岗位工列表", notes = "公司岗位工列表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyId", value = "公司id", paramType = "query", required = false, dataType = "String"),
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", required = true, dataType = "Integer") })
	public Response findByCompanyPage(String companyId, int page, int pageSize) {
		PageVO pageVo = new PageVO();
		pageVo.setPage(page + 1);
		pageVo.setPageSize(pageSize);
		return SuccessResponseData.newInstance(regWorkerInterviewService.findByCompanyPage(companyId, pageVo));
	}

	/**
	 * 批量修改体检状态
	 * 
	 * @param dtos
	 * @return
	 */
	@RequestMapping(value = "/updateBatch", method = { RequestMethod.POST })
	@ApiOperation(value = "批量修改体检状态", notes = "批量修改体检状态", httpMethod = "POST")
	public Response updateBatch(@RequestBody List<RegWorkerInterviewDto> dtos) {
		if (regWorkerInterviewService.updateBatch(dtos)) {
			return SuccessResponseData.newInstance("保存成功");
		} else {
			return SuccessResponseData.newInstance("保存失败");
		}
	}

	/**
	 * 生成录用人员明细表
	 * 
	 * @param ids
	 *            格式：1;2;3;4....
	 * @return
	 */
	@RequestMapping(value = "/createEmployPeopleTable", method = { RequestMethod.GET })
	@ApiOperation(value = "生成录用人员明细表", notes = "生成录用人员明细表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4", paramType = "query", required = true, dataType = "String") })
	public String createEmployPeopleTable(String ids) {
		return regWorkerInterviewService.createEmployPeopleTable(ids);
	}

	@RequestMapping(value = "/queryEmployPeopleTable", method = { RequestMethod.GET })
	@ApiOperation(value = "查询录用人员明细表", notes = "查询录用人员明细表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oabatch", value = "提交OA批次(传到OA系统中间表的recordId)", paramType = "query", required = true, dataType = "String") })
	public Response queryEmployPeopleTable(String oabatch) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.queryEmployPeopleTable(oabatch));
	}
	
	@RequestMapping(value = "/queryWorkerPicByNid", method = { RequestMethod.GET })
	@ApiOperation(value = "查询岗位工照片", notes = "查询岗位工照片", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "nid", value = "身份证号码", paramType = "query", required = true, dataType = "String") })
	public Response queryWorkerPicByNid(String nid) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.queryWorkerPicByNid(nid));
	}
}
