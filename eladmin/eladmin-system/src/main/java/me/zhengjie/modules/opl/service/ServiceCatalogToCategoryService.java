package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalogToCategory;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.domain.TrequestCategory;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;

import java.util.List;
import java.util.Map;


/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
public interface ServiceCatalogToCategoryService {


    /**
     * @title: findAllCatalogById
     * @description: 获取服务分类的工单分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    List<TrequestCategoryDto> findAllCatalogById(Integer catalogId);


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
     * @title: update
     * @description: 修改工单分类
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void updateCategory(TrequestCategory trequestCategory);

    /**
     * @title: delete
     * @description: 删除工单分类
     * @date: 2021/2/8 15:21
     * @author: yuchao.wang
     * @param
     * @return void
     * @throws
     */
    void deleteCategory(List<Integer> ids);

    /**
     * @title: findAllCatalog
     * @description: 获取所有工单分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    Map<String, Object> findAllCatalog(Pageable pageable);

    /**
     * @title: findAllCatalog
     * @description: 获取使用的工单分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    Map<String, Object> findUsedCatalog(Pageable pageable);

    /**
     * @title: findAllCatalog
     * @description: 获取使用的工单分类根节点
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    Map<String, Object> findRootCatalog(Pageable pageable);


    /**
     * @title: findAllCatalogById
     * @description: 获取服务分类的工单分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    List<TrequestCategory> findCategoryById(Integer id);

    /**
     * @title: insertCategoryAssociation
     * @description: 新增服务分类和工单分类关联
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param serviceCatalogToCategory
     * @return void
     * @throws
     */
    void insertCategoryAssociation(ServiceCatalogToCategory serviceCatalogToCategory);
}
