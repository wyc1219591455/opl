package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.OrderStatus;
import me.zhengjie.modules.opl.service.dto.OrderStatusCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 工单状态
 * @author: ming.cao
 * @create: 2021-02-08 10:35
 **/
@Mapper
public interface OrderStatusMapper {

    /**
     * @title: insert
     * @description: 插入
     * @date: 2021/2/8 10:39
     * @author: ming.cao
     * @param orderStatus
     * @return void
     * @throws
     */
    void insert(OrderStatus orderStatus);

    /**
     * @title: update
     * @description: 更新
     * @date: 2021/2/8 11:15
     * @author: ming.cao
     * @param orderStatus
     * @return void
     * @throws
     */
    void update(OrderStatus orderStatus);

    /**
     * @title: delete
     * @description: 根据id删除
     * @date: 2021/2/8 11:16
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: findStatusById
     * @description: 通过id查询查询
     * @date: 2021/2/8 10:39
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderStatus>
     * @throws
     */
    List<OrderStatus> findStatusById(Integer id);

    /**
     * @title: findAllStatus
     * @description: 查询所有
     * @date: 2021/2/8 10:44
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.OrderStatus>
     * @throws
     */
    List<OrderStatus> findAllStatus(OrderStatusCriteria criteria);

}
