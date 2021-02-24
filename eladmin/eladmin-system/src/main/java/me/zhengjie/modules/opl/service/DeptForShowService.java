package me.zhengjie.modules.opl.service;

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

}
