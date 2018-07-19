package com.hwagain.org.register.service;

import com.hwagain.org.register.entity.RegWorkerInterview;
import com.hwagain.org.util.JDBCConfig;
import com.hwagain.org.register.dto.RegEmployTableDto;
import com.hwagain.org.register.dto.RegWorkerInterviewDto;
import com.hwagain.org.register.dto.RegWorkerInterviewPictureDto;
import com.hwagain.framework.mybatisplus.service.IService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.exception.CustomException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guoym
 * @since 2018-06-07
 */
public interface IRegWorkerInterviewService extends IService<RegWorkerInterview> {

	public List<RegWorkerInterviewDto> findAll() throws CustomException;

	public RegWorkerInterviewDto findOne(String fdId) throws CustomException;

	public RegWorkerInterviewDto save(RegWorkerInterviewDto dto) throws CustomException;

	public RegWorkerInterviewDto update(RegWorkerInterviewDto dto) throws CustomException;

	public Boolean deleteByIds(String ids) throws CustomException;

	public PageDto<RegWorkerInterviewDto> findByPage(RegWorkerInterviewDto dto, PageVO pageVo) throws CustomException;

	public PageDto<RegWorkerInterviewDto> findByCompanyPage(String company, PageVO pageVO) throws CustomException;

	public Boolean updateBatch(List<RegWorkerInterviewDto> dtos) throws CustomException;

	public String createEmployPeopleTable(String ids);

	public List<RegEmployTableDto> queryEmployPeopleTable(String oabatch);

	public List<RegWorkerInterviewPictureDto> queryWorkerPicByNid(String nid);

}
