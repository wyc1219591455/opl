package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.jna.platform.win32.Sspi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.OrderApplyCcService;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.*;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.PageInfoUtils;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private final UserMapper userMapper;
    private final QueuesToDeptMapper queuesToDeptMapper;
    private final OrderApplyCcService orderApplyCcService;


    @Override
    public void insert(CrmWorkOrderCriteria crmWorkOrderCriteria) {
        //获取opl工单号
        String maxSerialNo = getOplMaxNo();
        crmWorkOrderCriteria.setSerialNo(maxSerialNo);
        crmWorkOrderCriteria.setOrderStatus(1);
//        crmWorkOrderCriteria.setJobNumber(SecurityUtils.getCurrentUsername());
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
        if(orderSession.getOriginalType()==0) {
            String jobNumber = SecurityUtils.getCurrentUsername();
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setReceiver(jobNumber);
            crmWorkOrderCriteria.setOrderStatus(3);
            crmWorkOrderCriteria.setRealOpTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setModifyPerson(jobNumber);
            crmWorkOrderCriteria.setId(orderSession.getTransId());

            crmWorkOrderMapper.update(crmWorkOrderCriteria);
        }
        else if(orderSession.getOriginalType()==1)
        {
            String jobNumber = SecurityUtils.getCurrentUsername();
            SubOrder subOrder=new SubOrder();
            subOrder.setOrderStatus(3);
            subOrder.setReceiver(jobNumber);
            subOrder.setId(orderSession.getTransId());
            subOrder.setRealOpTime(new Timestamp(new Date().getTime()));
            subOrderMapper.updateSubOrder(subOrder);
        }
            orderSession.setOrderType(2);
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setOriginalType(orderSession.getOriginalType());
            orderSessionMapper.insertSession(orderSession);


    }

    @Override
    public void changeTime(OrderSessionDetail orderSessionDetail) throws ParseException {
        orderSessionDetail.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSessionDetail.setCreateUserId(SecurityUtils.getCurrentUsername());
        CrmWorkOrderCriteria crmWorkOrderCriteria=new CrmWorkOrderCriteria();
        Date time = new SimpleDateFormat("yyyy-MM-dd").parse(orderSessionDetail.getNewValue());
        Timestamp planTime=new Timestamp(time.getTime());
        crmWorkOrderCriteria.setHopeCompTime(planTime);
        crmWorkOrderCriteria.setId(orderSessionDetail.getTransId());
        crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
        crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
        crmWorkOrderMapper.update(crmWorkOrderCriteria);
        OrderSession orderSession=new OrderSession();
        orderSession.setOriginalType(orderSessionDetail.getOriginalType());
        orderSession.setOrderType(11);
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setTransId(orderSessionDetail.getTransId());
        orderSessionMapper.insertSession(orderSession);
        orderSessionDetail.setSessionId(orderSession.getId());
        orderSessionDetailMapper.insertSessionDetail(orderSessionDetail);

    }



    @Override
    public void transferOrder(TransferOrderDto transferOrderDto) {
        if(transferOrderDto.getOrderType()==0) {
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setId(transferOrderDto.getOrderId());
            crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setReceiver(transferOrderDto.getReceiver());
            if (transferOrderDto.getOrderStatus() == 1 || transferOrderDto.getOrderStatus() == 2) {
                crmWorkOrderCriteria.setOrderStatus(2);
            } else {
                crmWorkOrderCriteria.setOrderStatus(3);
            }
            crmWorkOrderMapper.update(crmWorkOrderCriteria);
        }
        else  if(transferOrderDto.getOrderType()==0) {

            SubOrder subOrder=subOrderMapper.findSubOrderById(transferOrderDto.getOrderId());
            isTransfer(subOrder.getIsTransfer());
            subOrder.setReceiver(transferOrderDto.getReceiver());
            if (transferOrderDto.getOrderStatus() == 1 || transferOrderDto.getOrderStatus() == 2) {
                subOrder.setOrderStatus(2);
            } else {
                subOrder.setOrderStatus(3);
            }
            subOrderMapper.updateSubOrder(subOrder);
        }
            OrderSession orderSession = new OrderSession();
            orderSession.setDescription(transferOrderDto.getDescription());
            orderSession.setOrderType(3);
            orderSession.setTransId(transferOrderDto.getOrderId());
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setOriginalType(transferOrderDto.getOrderType());
            orderSessionMapper.insertSession(orderSession);
            List<OrderSessionDetail> orderSessionDetailList = transferOrderDto.getOrderSessionDetailDtoList();
            Integer sessionId = orderSession.getId();
            for (OrderSessionDetail orderSessionDetailDto : orderSessionDetailList) {
                orderSessionDetailDto.setSessionId(sessionId);
                orderSessionDetailDto.setTransId(transferOrderDto.getOrderId());
                orderSessionDetailDto.setCreateUserId(SecurityUtils.getCurrentUsername());
                orderSessionDetailDto.setCreateDateTime(new Timestamp(new Date().getTime()));
                orderSessionDetailDto.setOriginalType(transferOrderDto.getOrderType());
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
    public void UpdateApplyCc(OrderApplyCcCriteria criteria) {
       orderApplyCcService.updateOrderApplyCcByOrderId(criteria);
       OrderSession orderSession = new OrderSession();
       orderSession.setTransId(criteria.getTransId());
       orderSession.setOriginalType(criteria.getOriginalType());
       orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
       orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
       orderSession.setOrderType(9);
       String description="";
       if(criteria.getEmpId()!=null&criteria.getEmpId().size()>0) {
           description = "修改了抄送人:";
           List<User> userList = userMapper.findUserByEmpId(criteria.getEmpId());
           for (User user : userList) {
               description = description + user.getName() + "   ";
           }

       }
       else description="清空了抄送人";
        orderSession.setDescription(description);
       orderSessionMapper.insertSession(orderSession);

    }

    @Override
    public void sendBackOrder(OrderSession orderSession) {
        if (orderSession.getOriginalType() == 0) {
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setOrderStatus(9);
            crmWorkOrderCriteria.setId(orderSession.getTransId());
            crmWorkOrderMapper.update(crmWorkOrderCriteria);
            orderSession.setOrderType(13);
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSessionMapper.insertSession(orderSession);

        }
    }


    @Override
    public void sellOrder(SubOrder subOrder) {
        subOrder.setJobNumber(SecurityUtils.getCurrentUsername());
        subOrder.setCreateTime(new Timestamp(new Date().getTime()));
        String serialNo=getSubOplMaxNo(subOrder.getParentNo());
        subOrder.setSerialNo(serialNo);
        subOrder.setOrderStatus(1);
        subOrder.setOrderType(1);
        subOrderMapper.insertSubOrder(subOrder);
        OrderSession orderSession=new OrderSession();
        orderSession.setTransId(subOrder.getId());
        orderSession.setOriginalType(1);
        orderSession.setOrderType(5);
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setDescription(subOrder.getDescription());
        orderSessionMapper.insertSession(orderSession);
        OrderSession orderSession2=new OrderSession();
        orderSession2.setTransId(subOrder.getParentNo());
        orderSession2.setOriginalType(0);
        orderSession2.setOrderType(5);
        orderSession2.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession2.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession2.setDescription(subOrder.getDescription());
        orderSessionMapper.insertSession(orderSession2);


    }


    @Override
    public void closeOrder(CloseOrderDto closeOrderDto){
        if(closeOrderDto.getOrderType()==0)
        {
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setFinishUserId(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setFinishDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setId(closeOrderDto.getOrderId());
            crmWorkOrderCriteria.setOrderStatus(5);
            crmWorkOrderCriteria.setCloseScore(closeOrderDto.getCloseScore());

            crmWorkOrderMapper.update(crmWorkOrderCriteria);
            OrderSession orderSession=new OrderSession();
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSession.setOrderType(7);
            String assess="";
            switch(closeOrderDto.getCloseScore())
            {
                case 1:assess="差评";
                break;
                case 2:assess="中评";
                break;
                case 3:assess="好评";
                break;
            }
            String description="评价"+":"+assess+""+closeOrderDto.getDescription();
            orderSession.setDescription(description);
            orderSession.setProblemAttach(closeOrderDto.getProblemAttach());
            orderSession.setTransId(closeOrderDto.getOrderId());
            orderSession.setOriginalType(closeOrderDto.getOrderType());
            orderSessionMapper.insertSession(orderSession);
        }

    }



    @Override
    public void cancelOrder(OrderSession orderSession){
        if(orderSession.getOriginalType()==0)
        {
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setCancelDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setCancelUserId(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setOrderStatus(7);
            crmWorkOrderCriteria.setId(orderSession.getTransId());
            crmWorkOrderMapper.update(crmWorkOrderCriteria);
        }
        else if(orderSession.getOriginalType()==1){
            SubOrder subOrder=new SubOrder();
            subOrder.setOrderStatus(7);
            subOrder.setId(orderSession.getTransId());
            subOrder.setCancelDateTime(new Timestamp(new Date().getTime()));
            subOrder.setCancelUserId(SecurityUtils.getCurrentUsername());
            subOrderMapper.updateSubOrder(subOrder);
        }
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setOrderType(8);
        orderSessionMapper.insertSession(orderSession);
    }

    @Override
    public void completeOrder(CompleteOrderDto completeOrderDto) {
        if(completeOrderDto.getOrderType()==0){
            CrmWorkOrderDto crmWorkOrderDto = crmWorkOrderMapper.findOrderById(completeOrderDto.getOrderId());
            if(crmWorkOrderDto.getIsAllSubCom()==false)
            {
                throw new BadRequestException("该工单存在尚未关闭的子单！");
            }
            CrmWorkOrderCriteria crmWorkOrderCriteria=new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setId(completeOrderDto.getOrderId());
            crmWorkOrderCriteria.setMeasures(completeOrderDto.getMeasures());
            crmWorkOrderCriteria.setReason(completeOrderDto.getReason());
            crmWorkOrderCriteria.setCompleteType(completeOrderDto.getCompleteType());
            crmWorkOrderCriteria.setOrderStatus(4);
            crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setFinishDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setFinishUserId(SecurityUtils.getCurrentUsername());
            crmWorkOrderMapper.update(crmWorkOrderCriteria);
            OrderSession orderSession=new OrderSession();
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSession.setOriginalType(0);
            orderSession.setTransId(completeOrderDto.getOrderId());
            orderSession.setOrderType(6);
            String description="原因分析"+":"+completeOrderDto.getReason()+"改善对策"+":"+completeOrderDto.getMeasures();
            orderSession.setDescription(description);
            orderSession.setProblemAttach(completeOrderDto.getProblemAttach());
            orderSessionMapper.insertSession(orderSession);

        }
        else if(completeOrderDto.getOrderType()==1){
            SubOrder subOrder=new SubOrder();
            subOrder.setId(completeOrderDto.getOrderId());
            subOrder.setOrderStatus(4);
            subOrder.setFinishDateTime(new Timestamp(new Date().getTime()));
            subOrder.setFinishUserId(SecurityUtils.getCurrentUsername());
            subOrderMapper.updateSubOrder(subOrder);
            OrderSession orderSession=new OrderSession();
            orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
            orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
            orderSession.setOriginalType(1);
            orderSession.setTransId(completeOrderDto.getOrderId());
            orderSession.setOrderType(6);
            orderSession.setDescription(completeOrderDto.getDescription());
            orderSession.setProblemAttach(completeOrderDto.getProblemAttach());
            orderSessionMapper.insertSession(orderSession);
        }

    }

    @Override
    public void resetOrder(OrderSession orderSession){
        if(orderSession.getOriginalType()==0)
        {
            CrmWorkOrderCriteria crmWorkOrderCriteria = new CrmWorkOrderCriteria();
            crmWorkOrderCriteria.setModifyPerson(SecurityUtils.getCurrentUsername());
            crmWorkOrderCriteria.setModifyDateTime(new Timestamp(new Date().getTime()));
            crmWorkOrderCriteria.setOrderStatus(8);
            crmWorkOrderCriteria.setId(orderSession.getTransId());
            crmWorkOrderMapper.update(crmWorkOrderCriteria);
        }
        else if(orderSession.getOriginalType()==1){
            SubOrder subOrder=new SubOrder();
            subOrder.setOrderStatus(8);
            subOrder.setId(orderSession.getTransId());
            subOrderMapper.updateSubOrder(subOrder);
        }
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setOrderType(10);
        orderSessionMapper.insertSession(orderSession);
    }


    @Override
    public void update(CrmWorkOrderCriteria crmWorkOrderCriteria) {
        crmWorkOrderMapper.update(crmWorkOrderCriteria);
        OrderSession orderSession =new OrderSession();
        orderSession.setTransId(crmWorkOrderCriteria.getId());
        orderSession.setCreateDateTime(new Timestamp(new Date().getTime()));
        orderSession.setCreateUserId(SecurityUtils.getCurrentUsername());
        orderSession.setOriginalType(crmWorkOrderCriteria.getOrderType());
        orderSession.setOrderType(12);
        orderSessionMapper.insertSession(orderSession);
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
    public Map<String,Object> findServiceOrder(WorkOrderCriteria workOrderCriteria,Pageable pageable) {
        PageHelper.clearPage();

        String jobNumber = SecurityUtils.getCurrentUsername();
        Integer userId= Math.toIntExact(userMapper.findIdByUsername(jobNumber));
        List<Integer> catalogList=queuesToDeptMapper.findServiceCatalogByUserId(userId);
        List<CrmWorkOrderDto> tempList=new ArrayList<>();
        if(catalogList!=null&catalogList.size()>0) {
            workOrderCriteria.setSubCatalogList(catalogList);
            tempList = crmWorkOrderMapper.findServiceOrder(workOrderCriteria);
            for (CrmWorkOrderDto crmWorkOrderDto : tempList) {
                crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId()));
                crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
            }
        }



        return PageHelpResultUtil.toPage(PageInfoUtils.listToPageInfo(tempList, pageable.getPage(), pageable.getSize()));
    }

    @Override
    public List<User> findUser(User user) {
     return userMapper.findUser(user);
    }

    @Override
    public  List<User> findUserBy(List<String> userIds) {

        return userMapper.findUserByEmpId(userIds);
    }

    @Override
    public Map<String,Object> findUser(User user, Pageable pageable) {
        PageHelper.startPage(pageable.getPage(), pageable.getSize());

        List<User> userList = userMapper.findUser(user);
        PageInfo<User> pageInfo = new PageInfo(userList);
        return PageHelpResultUtil.toPage(pageInfo);

    }



    @Override
    public OrderShowDto findOrderBySerialNo(SerialDto serialDto) {
        OrderShowDto orderShowDto = new OrderShowDto();
        String jobNumber = SecurityUtils.getCurrentUsername();
        if (serialDto.getOrderType() == 0) {
            String serialNo = serialDto.getSerialNo();

            CrmWorkOrderDto crmWorkOrderDto = crmWorkOrderMapper.findOrderBySerialNo(serialNo);
            crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(crmWorkOrderDto.getId()));
            crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(crmWorkOrderDto.getId()));
            Boolean isCom=isComplete(crmWorkOrderDto.getId());
            crmWorkOrderDto.setIsAllSubCom(isCom);
            if (crmWorkOrderDto.getJobNumber().equals(jobNumber)) {
                crmWorkOrderDto.setEqualsCreate(1);
            } else crmWorkOrderDto.setEqualsCreate(0);
            if (crmWorkOrderDto.getReceiver()!=null&&jobNumber.equals(crmWorkOrderDto.getReceiver())) {
                crmWorkOrderDto.setEqualsReceiver(1);
            } else crmWorkOrderDto.setEqualsReceiver(0);
            List<CrmWorkOrderDto> crmWorkOrderDtoList=new ArrayList<>();
            List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSessionById(serialDto.getId());
            crmWorkOrderDtoList.add(crmWorkOrderDto);
            orderShowDto.setCrmWorkOrderDtos(crmWorkOrderDtoList);
            orderShowDto.setCrmWorkOrderDto(crmWorkOrderDto);
            orderShowDto.setOrderSessionDtoList(orderSessionDtoList);

        } else if (serialDto.getOrderType() == 1) {
            String serialNo = serialDto.getSerialNo();
            SubOrderDto subOrderDto = subOrderMapper.findSubOrderBySerialNo(serialNo);

            if (subOrderDto.getJobNumber().equals(jobNumber)) {
                subOrderDto.setEqualsCreate(1);
            } else subOrderDto.setEqualsCreate(0);
            if (subOrderDto.getReceiver()!=null&jobNumber.equals(subOrderDto.getReceiver())) {
                subOrderDto.setEqualsReceiver(1);
            } else subOrderDto.setEqualsReceiver(0);

            List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSubSessionById(serialDto.getId());
            CrmWorkOrderDto crmWorkOrder=crmWorkOrderMapper.findOrderById(subOrderDto.getParentNo());
            Boolean isCom=isComplete(crmWorkOrder.getId());
            crmWorkOrder.setIsAllSubCom(isCom);
            crmWorkOrder.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(subOrderDto.getParentNo()));
            crmWorkOrder.setSubOrderDto(subOrderDto);
            List<CrmWorkOrderDto> crmWorkOrderDtoList=new ArrayList<>();
            crmWorkOrderDtoList.add(crmWorkOrder);
            orderShowDto.setCrmWorkOrderDto(crmWorkOrder);
            orderShowDto.setCrmWorkOrderDtos(crmWorkOrderDtoList);
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
            List<CrmWorkOrder> maxCrmWorkOrderList = crmWorkOrderMapper.findCrmOrderById(orderId);
            tempMaxSerialNo=maxCrmWorkOrderList.get(0).getSerialNo();
            String uid = tempMaxSerialNo.substring(0, 15);
            serialNumber = uid + "-" + "001";
        }
        return serialNumber;
    }


    private void isTransfer(Integer id) {

        // 若查询结果非空，即控制器下存在使用中的读头
        if (id==0) {
            throw new BadRequestException("该子工单不可转派！");
        }
    }


    private Boolean isComplete(Integer id) {

        Integer subNum=subOrderMapper.findSubNum(id);
        Integer subComNum=subOrderMapper.findComSubNum(id);
        if(subNum==subComNum)return true;
        else return false;

    }
}


