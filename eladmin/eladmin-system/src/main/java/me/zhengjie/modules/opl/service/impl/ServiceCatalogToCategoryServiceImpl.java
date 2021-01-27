package me.zhengjie.modules.opl.service.impl;


import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.ServiceCatalogToCategory;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToCategoryMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToCategoryService;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                getCategory(trequestCategoryDto);
            }
        }
        return  categoryDtoList;
    }

    @Override
    public void insertCategoryAssociation(ServiceCatalogToCategory serviceCatalogToCategory){
       serviceCatalogToCategoryMapper.insertCatalogToCategoryAssociation(serviceCatalogToCategory);
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

}
