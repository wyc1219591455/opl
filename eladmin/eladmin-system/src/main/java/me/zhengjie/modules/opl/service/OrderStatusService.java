package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.OrderStatus;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.OrderStatusCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 工单状态业务逻辑层
 * @author: ming.cao
 * @create: 2021-02-08 10:45
 **/
public interface OrderStatusService {

    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/8 11:32
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void insert(OrderStatusCriteria criteria);

    /**
     * @title: update
     * @description: 更新
     * @date: 2021/2/8 11:32
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void update(OrderStatusCriteria criteria);

    /**
     * @title: delete
     * @description: 根据id删除
     * @date: 2021/2/8 11:32
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void delete(List<Integer> ids);

    /**
     * @title: findStatusById
     * @description: 根据id查工单
     * @date: 2021/2/8 10:47
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderStatus>
     * @throws
     */
    Map<String,Object> findStatusById(Integer id);


    /**
     * @title: findAllStatus
     * @description: 查询所有状态
     * @date: 2021/2/8 10:48
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderStatus>
     * @throws
     */
    Map<String,Object> findAllStatus(Pageable pageable, OrderStatusCriteria criteria);

}
