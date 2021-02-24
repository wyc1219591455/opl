package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.domain.OrderSessionDetail;
import me.zhengjie.modules.opl.service.dto.OrderSessionDetailDto;
import me.zhengjie.modules.opl.service.dto.SessionDetailCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-21 11:28
 **/
@Mapper
public interface OrderSessionDetailMapper {
    /**
     * @title: insert
     * @description: 工单子单明细新建
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param orderSessionDetail
     * @return void
     * @throws
     */
    void insertSessionDetail(OrderSessionDetail orderSessionDetail);


    /**
     * @title: findById
     * @description: findById
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param sessionDetailCriteria
     * @return OrderSession
     * @throws
     */
    List<OrderSessionDetailDto> findSessionDetailById(SessionDetailCriteria sessionDetailCriteria);

    /**
     * @title: findById
     * @description: findById
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param sessionDetailCriteria
     * @return OrderSession
     * @throws
     */
    List<OrderSessionDetailDto> findSuBSessionDetailById(SessionDetailCriteria sessionDetailCriteria);

}
