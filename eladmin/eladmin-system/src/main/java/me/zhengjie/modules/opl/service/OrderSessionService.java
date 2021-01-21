package me.zhengjie.modules.opl.service;


import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.OrderSessionDto;

import java.util.List;

/**
 * @program: eladmin
 * @description: 工单主表明细
 * @author: yuchao.wang
 * @create: 2021-01-13 19:16
 **/
public interface OrderSessionService {

    /**
     * @title: insert
     * @description: 插入明细
     * @date: 2021/1/20 19:17
     * @author: yuchao.wang
     * @param orderSession
     * @return void
     * @throws
     */
    void insertSession(OrderSession orderSession) ;

    /**
     * @title: findSessionById
     * @description: 根据工单id获取明细
     * @date: 2021/1/13 19:18
     * @author: yuchao.wang
     * @param
     * @return OrderSessionDto
     * @throws
     */
    List<OrderSessionDto> findSessionById(Integer transId);
}
