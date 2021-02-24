package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.ServiceCatalogRelateDept;
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
     * @title: insert
     * @description: 批量新增
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void batchInsert(List<ServiceCatalogToCategory> list);


    /**
     * @title: insert
     * @description: 新增工单分类
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void insertCategory(TrequestCategory trequestCategory);

    /**
     * @title: find
     * @description: 根据ID查找工单分类
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    List<TrequestCategory> findCategoryById(Integer Id);

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
     * @title: 查找所有父分类
     * @description: 查找所有父分类
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogToCategory
     * @throws
     */
    List<TrequestCategoryDto> findAssociation();

    /**
     * @title: 查找所有可用的父分类
     * @description: 查找所有可用的父分类
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogToCategory
     * @throws
     */
    List<TrequestCategoryDto> findUsedAssociation();


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

    /**
     * @title: 根据ID查找可用的子分类
     * @description: 根据父ID查找可用的子分类
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param categoryId
     * @return TrequestCategoryDto
     * @throws
     */
    List<TrequestCategoryDto> findUsedSubAssociationById(Integer categoryId);

    /**
     * @title: update
     * @description: 根据ID修改
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void updateCategory(TrequestCategory trequestCategory);

    /**
     * @title: delete
     * @description: 根据ID删除
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void deleteCategoryById(Integer Id);


    TrequestCategoryDto getParentCategory(TrequestCategoryDto trequestCategoryDto);

}
