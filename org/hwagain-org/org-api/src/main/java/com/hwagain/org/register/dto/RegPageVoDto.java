package com.hwagain.org.register.dto;

import java.io.Serializable;

import com.hwagain.framework.core.dto.BaseDto;
import com.hwagain.framework.core.dto.PageVO;

public class RegPageVoDto extends BaseDto implements Serializable {

	  /**
     * 分页信息
     */
	private PageVO pageVo;
	public PageVO getPageVo() {
		return this.pageVo;
	}
	public void setPageVo(PageVO pageVo) {
		this.pageVo = pageVo;
	}
	
}
