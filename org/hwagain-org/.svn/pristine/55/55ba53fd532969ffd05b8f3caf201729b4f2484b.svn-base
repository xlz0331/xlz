package com.hwagain.org.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.framework.web.common.controller.BaseController;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.task.AsyncTask;

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
 * @since 2018-03-14
 */
@RestController
@RequestMapping(value="/config/orgVersionAudit",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="版本管理",description="版本管理")
public class OrgVersionAuditController extends BaseController{
	
	@Autowired
	IOrgVersionAuditService orgVersionAuditService;
	
	@Autowired
	AsyncTask asyncTask;
	
	/**
	 *按公司查询全部版本
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	@ApiOperation(value = "按公司编号查詢版本列表", notes = "按公司编号查詢版本列表",httpMethod="GET")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "code", value = "按公司编号查詢版本列表", paramType = "query", required = true, dataType = "String")
})
	public Response findAll(String code){
		return SuccessResponseData.newInstance(orgVersionAuditService.findAll(code));
	}
	
	/**
	 *提交审核
	 * 
	 * @return
	 */
	@RequestMapping("/submitAuditOrg")
	@ApiOperation(value = "提交审核", notes = "提交审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "time", value = "生效时间", paramType = "query", required = true, dataType = "String")
	})
	public Response submitAuditOrg(String company,String time){
		String result = orgVersionAuditService.submitAuditOrg(company,time);
		//临时数据插入到历史表
		asyncTask.saveHistory(company);
		return SuccessResponseData.newInstance(result);
	}
}
