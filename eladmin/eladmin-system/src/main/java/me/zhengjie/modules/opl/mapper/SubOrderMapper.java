package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.SubOrder;
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
    List<SubOrder> findSubOrderByParentId();
}
