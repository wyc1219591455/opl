package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.OrderStatus;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.OrderStatusMapper;
import me.zhengjie.modules.opl.service.OrderStatusService;
import me.zhengjie.modules.opl.service.dto.OrderStatusCriteria;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 工单状态业务逻辑实现
 * @author: ming.cao
 * @create: 2021-02-08 10:48
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true, rollbackFor = Exception.class)
public class OrderStatusServiceImpl implements OrderStatusService {

   private final OrderStatusMapper orderStatusMapper;

    @Override
    public void insert(OrderStatusCriteria criteria) {
        //非空校验
        isNotEmptyTest(criteria);
        //唯一性校验

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setDesc(criteria.getDesc());
        orderStatus.setName(criteria.getName());
        orderStatus.setColor(criteria.getColor());
        orderStatus.setIsCloseTimer(criteria.getIsCloseTimer());
        orderStatus.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderStatus.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderStatusMapper.insert(orderStatus);
    }

    @Override
    public void update(OrderStatusCriteria criteria) {
        //非空校验
        isNotEmptyTest(criteria);
        //唯一性校验

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setDesc(criteria.getDesc());
        orderStatus.setName(criteria.getName());
        orderStatus.setColor(criteria.getColor());
        orderStatus.setIsCloseTimer(criteria.getIsCloseTimer());
        orderStatus.setModifyUserId(SecurityUtils.getCurrentUsername());
        orderStatus.setModifyDateTime(new Timestamp(new Date().getTime()));
        orderStatusMapper.update(orderStatus);
    }

    @Override
    public void delete(List<Integer> ids) {
        try{
            for (Integer id : ids) {
                orderStatusMapper.delete(id);
            }

        }catch (Exception e){
            throw new BadRequestException("删除失败！");
        }

    }

    @Override
    public Map<String,Object> findStatusById(Integer id) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        List<OrderStatus> tempList=orderStatusMapper.findStatusById(id);

        map.put("content",tempList);
        map.put("totalElements",tempList.size());
        return map;
    }

    @Override
    public Map<String,Object> findAllStatus(Pageable pageable, OrderStatusCriteria criteria) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<OrderStatus> tempList = orderStatusMapper.findAllStatus(criteria);
        PageInfo<OrderStatus> pageInfo1 = new PageInfo<>(tempList);
        return PageHelpResultUtil.toPage(pageInfo1);
    }

    /**
     * 非空校验
     */
    private void isNotEmptyTest(OrderStatusCriteria criteria){
        if (ObjectUtil.isEmpty(criteria.getName())){
            throw new BadRequestException("名称不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria.getDesc())){
            throw new BadRequestException("描述不能为空！");
        }
    }

    /**
     * 唯一性校验
     */
    private void isOnlyTest(OrderStatusCriteria criteria){

    }
}
