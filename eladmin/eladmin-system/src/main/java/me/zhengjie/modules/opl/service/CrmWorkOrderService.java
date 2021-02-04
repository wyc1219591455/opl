package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.WorkOrderCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: crm工单业务逻辑层
 * @author: ming.cao
 * @create: 2021-01-13 19:16
 **/
public interface CrmWorkOrderService {

    /**
     * @title: insert
     * @description: opl新建工单
     * @date: 2021/1/13 19:17
     * @author: ming.cao
     * @param crmWorkOrderCriteria
     * @return void
     * @throws
     */
    void insert(CrmWorkOrderCriteria crmWorkOrderCriteria) ;

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/1/13 19:18
     * @author: ming.cao
     * @param crmWorkOrder
     * @return void
     * @throws
     */
    void update(CrmWorkOrder crmWorkOrder);

    /**
     * @title: findCrmOrderById
     * @description: 根据id获取数据
     * @date: 2021/1/13 19:18
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findCrmOrderById(Integer id);

    /**
     * @title: findAll
     * @description: 查询所有
     * @date: 2021/1/13 19:48
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    Map<String,Object> findAll(WorkOrderCriteria workOrderCriteria, Pageable pageable);

    /**
     * @title: findCreatedByMe
     * @description: 查询我发起的工单
     * @date: 2021/1/13 19:48
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    Map<String,Object> findCreatedByMe(WorkOrderCriteria workOrderCriteria, Pageable pageable);

    /**
     * @title: findTreatByMe
     * @description: 查询我处理的工单
     * @date: 2021/1/13 19:48
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    Map<String,Object> findTreatByMe(WorkOrderCriteria workOrderCriteria, Pageable pageable);

    /**
     * @title: findCrmOrderByOrderType
     * @description: 更具工单分类获取数据
     * @date: 2021/1/13 19:23
     * @author: ming.cao
     * @param type
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findCrmOrderByOrderType(Integer type);


}
