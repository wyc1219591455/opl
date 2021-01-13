package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;

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
     * @description: insert
     * @date: 2021/1/13 19:17
     * @author: ming.cao
     * @param crmWorkOrder
     * @return void
     * @throws
     */
    void insert(CrmWorkOrder crmWorkOrder) ;

    /**
     * @title: update
     * @description: update
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
     * @description: findAll
     * @date: 2021/1/13 19:48
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    Map<String,Object> findAll(CrmWorkOrderCriteria criteria, Pageable pageable);

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
