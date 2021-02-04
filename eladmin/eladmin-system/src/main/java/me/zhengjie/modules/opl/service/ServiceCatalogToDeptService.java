package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
public interface ServiceCatalogToDeptService {
    /**
     * @title: findSubCatalogById
     * @description: 获取用户可查看的子分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return SubServiceCatalogDto
     * @throws
     */
    List<SubServiceCatalogDto> findSubCatalogById(String userId);

    /**
     * @title: findParentCatalogById
     * @description: 获取用户可查看的父分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    List<ServiceCatalogDto> findParentCatalogById(String userId);

    /**
     * @title: findAllCatalogById
     * @description: 获取用户可查看的所有分类
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    List<ServiceCatalogDto> findAllCatalogById(String userId);

    /**
     * @title: insertCatalogToDeptAssociation
     * @description: 新增服务和人员关联表
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param serviceCatalogToDept
     * @return void
     * @throws
     */
    void insertCatalogToDeptAssociation(ServiceCatalogToDept serviceCatalogToDept);

}
