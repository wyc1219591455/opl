package me.zhengjie.modules.opl.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.CrmProduct;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalogToCategory;
import me.zhengjie.modules.opl.domain.TrequestCategory;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToCategoryMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToCategoryService;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 服务分类对应工单分类关联表
 * @author: yuchao.wang
 * @create: 2021-01-20 19:25
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogToCategoryServiceImpl implements ServiceCatalogToCategoryService {

    private final ServiceCatalogToCategoryMapper serviceCatalogToCategoryMapper;

    @Override
    public List<TrequestCategoryDto> findAllCatalogById(Integer catalogId){
        List<TrequestCategoryDto> categoryDtoList= serviceCatalogToCategoryMapper.findAssociationById(catalogId);
        if(ObjectUtil.isNotEmpty(categoryDtoList))
        {
            for(TrequestCategoryDto trequestCategoryDto:categoryDtoList)
            {
                getUsedCategory(trequestCategoryDto);
            }
        }
        return  categoryDtoList;
    }



    @Override
    public void insertCategoryAssociation(ServiceCatalogToCategory serviceCatalogToCategory){
       serviceCatalogToCategoryMapper.insertCatalogToCategoryAssociation(serviceCatalogToCategory);
    }


    @Override
    public void insertCategory(TrequestCategory trequestCategory){
        if(trequestCategory.getParentId()==0)
        {
            trequestCategory.setRootId(0);
            String jobNumber = SecurityUtils.getCurrentUsername();
            trequestCategory.setCreateUserId(jobNumber);
            trequestCategory.setCreateDateTime(new Timestamp(new Date().getTime()));
            trequestCategory.setStatus(1);
            serviceCatalogToCategoryMapper.insertCategory(trequestCategory);
        }
        else {
            TrequestCategory parentCategory = serviceCatalogToCategoryMapper.findCategoryById(trequestCategory.getParentId());
            Integer rootId = parentCategory.getRootId();
            trequestCategory.setRootId(rootId);
            String jobNumber = SecurityUtils.getCurrentUsername();
            trequestCategory.setCreateUserId(jobNumber);
            trequestCategory.setCreateDateTime(new Timestamp(new Date().getTime()));
            trequestCategory.setStatus(1);
            serviceCatalogToCategoryMapper.insertCategory(trequestCategory);
        }
    }

    @Override
    public Map<String, Object> findAllCatalog(Pageable pageable){
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<TrequestCategoryDto> categoryDtoList= serviceCatalogToCategoryMapper.findAssociation();
        if(ObjectUtil.isNotEmpty(categoryDtoList))
        {
            for(TrequestCategoryDto trequestCategoryDto:categoryDtoList)
            {
                getCategory(trequestCategoryDto);
            }
        }
        PageInfo<TrequestCategoryDto> pageInfo = new PageInfo<>(categoryDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findUsedCatalog(Pageable pageable){
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<TrequestCategoryDto> categoryDtoList= serviceCatalogToCategoryMapper.findUsedAssociation();
        if(ObjectUtil.isNotEmpty(categoryDtoList))
        {
            for(TrequestCategoryDto trequestCategoryDto:categoryDtoList)
            {
                getUsedCategory(trequestCategoryDto);
            }
        }
        PageInfo<TrequestCategoryDto> pageInfo = new PageInfo<>(categoryDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findRootCatalog(Pageable pageable){
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<TrequestCategoryDto> categoryDtoList= serviceCatalogToCategoryMapper.findUsedAssociation();
        PageInfo<TrequestCategoryDto> pageInfo = new PageInfo<>(categoryDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public List<TrequestCategory> findCategoryById(Integer id){

        return serviceCatalogToCategoryMapper.findCategoryListById(id);
    }

    //递归查询子节点
    public TrequestCategoryDto getCategory(TrequestCategoryDto trequestCategoryDto){
        List<TrequestCategoryDto> trequestCategoryDtos=serviceCatalogToCategoryMapper.findSubAssociationById(trequestCategoryDto.getId());
        if(trequestCategoryDtos!=null)
        {
            trequestCategoryDto.setTrequestCategoryDtos(trequestCategoryDtos);
            for (TrequestCategoryDto trequestCategoryDto1:trequestCategoryDtos)
                {
                    getCategory(trequestCategoryDto1);
                }
        }
        return trequestCategoryDto;
    }

    public TrequestCategoryDto getUsedCategory(TrequestCategoryDto trequestCategoryDto){
        List<TrequestCategoryDto> trequestCategoryDtos=serviceCatalogToCategoryMapper.findUsedSubAssociationById(trequestCategoryDto.getId());
        if(trequestCategoryDtos!=null)
        {
            trequestCategoryDto.setTrequestCategoryDtos(trequestCategoryDtos);
            for (TrequestCategoryDto trequestCategoryDto1:trequestCategoryDtos)
            {
                getUsedCategory(trequestCategoryDto1);
            }
        }
        return trequestCategoryDto;
    }

}
