package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.service.dto.*;

import java.util.List;
import java.util.Map;

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
    List<ServiceCatalogDto> findAllCatalogById();

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

    /**
     * @title: addCatalogToDept
     * @description: 新增
     * @date: 2021/2/25 9:59
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void addCatalogToDept(CatalogToDeptCriteria1 criteria1);

    /**
     * @title: deleteCatalogToDept
     * @description: 删除
     * @date: 2021/2/25 9:59
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void deleteCatalogToDept(CatalogToDeptCriteria1 criteria1);

    /**
     * @title: batchAddCatalogToDept
     * @description: 批量新增
     * @date: 2021/2/25 10:02
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void batchAddCatalogToDept(CatalogToDeptCriteria criteria);

    /**
     * @title: batchDeleteCatalogToDept
     * @description: 批量删除
     * @date: 2021/2/25 10:02
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void batchDeleteCatalogToDept(CatalogToDeptCriteria criteria);




    /**
     * @title: findAllUserByDeptId
     * @description: 获取所有部门人员信息
     * @date: 2021/2/24 20:07
     * @author: ming.cao
     * @param pageable
     * @param level
     * @param
     * @param deptId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.UserForShow>
     * @throws
     */
    Map<String,Object> findAllUserByDeptId(Pageable pageable, Integer level, Integer catalogId, Integer deptId,String name) ;

    /**
     * @title: findAllUserByName
     * @description: 根据名称获取所有部门人员信息
     * @date: 2021/3/2 9:20
     * @author: ming.cao
     * @param level
     * @param catalogId
     * @param name
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findAllUserByName(Integer level, Integer catalogId, String name);
}
