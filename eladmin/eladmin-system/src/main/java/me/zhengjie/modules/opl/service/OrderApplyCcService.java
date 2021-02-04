package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;

import java.util.Map;

/**
 * @program: eladmin
 * @description: 抄送业务逻辑
 * @author: ming.cao
 * @create: 2021-02-01 11:24
 **/

public interface OrderApplyCcService {
    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/1 11:26
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void insert(OrderApplyCc orderApplyCc);

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/2/1 11:26
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void update(OrderApplyCc orderApplyCc);

    /**
     * @title: findCcByOrderId
     * @description: 根据工单id查询抄送数据
     * @date: 2021/2/1 11:27
     * @author: ming.cao
     * @param orderId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findCcByOrderId(Pageable pageable, Integer orderId);

    /**
     * @title: findCcByEmpId
     * @description: 根据人员id查询抄送数据
     * @date: 2021/2/1 11:28
     * @author: ming.cao
     * @param empId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findCcByEmpId(Pageable pageable, Integer empId);

}