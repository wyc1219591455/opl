package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;

import java.util.List;


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
