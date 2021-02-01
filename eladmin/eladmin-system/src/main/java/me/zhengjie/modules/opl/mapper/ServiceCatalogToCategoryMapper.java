package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.ServiceCatalogToCategory;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.domain.TrequestCategory;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-21 11:28
 **/
@Mapper
public interface ServiceCatalogToCategoryMapper {

    /**
     * @title: insert
     * @description: 新增关联关系
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param serviceCatalogToCategory
     * @return void
     * @throws
     */
    void insertCatalogToCategoryAssociation(ServiceCatalogToCategory serviceCatalogToCategory);


    /**
     * @title: 根据服务分类ID查找工单分类
     * @description: 根据服务分类ID查找工单分类
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param catalogId
     * @return ServiceCatalogToCategory
     * @throws
     */
    List<TrequestCategoryDto> findAssociationById(Integer catalogId);


    /**
     * @title: 根据父ID查找子分类
     * @description: 根据父ID查找子分类
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param categoryId
     * @return TrequestCategoryDto
     * @throws
     */
    List<TrequestCategoryDto> findSubAssociationById(Integer categoryId);
}