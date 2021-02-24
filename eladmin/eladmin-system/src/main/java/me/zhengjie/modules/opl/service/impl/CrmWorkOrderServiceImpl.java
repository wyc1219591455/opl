package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.*;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: crm业务逻辑实现
 * @author: ming.cao
 * @create: 2021-01-13 19:25
 **/
@Service
@RequiredArgsConstructor
public class CrmWorkOrderServiceImpl implements CrmWorkOrderService {

    private final CrmWorkOrderMapper crmWorkOrderMapper;
    private final SubOrderMapper subOrderMapper;
    private final OrderSessionMapper orderSessionMapper;
    private final OrderSessionService orderSessionService;
    private final OrderSessionDetailMapper orderSessionDetailMapper;
    private final OrderApplyCcMapper orderApplyCcMapper;

    @Override
    public void insert(CrmWorkOrderCriteria crmWorkOrderCriteria) {
        //获取opl工单号
        String maxSerialNo = getOplMaxNo();
        crmWorkOrderCriteria.setSerialNo(maxSerialNo);
        crmWorkOrderCriteria.setOrderStatus(1);
        crmWorkOrderCriteria.setJobNumber(SecurityUtils.getCurrentUsername());
        crmWorkOrderCriteria.setCreateDateTime(new Timestamp(new Date().getTime()));
        crmWorkOrderCriteria.setOrderType(0);
        crmWorkOrderMapper.insert(crmWorkOrderCriteria);
        Integer orderId = crmWorkOrderCriteria.getId();
        OrderSession orderSession = new OrderSession();
        orderSession.setOrderType(1);
        orderSession.setTransId(orderId);
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setOriginalType(0);
        orderSessionMapper.insertSession(orderSession);
    }

    @Override
    public void treatOrder(OrderSession orderSession) {
        String jobNumber = SecurityUtils.getCurrentUsername();
        CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
        crmWorkOrderCriteria.setReceiver(jobNumber);
        crmWorkOrderCriteria.setOrderStatus(3);
        crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
        crmWorkOrderCriteria.setModifyPerson(jobNumber);
        crmWorkOrderCriteria.setId(orderSession.getTransId());
        crmWorkOrderMapper.update(crmWorkOrderCriteria);
        orderSession.setOrderType(2);
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setOriginalType(0);
        orderSessionMapper.insertSession(orderSession);

    }


    @Override
    public void transferOrder(TransferOrderDto transferOrderDto) {
        CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
        crmWorkOrderCriteria.setReceiver(transferOrderDto.getUserName());
        crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
        crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
        if (transferOrderDto.getOrderStatus() == 1) {
            crmWorkOrderCriteria.setOrderStatus(2);
        } else {
            crmWorkOrderCriteria.setOrderStatus(3);
        }
        crmWorkOrderMapper.update(crmWorkOrderCriteria);
        Integer orderId = crmWorkOrderCriteria.getId();
        OrderSession orderSession = new OrderSession();
        orderSession.setOrderType(3);
        orderSession.setDescription(transferOrderDto.getDescription());
        orderSession.setTransId(orderId);
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setOriginalType(0);
        orderSessionMapper.insertSession(orderSession);
        List<OrderSessionDetail> orderSessionDetailList = transferOrderDto.getOrderSessionDetailDtoList();
        Integer sessionId = orderSession.getId();
        for (OrderSessionDetail orderSessionDetailDto : orderSessionDetailList) {
            orderSessionDetailDto.setSessionId(sessionId);
            orderSessionDetailDto.setTransId(orderId);
            orderSessionDetailDto.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSessionDetailDto.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSessionDetailDto.setOriginalType(0);
            orderSessionDetailMapper.insertSessionDetail(orderSessionDetailDto);
        }
    }

    @Override
    public void remarks(OrderSession orderSession) {
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setOrderType(4);
        orderSessionMapper.insertSession(orderSession);
    }

    @Override
    public void sellOrder(OrderSession orderSession) {
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setOrderType(4);
        orderSessionMapper.insertSession(orderSession);
    }

    @Override
    public void update(CrmWorkOrderCriteria crmWorkOrderCriteria) {
        crmWorkOrderMapper.update(crmWorkOrderCriteria);
    }

    @Override
    public List<CrmWorkOrder> findCrmOrderById(Integer id) {
        return crmWorkOrderMapper.findCrmOrderById(id);
    }

