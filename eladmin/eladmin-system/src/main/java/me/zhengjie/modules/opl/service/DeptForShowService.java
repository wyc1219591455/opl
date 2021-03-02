package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.DeptVo;

import java.util.Map;

/**
 * @program: eladmin
 * @description: 部门业务逻辑层
 * @author: ming.cao
 * @create: 2021-01-21 11:35
 **/
public interface DeptForShowService {
    /**
     * @title: getOrgData
     * @description: 获取部门树结构展示数据
     * @date: 2021/1/26 10:01
     * @author: ming.cao
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> getOrgData();

    /**
     * @title: findDeptVoNotInCatalogId
     * @description: 获取服务id下不在此服务组的部门信息
     * @date: 2021/2/23 10:23
     * @author: ming.cao
     * @param catalogId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findDeptVoNotInCatalogId(Pageable pageable, Integer catalogId, DeptVo deptVo);

    /**
     * @title: findDeptVoNotInCatalogId
     * @description: 获取服务id下不在此服务组的部门信息
     * @date: 2021/2/23 10:23
     * @author: ming.cao
     * @param catalogId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findDeptVoNotInCatalogId2(Pageable pageable, Integer catalogId, DeptVo deptVo);

}
