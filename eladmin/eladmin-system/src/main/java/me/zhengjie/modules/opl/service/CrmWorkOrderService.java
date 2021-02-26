package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.service.dto.*;

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
     * @param crmWorkOrderCriteria
     * @return void
     * @throws
     */
    void update(CrmWorkOrderCriteria crmWorkOrderCriteria);



    /**
     * @title: update
     * @description: 受理工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param crmWorkOrderCriteria
     * @return void
     * @throws
     */
    void treatOrder(OrderSession orderSession);

    /**
     * @title: update
     * @description: 转派工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param transferOrderDto
     * @return void
     * @throws
     */
    void transferOrder(TransferOrderDto transferOrderDto);

    /**
     * @title: update
     * @description: 回复工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param orderSession
     * @return void
     * @throws
     */
    void remarks(OrderSession orderSession);

    /**
     * @title: update
     * @description: 派发工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param subOrder
     * @return void
     * @throws
     */
    void sellOrder(SubOrder subOrder);

    /**
     * @title: update
     * @description: 关闭工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param closeOrderDto
     * @return void
     * @throws
     */
    void closeOrder(CloseOrderDto closeOrderDto);

    /**
     * @title: update
     * @description: 取消工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param orderSession
     * @return void
     * @throws
     */
    void cancelOrder(OrderSession orderSession);

    /**
     * @title: update
     * @description: 重开工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param orderSession
     * @return void
     * @throws
     */
    void resetOrder(OrderSession orderSession);

    /**
     * @title: update
     * @description: 完成工单
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param completeOrderDto
     * @return void
     * @throws
     */
    void completeOrder(CompleteOrderDto completeOrderDto);

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
     * @title: findOrderBySerialNo
     * @description: 根据工单号查询工单
     * @date: 2021/1/13 19:33
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    OrderShowDto findOrderBySerialNo(SerialDto serialDto);






    /**
     * @title: findOplByMaxId
     * @description: findOplByMaxId
     * @date: 2021/1/13 19:33
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<User> findUser(User user);


    /**
     * @title: findServiceOrder
     * @description: 服务台工单
     * @date: 2021/1/13 19:46
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    Map<String,Object> findServiceOrder(WorkOrderCriteria workOrderCriteria,Pageable pageable);


    /**
     * @title: update
     * @description: 修改抄送人
     * @date: 2021/1/13 19:18
     * @author:   yuchao.wang
     * @param criteria
     * @return void
     * @throws
     */
    void UpdateApplyCc(OrderApplyCcCriteria criteria);


    List<User> findUserBy(List<String> userIds);
}