    @Override
    public Map<String, Object> findAll(WorkOrderCriteria criteria, Pageable pageable) {
        PageHelper.startPage(pageable.getPage(), pageable.getSize());
        List<CrmWorkOrderDto> tempList = crmWorkOrderMapper.findAll(criteria);
        for (CrmWorkOrderDto crmWorkOrderDto : tempList) {
            crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId()));
            crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
        }
        PageInfo<CrmWorkOrderCriteria> pageInfo = new PageInfo(tempList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findCreatedByMe(WorkOrderCriteria criteria, Pageable pageable) {
        PageHelper.startPage(pageable.getPage(), pageable.getSize());
        String jobNumber = SecurityUtils.getCurrentUsername();
        criteria.setJobNumber(jobNumber);
        List<CrmWorkOrderDto> tempList = crmWorkOrderMapper.findCreatedByMe(criteria);
        for (CrmWorkOrderDto crmWorkOrderDto : tempList) {
            crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId()));
            crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
        }
        PageInfo<CrmWorkOrderCriteria> pageInfo = new PageInfo(tempList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findTreatByMe(WorkOrderCriteria criteria, Pageable pageable) {
        PageHelper.startPage(pageable.getPage(), pageable.getSize());
        String jobNumber = SecurityUtils.getCurrentUsername();
        criteria.setJobNumber(jobNumber);
        List<CrmWorkOrderDto> tempList = crmWorkOrderMapper.findTreatByMe(criteria);
        for (CrmWorkOrderDto crmWorkOrderDto : tempList) {
            crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId()));
            crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
        }
        PageInfo<CrmWorkOrderCriteria> pageInfo = new PageInfo(tempList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public String findSubOplByMaxId(Integer orderId) {

        return getSubOplMaxNo(orderId);
    }


    @Override
    public OrderShowDto findOrderBySerialNo(SerialDto serialDto) {
        OrderShowDto orderShowDto = new OrderShowDto();
        String jobNumber = SecurityUtils.getCurrentUsername();
        if (serialDto.getOrderType() == 0) {
            String serialNo = serialDto.getSerialNo();

            CrmWorkOrderDto crmWorkOrderDto = crmWorkOrderMapper.findOrderBySerialNo(serialNo);
            crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
            if (crmWorkOrderDto.getJobNumber().equals(jobNumber)) {
                crmWorkOrderDto.setEqualsCreate(1);
            } else crmWorkOrderDto.setEqualsCreate(0);
            List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSessionById(serialDto.getId());

            orderShowDto.setCrmWorkOrderDto(crmWorkOrderDto);
            orderShowDto.setOrderSessionDtoList(orderSessionDtoList);

        } else if (serialDto.getOrderType() == 1) {
            String serialNo = serialDto.getSerialNo();
            SubOrderDto subOrderDto = subOrderMapper.findSubOrderBySerialNo(serialNo);
            if (subOrderDto.getJobNumber().equals(jobNumber)) {
                subOrderDto.setEqualsCreate(1);
            } else subOrderDto.setEqualsCreate(0);
            List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSubSessionById(serialDto.getId());

            orderShowDto.setSubOrderDto(subOrderDto);
            orderShowDto.setOrderSessionDtoList(orderSessionDtoList);

        }
        return orderShowDto;
    }


    private String getOplMaxNo() {
        //获取crm同步到opl最大编号的数据
        List<CrmWorkOrder> maxCrmWorkOrderList = crmWorkOrderMapper.findOplByMaxId();
        //获取时间编号
        String tempMaxSerialNo = "";
        if (ObjectUtil.isNotEmpty(maxCrmWorkOrderList)) {
            tempMaxSerialNo = maxCrmWorkOrderList.get(0).getSerialNo();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String uid = "opl" + simpleDateFormat.format(new Date());
        System.out.println("uid" + uid);
        String serialNumber = "";
        //如果不为空，进行判断，如果有数据则判断是否是今天的数据。如果没有今天的数据初始化为第一个，否则+1；
        //如果为空，直接设为今天第一个
        if (ObjectUtil.isNotEmpty(maxCrmWorkOrderList)) {
               /* String tempMaxSerialNo = maxCrmWorkOrderList.get(0).getSerialNo();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String uid = "crm"+simpleDateFormat.format(new Date());*/
            System.out.println("序号" + tempMaxSerialNo.substring(0, 11));
            if (uid.equals(tempMaxSerialNo.substring(0, 11))) {
                Integer intNumber = Integer.parseInt(tempMaxSerialNo.substring(11));
                intNumber++;
                serialNumber = uid + String.format("%04d", Integer.valueOf(intNumber));

            } else {
                serialNumber = uid + "0001";
            }
        } else {
            serialNumber = uid + "0001";
        }

        System.out.println(serialNumber);
        return serialNumber;
    }

    private String getSubOplMaxNo(Integer orderId) {
        //获取crm同步到opl最大编号的数据
        List<SubOrder> maxSubOrder = subOrderMapper.findSubOplByMaxId(orderId);
        //获取时间编号
        String serialNumber = "";
        String tempMaxSerialNo = "";
        if (ObjectUtil.isNotEmpty(maxSubOrder)) {
            tempMaxSerialNo = maxSubOrder.get(0).getSerialNo();

            String uid = tempMaxSerialNo.substring(0, 15);
            Integer intNumber = Integer.parseInt(tempMaxSerialNo.substring(16));
            intNumber++;

            serialNumber = uid + "-" + String.format("%03d", Integer.valueOf(intNumber));
        } else {
            String uid = tempMaxSerialNo.substring(0, 15);
            serialNumber = uid + "-" + "001";
        }
        return serialNumber;
    }
}


