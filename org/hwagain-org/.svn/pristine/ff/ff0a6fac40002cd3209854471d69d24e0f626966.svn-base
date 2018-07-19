package com.hwagain.org.job.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.framework.core.dto.PageDto;
import com.hwagain.framework.core.dto.PageVO;
import com.hwagain.framework.core.util.ArraysUtil;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.plugins.Page;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.org.constant.TypeConstant;
import com.hwagain.org.dept.dto.OrgDeptDto;
import com.hwagain.org.dept.dto.OrgDeptProDto;
import com.hwagain.org.dept.entity.OrgDeptPro;
import com.hwagain.org.dept.service.IOrgDeptProService;
import com.hwagain.org.job.dto.OrgJobDesignAllDto;
import com.hwagain.org.job.dto.OrgJobDesignBranchDto;
import com.hwagain.org.job.dto.OrgJobDesignDetailDto;
import com.hwagain.org.job.dto.OrgJobDesignDto;
import com.hwagain.org.job.dto.OrgJobMenu;
import com.hwagain.org.job.entity.OrgJobDesign;
import com.hwagain.org.job.mapper.OrgJobDesignMapper;
import com.hwagain.org.job.service.IOrgJobDesignService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangyj
 * @since 2018-06-13
 */
@Service("orgJobDesignService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrgJobDesignServiceImpl extends ServiceImpl<OrgJobDesignMapper, OrgJobDesign>
		implements IOrgJobDesignService {

	@Autowired
	private OrgJobDesignMapper orgJobDesignMapper;
	@Autowired IOrgDeptProService orgDeptProService;
	// entity转dto
	static MapperFacade entityToDtoMapper;

	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OrgJobDesign.class, OrgJobDesignDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();

		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OrgJobDesignDto.class, OrgJobDesign.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OrgJobDesignDto> findAll() {
		Wrapper<OrgJobDesign> wrapper = new CriterionWrapper<OrgJobDesign>(OrgJobDesign.class);
		List<OrgJobDesign> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OrgJobDesignDto.class);
	}

	@Override
	public OrgJobDesignDto findOne(String fdId) {
		return entityToDtoMapper.map(super.selectById(fdId), OrgJobDesignDto.class);
	}

	@Override
	public OrgJobDesignDto save(OrgJobDesignDto dto) {
		OrgJobDesign entity = dtoToEntityMapper.map(dto, OrgJobDesign.class);
		super.insert(entity);
		return dto;
	}

	@Override
	public OrgJobDesignDto update(OrgJobDesignDto dto) {
		OrgJobDesign entity = dtoToEntityMapper.map(dto, OrgJobDesign.class);
		super.updateById(entity);
		return dto;
	}

	@Override
	public Boolean deleteByIds(String ids) {
		String[] id = ids.split(";");
		return super.deleteBatchIds(Arrays.asList(id));
	}

	@Override
	public PageDto<OrgJobDesignDto> findByPage(OrgJobDesignDto dto, PageVO pageVo) {
		PageDto<OrgJobDesignDto> pageDto = new PageDto<OrgJobDesignDto>();
		pageDto.setPage(pageVo.getPage() + 1);
		pageDto.setPageSize(pageVo.getPageSize());
		Wrapper<OrgJobDesign> wrapper = new CriterionWrapper<OrgJobDesign>(OrgJobDesign.class);
		Page<OrgJobDesign> page = super.selectPage(new Page<OrgJobDesign>(pageDto.getPage(), pageDto.getPageSize()),
				wrapper);
		if (ArraysUtil.notEmpty(page.getRecords())) {
			pageDto.setList(entityToDtoMapper.mapAsList(page.getRecords(), OrgJobDesignDto.class));
		}
		pageDto.setRowCount(page.getTotal());
		return pageDto;
	}

	@Override
	public List<OrgJobDesignAllDto> getDetails(String company, String typeCode,String deptId) {
		Assert.notBlank(company, "公司编号不能为空");
		Assert.notBlank(typeCode, "类型编码不能为空");
		List<OrgDeptDto> deptId_Name = orgJobDesignMapper.getDeptIdByParam(company, typeCode,deptId);
		List<OrgJobDesignDetailDto> details = orgJobDesignMapper.getDetails(company, typeCode,deptId);
		List<OrgJobDesignAllDto> all = new ArrayList<OrgJobDesignAllDto>();
		for (OrgDeptDto orgDeptDto : deptId_Name) {
			OrgJobDesignAllDto oDesignAllDto = new OrgJobDesignAllDto();
			List<OrgJobDesignBranchDto> designBranchs = new ArrayList<OrgJobDesignBranchDto>();
			List<OrgJobDesignDetailDto> detailDtos = new ArrayList<OrgJobDesignDetailDto>();
			for (OrgJobDesignDetailDto detail : details) {
				if (orgDeptDto.getDeptid().equals(detail.getDeptId())) {
					detailDtos.add(detail);
					oDesignAllDto.setNodeName(orgDeptDto.getDescrshort());
				}
				if (orgDeptDto.getDeptid().equals(detail.getDeptParentId())) {
					detailDtos.add(detail);
					oDesignAllDto.setNodeName(orgDeptDto.getDescrshort());
				}
				
			}
			if(detailDtos!=null&&detailDtos.size()>0) {
				  Map<String,List<OrgJobDesignDetailDto>> ss =sort(detailDtos);
			        Iterator it = ss.keySet().iterator();
			        while(it.hasNext()){
			        OrgJobDesignBranchDto orgJobDesignBranchDto=new OrgJobDesignBranchDto();
			         String key = (String)it.next(); 
			         orgJobDesignBranchDto.setDeptId(key); 
			         List<OrgJobDesignDetailDto> deptDetails = ss.get(key);
			         orgJobDesignBranchDto.setDeptDetails(deptDetails);
			         orgJobDesignBranchDto.setDeptName(deptDetails.get(0).getNodeName());
			         designBranchs.add(orgJobDesignBranchDto);
			        }
				oDesignAllDto.setBranchDetails(designBranchs);
			}
			if(oDesignAllDto!=null&&oDesignAllDto.getNodeName()!=null&&oDesignAllDto.getBranchDetails().size()>0) {
				all.add(oDesignAllDto);
			}
		}
		return entityToDtoMapper.mapAsList(all, OrgJobDesignAllDto.class);

	}
	
	public  Map<String,List<OrgJobDesignDetailDto>> sort(List<OrgJobDesignDetailDto> list){
        TreeMap tm=new TreeMap();
        for(int i=0;i<list.size();i++){
        	OrgJobDesignDetailDto oDesignDetailDto=(OrgJobDesignDetailDto)list.get(i);
            if(tm.containsKey(oDesignDetailDto.getDeptId())){
            	List<OrgJobDesignDetailDto> l1=(ArrayList)tm.get(oDesignDetailDto.getDeptId());
             l1.add(oDesignDetailDto);
            }else{
            	List<OrgJobDesignDetailDto> tem=new ArrayList<OrgJobDesignDetailDto>();
                tem.add(oDesignDetailDto);
                tm.put(oDesignDetailDto.getDeptId(), tem);
            }
              
        }
        return tm;
    }

	@Override
	public List<OrgJobMenu> findJobMenu() {
		Wrapper<OrgDeptPro> wrapperDept = new CriterionWrapper<OrgDeptPro>(OrgDeptPro.class);
		wrapperDept.in("type_code", new String[] {TypeConstant.ORG_GS,TypeConstant.ORG_TX,TypeConstant.ORG_BM});
		List<OrgDeptPro> deptList = orgDeptProService.selectList(wrapperDept);
		List<OrgJobMenu> menuList = deptList.stream().map(dept -> {
			return new OrgJobMenu(dept.getDeptid(), dept.getDescrshort(), "", null, dept.getTypeCode(),dept.getCompany());
		}).collect(Collectors.toList());
		List<OrgJobMenu> companyList = menuList.stream().filter(dept->TypeConstant.ORG_GS.equals(dept.getTypeCode())).collect(Collectors.toList());
		for (OrgJobMenu orgJobMenu : companyList) {
			orgJobMenu.setChildren(menuList.stream().filter(dept->orgJobMenu.getCompanyCode().equals(dept.getCompanyCode())&&(TypeConstant.ORG_GC.equals(dept.getTypeCode())||TypeConstant.ORG_BM.equals(dept.getTypeCode()))).collect(Collectors.toList()));
		}
		return companyList;
	}
}
