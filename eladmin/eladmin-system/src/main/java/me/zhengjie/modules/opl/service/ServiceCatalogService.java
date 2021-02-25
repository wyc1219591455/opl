package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalog;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;
import me.zhengjie.modules.opl.service.dto.CatalogCriteria;
import me.zhengjie.modules.opl.service.dto.SubCatalogVo;


import java.util.Map;


/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
public interface ServiceCatalogService {

    /**
     * @title: findAllCatalog
     * @description: 获取全部服务分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findAllCatalog(Pageable pageable);

    /**
     * @title: insertCatalog
     * @description: 新增服务分类主表
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param serviceCatalog
     * @return void
     * @throws
     */
    void insertCatalog(ServiceCatalog serviceCatalog);

    /**
     * @title: findParentCatalog
     * @description: 获取父服务分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findParentCatalog(Pageable pageable);



    /**
     * @title: findSubCatalog
     * @description: 根据ParentId获取子服务分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findSubCatalog(Integer parentId,Pageable pageable);

    /**
     * @title: insertSubCatalog
     * @description: 新增服务子分类
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param catalogCriteria
     * @return void
     * @throws
     */
    void insertSubCatalog(CatalogCriteria catalogCriteria);

    /**
     * @title: updateSubCatalog2
     * @description: 修改服务子分类
     * @date: 2021/2/23 18:25
     * @author: ming.cao
     * @param catalogCriteria
     * @return void
     * @throws
     */
    void updateSubCatalog2(CatalogCriteria catalogCriteria);

    /**
     * @title: updateParentCatalog
     * @description: 修改服务父分类
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param serviceCatalog
     * @return void
     * @throws
     */
    void updateParentCatalog(ServiceCatalog serviceCatalog);


    /**
     * @title: updateSubCatalog
     * @description: 修改服务子分类
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param subServiceCatalog
     * @return void
     * @throws
     */
    void updateSubCatalog(SubServiceCatalog subServiceCatalog);

    /**
     * @title: deleteSubCatalog
     * @description: 根据subCatalogId删除
     * @date: 2021/2/24 17:33
     * @author: ming.cao
     * @param subCatalogId
     * @return void
     * @throws
     */
    void deleteSubCatalog(Integer subCatalogId);


    /**
     * @title: findSubCatalogById
     * @description: 根据id获取子分类数据
     * @date: 2021/2/24 11:37
     * @author: ming.cao
     * @param catalogId
     * @return me.zhengjie.modules.opl.service.dto.CatalogCriteria
     * @throws
     */
    Map<String,Object> findSubCatalogById(Integer catalogId);

}
