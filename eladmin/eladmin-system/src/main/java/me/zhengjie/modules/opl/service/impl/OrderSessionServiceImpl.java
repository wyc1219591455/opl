package me.zhengjie.modules.opl.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.mapper.OrderSessionDetailMapper;
import me.zhengjie.modules.opl.mapper.OrderSessionMapper;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.OrderSessionDetailDto;
import me.zhengjie.modules.opl.service.dto.OrderSessionDto;
import me.zhengjie.modules.opl.service.dto.SessionDetailCriteria;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @program: eladmin
 * @description: 工单主单明细业务逻辑实现
 * @author: yuchao.wang
 * @create: 2021-01-20 19:25
 **/
@Service
@RequiredArgsConstructor
public class OrderSessionServiceImpl implements OrderSessionService {

    private final OrderSessionMapper orderSessionMapper;
    private final OrderSessionDetailMapper orderSessionDetailMapper;

    @Override
    public void insertSession(OrderSession orderSession) {
        //获取opl工单号
         orderSessionMapper.insertSession(orderSession);
    }

    @Override
    public List<OrderSessionDto> findSessionById(Integer transId) {

        List<OrderSessionDto> orderSessionDtoList= orderSessionMapper.findSessionById(transId);
        for(OrderSessionDto orderSessionDto:orderSessionDtoList)
        {
            SessionDetailCriteria sessionDetailCriteria=new SessionDetailCriteria();
            sessionDetailCriteria.setTransId(transId);
            sessionDetailCriteria.setSessionId(orderSessionDto.getId());
            orderSessionDto.setOrderSessionDetailDtoList(orderSessionDetailMapper.findSessionDetailById(sessionDetailCriteria));
        }
        return orderSessionDtoList;

    }

}
