package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.OrderApplyCcCriteria;
import me.zhengjie.modules.opl.service.dto.WorkOrderCriteria;

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
     * @title: updateOrderApplyCcByOrderId
     * @description: 修改工单抄送信息
     * @date: 2021/2/23 9:06
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void updateOrderApplyCcByOrderId(OrderApplyCcCriteria criteria);


    /**
     * @title: findCcByOrderId
     * @description: 根据工单id查询抄送数据
     * @date: 2021/2/1 11:27
     * @author: ming.cao
     * @param orderId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findCcByOrderId(Pageable pageable, Integer orderId, Integer orderType);

    /**
     * @title: findCcByEmpId
     * @description: 根据人员id查询抄送数据
     * @date: 2021/2/1 11:28
     * @author: ming.cao
     * @param empId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findCcByEmpId(Pageable pageable, String empId);

    /**
     * @title: findCcOrder
     * @description: 获取抄送我的工单
     * @date: 2021/2/18 13:41
     * @author: ming.cao
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findCcOrder(WorkOrderCriteria criteria, Pageable pageable);




}
