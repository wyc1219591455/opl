package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-08 11:28
 **/
@Mapper
public interface CrmWorkOrderMapper {

    /**
     * @title: insert
     * @description: opl插入表
     * @date: 2021/1/13 19:32
     * @author: ming.cao
     * @param crmWorkOrderCriteria
     * @return void
     * @throws
     */
    void insert(CrmWorkOrderCriteria crmWorkOrderCriteria);

    /**
     * @title: update
     * @description: update
     * @date: 2021/1/13 19:32
     * @author: ming.cao
     * @param crmWorkOrder
     * @return void
     * @throws
     */
    void update(CrmWorkOrder crmWorkOrder);

    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/13 19:46
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrderCriteria> findAll();

    /**
     * @title: findCrmOrderById
     * @description: findCrmOrderById
     * @date: 2021/1/13 19:32
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findCrmOrderById(Integer id);

    /**
     * @title: findCrmOrderByOrderType
     * @description: findCrmOrderByOrderType
     * @date: 2021/1/13 19:32
     * @author: ming.cao
     * @param type
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findCrmOrderByOrderType(Integer type);

    /**
     * @title: batchInsert
     * @description: batchInsert
     * @date: 2021/1/13 19:32
     * @author: ming.cao
     * @param list
     * @return void
     * @throws
     */
    void batchInsert(List<CrmWorkOrder> list);

    /**
     * @title: batchUpdate
     * @description: batchUpdate
     * @date: 2021/1/13 19:33
     * @author: ming.cao
     * @param list
     * @return void
     * @throws
     */
    void batchUpdate(List<CrmWorkOrder> list);

    /**
     * @title: findCrmOrderByMaxId
     * @description: findCrmOrderByMaxId
     * @date: 2021/1/13 19:33
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findCrmOrderByMaxId();

    /**
     * @title: findOplByMaxId
     * @description: findOplByMaxId
     * @date: 2021/1/13 19:33
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findOplByMaxId();

    /**
     * @title: findAllOrder
     * @description: findAllOrder
     * @date: 2021/1/13 19:33
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrderCriteria> findAllOrder();

    /**
     * @title: findOrderBySerialNo
     * @description: findOrderBySerialNo
     * @date: 2021/1/13 19:33
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.CrmWorkOrder>
     * @throws
     */
    List<CrmWorkOrder> findOrderBySerialNo();

}
