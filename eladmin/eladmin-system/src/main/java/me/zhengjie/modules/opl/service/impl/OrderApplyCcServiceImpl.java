package me.zhengjie.modules.opl.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.OrderApplyCcMapper;
import me.zhengjie.modules.opl.service.OrderApplyCcService;
import me.zhengjie.modules.opl.service.dto.OrderApplyCcDto;
import me.zhengjie.utils.PageHelpResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 抄送业务逻辑实现
 * @author: ming.cao
 * @create: 2021-02-01 11:29
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderApplyCcServiceImpl implements OrderApplyCcService {

    private final OrderApplyCcMapper orderApplyCcMapper;

    @Override
    public void insert(OrderApplyCc orderApplyCc) {
        try {
            orderApplyCcMapper.insert(orderApplyCc);
        } catch (Exception e) {
            throw new BadRequestException("新增失败！");
        }
    }

    @Override
    public void update(OrderApplyCc orderApplyCc) {
        try {
            orderApplyCcMapper.update(orderApplyCc);
        } catch (Exception e) {
            throw new BadRequestException("修改失败！");
        }
    }

    @Override
    public Map<String, Object> findCcByOrderId(Pageable pageable, Integer orderId) {

        if (pageable != null && pageable.getPage() == -1) {
            List<OrderApplyCcDto> orderApplyCcList = orderApplyCcMapper.findCcByTransId(orderId);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("content", orderApplyCcList);
            map.put("totalElements", orderApplyCcList.size());
            return map;
        } else {
            PageHelper.startPage(pageable.getPage(), pageable.getSize());
            List<OrderApplyCcDto> orderApplyCcList = orderApplyCcMapper.findCcByTransId(orderId);
            PageInfo<OrderApplyCcDto> pageInfo1 = new PageInfo<>(orderApplyCcList);
            return PageHelpResultUtil.toPage(pageInfo1);
        }

    }

    @Override
    public Map<String, Object> findCcByEmpId(Pageable pageable, Integer empId) {

        if (pageable != null && pageable.getPage() == -1) {
            List<OrderApplyCcDto> orderApplyCcList = orderApplyCcMapper.findCcByEmpId(empId);
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("content", orderApplyCcList);
            map.put("totalElements", orderApplyCcList.size());
            return map;
        } else {
            PageHelper.startPage(pageable.getPage(), pageable.getSize());
            List<OrderApplyCcDto> orderApplyCcList = orderApplyCcMapper.findCcByEmpId(empId);
            PageInfo<OrderApplyCcDto> pageInfo1 = new PageInfo<>(orderApplyCcList);
            return PageHelpResultUtil.toPage(pageInfo1);
        }

    }

}
