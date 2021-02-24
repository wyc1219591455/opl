package me.zhengjie.modules.opl.service.impl;


import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.OrderApplyCcMapper;
import me.zhengjie.modules.opl.mapper.SubOrderMapper;
import me.zhengjie.modules.opl.service.OrderApplyCcService;
import me.zhengjie.modules.opl.service.dto.*;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.PageInfoUtils;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    private final SubOrderMapper subOrderMapper;

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
    @Transactional
    public void updateOrderApplyCcByOrderId(OrderApplyCcCriteria criteria) {

        //先删除
        orderApplyCcMapper.deleteByTransId(criteria.getTransId());

        List<OrderApplyCc> orderApplyCcList = new ArrayList<>();

        //非空校验
        if (ObjectUtil.isNotEmpty( criteria.getEmpId() )){

            for (Integer empId : criteria.getEmpId()) {
                OrderApplyCc orderApplyCc = new OrderApplyCc();
                //设置请求主表编号
                orderApplyCc.setTransId(criteria.getTransId());
                //设置人员id
                orderApplyCc.setEmpId(empId);
                //设置表来源
                orderApplyCc.setOriginalType(criteria.getOriginalType());
                //设置抄送模式 1为发布
                orderApplyCc.setCcType(1);
                orderApplyCc.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
                orderApplyCc.setCreateDateTime(new Timestamp(new Date().getTime()));
                orderApplyCcList.add(orderApplyCc);
            }
            //批量新增
            orderApplyCcMapper.batchInsert(orderApplyCcList);

        }


    }

    @Override
    public Map<String, Object> findCcByOrderId(Pageable pageable, Integer orderId, Integer orderType) {

        if (pageable != null && pageable.getPage() == -1) {
            List<OrderApplyCcDto> orderApplyCcList = new ArrayList<>();
            if (orderType==0) {  //来自主表
                orderApplyCcList = orderApplyCcMapper.findCcByTransId(orderId);
            }else{
                orderApplyCcList = orderApplyCcMapper.findCcByTransIdFromSubOrder(orderId);
            }
                Map<String, Object> map = new LinkedHashMap<>(2);
                map.put("content", orderApplyCcList);
                map.put("totalElements", orderApplyCcList.size());
                return map;
        } else {
            PageHelper.startPage(pageable.getPage(), pageable.getSize());
            List<OrderApplyCcDto> orderApplyCcList = new ArrayList<>();
            if (orderType==0) {  //来自主表
                orderApplyCcList = orderApplyCcMapper.findCcByTransId(orderId);
            }else{
                orderApplyCcList = orderApplyCcMapper.findCcByTransIdFromSubOrder(orderId);
            }
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

    @Override
    public Map<String, Object> findCcOrder(WorkOrderCriteria criteria, Pageable pageable) {

        Integer empId= SecurityUtils.getCurrentUserId().intValue();

        List<CrmWorkOrderDto> totalOrderList = new ArrayList<>();

        //抄送主表的数据
        List<CrmWorkOrderDto> crmWorkOrderDtoList = orderApplyCcMapper.findMasterOrderCcByEmpId2(criteria,empId);
        //主表数据中拼接子表
        for (CrmWorkOrderDto crmWorkOrderDto : crmWorkOrderDtoList) {
            List<SubOrderDto> subOrderDtoList = subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId());
            crmWorkOrderDto.setSubOrderDtoList(subOrderDtoList);
        }

        //抄送子表的数据
        List<SubOrderDto> subOrderDtoList = orderApplyCcMapper.findSubOrderCcByEmpId(empId);
        //子表的父类工单集合
        List<CrmWorkOrderDto> parentList = new ArrayList<>();
        //查询父类
        for (SubOrderDto subOrderDto : subOrderDtoList) {
            List<CrmWorkOrderDto> crmWorkOrderDtoList1 = subOrderMapper.findParentWorkOrderDtoById(criteria,subOrderDto.getId());
            //主工单数据
            CrmWorkOrderDto tempParent = crmWorkOrderDtoList1.get(0);
            //将子表工单数据分装成list
            List<SubOrderDto> tempSubOrderDtoList = new ArrayList<>();
            //父类工单中插入子表工单list
            tempSubOrderDtoList.add(subOrderDto);
            tempParent.setSubOrderDtoList(tempSubOrderDtoList);
            //父类保存
            parentList.add(crmWorkOrderDtoList1.get(0));
        }

        //子表去重
        for (CrmWorkOrderDto workOrderDto1 : parentList) {
            for (CrmWorkOrderDto workOrderDto2 : crmWorkOrderDtoList) {
                if (workOrderDto1.getId()==workOrderDto2.getId()){
                    parentList.remove(workOrderDto1);
                }
            }
        }

        //插入主表数据
        totalOrderList.addAll(crmWorkOrderDtoList);
        //插入子类的数据
        totalOrderList.addAll(parentList);

        //list按照时间排序
        List<CrmWorkOrderDto> collect =totalOrderList.stream().sorted(Comparator.comparing(CrmWorkOrderDto::getCreateAt)).collect(Collectors.toList());
       /* totalOrderList.stream().
                collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(o->o.getId()))), ArrayList::new));*/
        //return PageHelpResultUtil.toPage(PageInfoUtils.listToPageInfo(totalOrderList, pageable.getPage(), pageable.getSize()));
   return PageHelpResultUtil.toPage(PageInfoUtils.listToPageInfo(collect, pageable.getPage(), pageable.getSize()));
    }





}
