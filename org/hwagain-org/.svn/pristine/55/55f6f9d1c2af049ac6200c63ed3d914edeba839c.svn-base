package com.hwagain.org.register.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.validation.util.ValidationUtil;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.register.dto.RegPersonalCredentialsFileDto;
import com.hwagain.org.register.service.IRegPersonalCredentialsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 入职注册-证件附件 前端控制器
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
@RestController
@RequestMapping(value = "/register/regPersonalCredentialsFile", method = { RequestMethod.GET, RequestMethod.POST })
@Api(value = "入职注册-证件附件", description = "入职注册-证件附件")
public class RegPersonalCredentialsFileController extends BaseController {

	@Autowired
	IRegPersonalCredentialsFileService regPersonalCredentialsFileService;

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "查詢数据列表", notes = "查詢数据列表", httpMethod = "GET")
	public Response findAll() {
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.findAll(), null,
				this.getValidation(new RegPersonalCredentialsFileDto()), null);
	}

	/**
	 * 获取当前资源下的用户权限
	 * 
	 * @param dto
	 * @return
	 */
	public Map<String, Boolean> getValidation(RegPersonalCredentialsFileDto dto) {
		String contextPath = getRequest().getContextPath();
		Map<String, Boolean> validURIMaps = ValidationUtil.validModuleOperator(contextPath,
				"/register/regPersonalCredentialsFile", dto);
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
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.findOne(fdId));
	}

	/**
	 * 新增数据
	 * 
	 * @param dto
	 * @return
	 */
	// @RequestMapping(value="/save",method={RequestMethod.POST})
	@ApiOperation(value = "新增数据", notes = "新增数据", httpMethod = "POST")
	public Response save(@RequestBody RegPersonalCredentialsFileDto dto) {
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.save(dto));
	}

	/**
	 * 修改数据
	 * 
	 * @param dto
	 * @return
	 */
	// @RequestMapping(value="/update",method={RequestMethod.POST})
	@ApiOperation(value = "修改数据", notes = "修改数据", httpMethod = "POST")
	public Response update(@RequestBody RegPersonalCredentialsFileDto dto) {
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.update(dto));
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
			@ApiImplicitParam(name = "ids", value = "数据集,格式：1;2;3;4....", paramType = "query", required = false, dataType = "String") })
	public Response delete(String ids) {
		Boolean isOk = regPersonalCredentialsFileService.deleteByIds(ids);
		return SuccessResponse.newInstance(isOk ? "删除成功！" : "删除失败！");
	}

	/**
	 * 分页查询
	 * 
	 * @param pageDto
	 * @return
	 */
	//@RequestMapping(value = "/findByPage", method = { RequestMethod.POST })
	@ApiOperation(value = "分页查询数据", notes = "分页查询数据", httpMethod = "POST")
	public Response findByPage(@RequestBody RegPersonalCredentialsFileDto dto, PageVO pageVo) {
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.findByPage(dto, pageVo));
	}
	
	@RequestMapping(value = "/findByPersonal", method = { RequestMethod.GET })
	@ApiOperation(value = "按人员查询数据", notes = "按人员查询数据", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "personalId", value = "数据ID", paramType = "query", required = false, dataType = "String") })
	public Response findByPersonal(String personalId) {
		return SuccessResponseData.newInstance(regPersonalCredentialsFileService.findByPersonal(personalId));
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ApiOperation(value = "上传证件附件", notes = "上传证件附件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "personalId", value = "人员Id", paramType = "query", required = false, dataType = "String") })
	public Response upload(String personalId ,HttpServletRequest request, MultipartFile multipartFile) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames(); // 表单里可以提交了多个附件控件
			if (iter.hasNext()) {
				String formFileName = iter.next().toString();// 附件控件的名字
				List<MultipartFile> files = multiRequest.getFiles(formFileName);// 获取相应名字的所有附件
				if (files != null && files.size() > 0) {
					// 遍历附件并上传
					for (MultipartFile file : files) {
						// System.err.println(file.getOriginalFilename()+"=====");
						RegPersonalCredentialsFileDto d =new RegPersonalCredentialsFileDto();
						d.setCode("证件附件");
						d.setPersonalId(personalId);
						d.setName(file.getName());
						d.setFilepath(file.getOriginalFilename());
						d.setFiletype(file.getContentType());
						d.setFilesize(file.getSize());
						//d.setFilebytes(file.getBytes());
						regPersonalCredentialsFileService.save(d);
					}
				}
			}
		}
		return null;
	}
}
