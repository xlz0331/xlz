package com.hwagain.org.job.dto;

import java.io.Serializable;
import java.util.List;

import com.hwagain.framework.core.dto.BaseDto;

/**    
 * 封装体系、部门、工厂等级下定编定员详情返回
 * @author: huangyj   
 * @date: 2018年6月14日 
 */
public class OrgJobDesignAllDto extends BaseDto implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 /**
	  * 体系、部门、工厂等类型名字
	 */
	 private String nodeName;
	 
	 /**
	  * 封装体系、部门、工厂等级下定编定员详
	 */
	 private List<OrgJobDesignBranchDto> branchDetails;

	 
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	
	public List<OrgJobDesignBranchDto> getBranchDetails() {
		return branchDetails;
	}

	public void setBranchDetails(List<OrgJobDesignBranchDto> branchDetails) {
		this.branchDetails = branchDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	 
}
