package com.hwagain.org.anonymous.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwagain.framework.core.response.Response;
import com.hwagain.framework.core.response.SuccessResponse;
import com.hwagain.framework.core.response.SuccessResponseData;
import com.hwagain.org.config.dto.OrgVersionAuditDto;
import com.hwagain.org.config.service.IOrgVersionAuditService;
import com.hwagain.org.log.service.IOrgLogService;
import com.hwagain.org.register.service.IRegWorkerInterviewService;
import com.hwagain.org.task.service.IOrgSyncPsService;
import com.hwagain.org.user.service.IOrgUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  匿名访问
 * </p>
 *
 * @author hanj
 * @since 2018-03-14
 */
@RestController
@RequestMapping(value="/anonymous",method={RequestMethod.GET,RequestMethod.POST})
@Api(value="匿名接口",description="匿名接口")
public class AnonymousController {

	@Autowired
	IOrgVersionAuditService orgVersionAuditService;
	@Autowired
	IOrgLogService orgLogService;
	@Autowired IOrgSyncPsService orgSyncPsService;
	@Autowired
	IRegWorkerInterviewService regWorkerInterviewService;
	
	@Autowired IOrgUserService orgUserService;
	
	/**
	 *版本审核
	 * 
	 * @return
	 */
	@RequestMapping("/auditOrg")
	@ApiOperation(value = "版本审核", notes = "版本审核",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fdId", value = "版本ID", paramType = "query", required = true, dataType = "String"),
		@ApiImplicitParam(name = "status", value = "审核状态，2：已审核并通过(待同步)，3：已审核未通过（不同步）", paramType = "query", required = true, dataType = "String")
	})
	public Response auditOrg(String fdId,String status){
		String result = orgVersionAuditService.auditOrg(fdId,status);
		return SuccessResponseData.newInstance(result);
	}
	
	/**
	 * 提交OA审批查询
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/findOrgLogByCompany",method={RequestMethod.GET})
	@ApiOperation(value = "提交OA审批查询", notes = "提交OA审批查询",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "company", value = "公司编号", paramType = "query", required = false, dataType = "String"),
		@ApiImplicitParam(name = "version", value = "版本", paramType = "query", required = false, dataType = "Integer")
	})
	public Response findOrgLogByCompany(String company,Integer version){
		return SuccessResponseData.newInstance(orgLogService.findOrgLogByCompany(company, version));
	}
	
	/**
	 *测试接口
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/sycnPStree",method={RequestMethod.POST})
	@ApiOperation(value = "测试接口", notes = "测试接口",httpMethod="POST")
	public Response sycnPStree(){
		
		return SuccessResponseData.newInstance(orgUserService.findUserAllByDeptId("16019"));
		
//		return SuccessResponse.newInstance("11");
		
//		SysOrgPersonInfo user = UserUtils.getUserInfo();
//		return SuccessResponseData.newInstance(UserUtils.getDeptInfo(user.getParentId().replace("d_", "")));
	}
	
	
	@RequestMapping(value = "/queryEmployPeopleTable", method = { RequestMethod.GET })
	@ApiOperation(value = "查询录用人员明细表", notes = "查询录用人员明细表", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oabatch", value = "提交OA批次(传到OA系统中间表的recordId)", paramType = "query", required = true, dataType = "String") })
	public Response queryEmployPeopleTable(String oabatch) {
		return SuccessResponseData.newInstance(regWorkerInterviewService.queryEmployPeopleTable(oabatch));
	}
}
