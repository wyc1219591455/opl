package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto;
import me.zhengjie.modules.opl.service.dto.SubOrderDto;
import me.zhengjie.modules.opl.service.dto.WorkOrderCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-08 11:28
 **/
@Mapper
public interface SubOrderMapper {
    /**
     * @title: insert
     * @description: 工单子单新建
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param subOrder
     * @return void
     * @throws
     */
    void insertSubOrder(SubOrder subOrder);

    /**
     * @title: update
     * @description: update
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param subOrder
     * @return void
     * @throws
     */
    void updateSubOrder(SubOrder subOrder);

    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param
     * @return SubOrder
     * @throws
     */
    List<SubOrderDto> findSubOrderByParentId(Integer parentId);

    /**
     * @title: findOrderBySerialNo
     * @description: findOrderBySerialNo
     * @date: 2021/1/13 19:33
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
   SubOrderDto findSubOrderBySerialNo(String SerialNo);

    /**
     * @title: findSubOrderById
     * @description: findOrderBySerialNo
     * @date: 2021/1/13 19:33
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    SubOrder findSubOrderById(Integer id);

    /**
     * @title: findParentWorkOrderDtoById
     * @description: findParentWorkOrderDtoById
     * @date: 2021/2/18 17:23
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto>
     * @throws
     */
    List<CrmWorkOrderDto> findParentWorkOrderDtoById(WorkOrderCriteria criteria, Integer id);


    /**
     * @title: findOplByMaxId
     * @description: findOplByMaxId
     * @date: 2021/1/13 19:33
     * @author: yuchao.wang
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<SubOrder> findSubOplByMaxId(Integer orderId);
}
