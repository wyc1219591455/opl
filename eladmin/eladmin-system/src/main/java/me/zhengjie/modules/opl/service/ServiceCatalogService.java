package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalog;


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
     * @description: 新增服务分类
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

//    /**
//     * @title: insertSubCatalog
//     * @description: 新增服务子分类
//     * @date: 2021/1/20 18:55
//     * @author: yuchao.wang
//     * @param subServiceCatalog
//     * @return void
//     * @throws
//     */
//    void insertSubCatalog(SubServiceCatalog subServiceCatalog);
}
