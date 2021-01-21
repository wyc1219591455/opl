package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.service.dto.OrderSessionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 11:28
 **/
@Mapper
public interface OrderSessionMapper {

    /**
     * @title: insert
     * @description: 工单子单新建
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param orderSession
     * @return void
     * @throws
     */
    void insertSession(OrderSession orderSession);


    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param transId
     * @return OrderSession
     * @throws
     */
    List<OrderSessionDto> findSessionById(Integer transId);
}
