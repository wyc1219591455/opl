package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.service.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 抄送
 * @author: ming.cao
 * @create: 2021-02-01 10:32
 **/
@Mapper
public interface OrderApplyCcMapper {

    /**
     * @title: findCcByTransId
     * @description: 根据主键id抄送
     * @date: 2021/2/1 10:48
     * @author: ming.cao
     * @param transId
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderApplyCc>
     * @throws
     */
    List<OrderApplyCcDto> findCcByTransId(Integer transId);

    /**
     * @title: findCcByTransIdFromSubOrder
     * @description: 根据子表id抄送
     * @date: 2021/2/24 9:40
     * @author: ming.cao
     * @param transId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.OrderApplyCcDto>
     * @throws
     */
    List<OrderApplyCcDto> findCcByTransIdFromSubOrder(Integer transId);

    /**
     * @title: findCcByEmpId
     * @description: 根据人员id抄送数据
     * @date: 2021/2/1 11:25
     * @author: ming.cao
     * @param empId
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderApplyCc>
     * @throws
     */
    List<OrderApplyCcDto> findCcByEmpId(String empId);

    /**
     * @title: findMasterOrderCcByEmpId
     * @description: 主表抄送数据
     * @date: 2021/2/9 18:43
     * @author: ming.cao
     * @param empId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto>
     * @throws
     */
    List<CrmWorkOrderDto> findMasterOrderCcByEmpId(String empId);

    /**
     * @title: findMasterOrderCcByEmpId
     * @description: 主表抄送数据
     * @date: 2021/2/9 18:43
     * @author: ming.cao
     * @param empId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto>
     * @throws
     */
    List<CrmWorkOrderDto> findMasterOrderCcByEmpId2(WorkOrderCriteria criteria, String empId);

    /**
     * @title: findSubOrderCcByEmpId
     * @description: 附表抄送数据
     * @date: 2021/2/9 18:42
     * @author: ming.cao
     * @param empId
     * @return java.util.List<me.zhengjie.modules.opl.domain.SubOrder>
     * @throws
     */
    List<SubOrderDto> findSubOrderCcByEmpId(String empId);


    /**
     * @title: insert
     * @description: 插入
     * @date: 2021/2/1 10:38
     * @author: ming.cao
     * @param orderApplyCc
     * @return void
     * @throws
     */
    void insert(OrderApplyCc orderApplyCc);

    /**
     * @title: batchInsert
     * @description: 批量插入
     * @date: 2021/2/23 9:44
     * @author: ming.cao
     * @param orderApplyCcList
     * @return void
     * @throws
     */
    void batchInsert(List<OrderApplyCc> orderApplyCcList);

    /**
     * @title: update
     * @description: 更新
     * @date: 2021/2/1 10:39
     * @author: ming.cao
     * @param orderApplyCc
     * @return void
     * @throws
     */
    void update(OrderApplyCc orderApplyCc);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/2/1 10:39
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: deleteByTransId
     * @description: 根据transId删除单子
     * @date: 2021/2/23 9:40
     * @author: ming.cao
     * @param transId
     * @return void
     * @throws
     */
    void deleteByTransId(Integer transId);

}
