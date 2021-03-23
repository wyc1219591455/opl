package me.zhengjie.modules.opl.rabbit;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.User;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.*;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.FatherToChild;
import me.zhengjie.utils.dingUtils.DingDingUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: eladmin
 * @description: SubDingTipReceiver
 * @author: ming.cao
 * @create: 2021-03-23 09:50
 **/
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "DingTipQueue2")//监听的队列名称 钉钉
public class SubDingTipReceiver {

    private final CrmWorkOrderMapper crmWorkOrderMapper;
    private final SubOrderMapper subOrderMapper;
    private final OrderSessionService orderSessionService;
    private final OrderApplyCcMapper orderApplyCcMapper;
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @RabbitHandler
    public void process( SubOrderDto subOrder) {
        CrmWorkOrderDto tempCrmWorkOrderDto = crmWorkOrderMapper.findOrderById(subOrder.getParentNo());
        //转派工单发送 发出邮件给执行服务者

        WorkOrderMessageToDingTip workOrderMessageToDingTip = new WorkOrderMessageToDingTip();

        workOrderMessageToDingTip.setTopic(subOrder.getTopic());
        workOrderMessageToDingTip.setDescribe(subOrder.getDescription());
        workOrderMessageToDingTip.setDept(subOrder.getDeptId()+"");
        //发起人
        workOrderMessageToDingTip.setSponsor(tempCrmWorkOrderDto.getCreatedPerson());
        //workOrderMessage.setUltimateCustomer(tempCrmWorkOrderDto.getUltimateCustomer());
        workOrderMessageToDingTip.setUltimateCustomer(tempCrmWorkOrderDto.getUltimateCustomer());
        workOrderMessageToDingTip.setCreateDate(tempCrmWorkOrderDto.getCreatedAt());
        workOrderMessageToDingTip.setHopeCompTime(tempCrmWorkOrderDto.getPlanCompTime());
        workOrderMessageToDingTip.setServiceCatalogId(tempCrmWorkOrderDto.getServiceCatalogId());
        workOrderMessageToDingTip.setReceiver(subOrder.getReceiverName());
        workOrderMessageToDingTip.setReceiverDept("");
        //获取服务人员部门
        if (ObjectUtil.isNotEmpty(subOrder.getReceiver())){
            Dept tempReceiverDept = userRepository.findByUsername(subOrder.getReceiver()).getDept();
            workOrderMessageToDingTip.setReceiverDept(tempReceiverDept.getName());
        }




        //获取完邮件模板后，发送邮件给对应支持（服务部门）组下的人
        List<User> userList =new ArrayList<>();
        List<String> empIdList = new ArrayList<>();
        empIdList.add(subOrder.getReceiver());
        userList = userMapper.findUserByEmpId(empIdList);
        switch (subOrder.getOperation()){
            case "拆分": //拆分
                //设置传入状态
                workOrderMessageToDingTip.setType("拆分");
                workOrderMessageToDingTip.setDingTipMessage("您有一条新的待处理工单！");
                break;
            case "转派":
                workOrderMessageToDingTip.setType("转派");
                workOrderMessageToDingTip.setDingTipMessage("您有一条新的待处理工单！");
                break;
            case "完成": //完成
                //设置传入状态
                workOrderMessageToDingTip.setType("完成");
                List<String> empIdList2 = new ArrayList<>();
                empIdList2.add(tempCrmWorkOrderDto.getJobNumber());
                userList = userMapper.findUserByEmpId(empIdList2);
                workOrderMessageToDingTip.setSendToUserPhoneList(userList.stream().map(e->e.getMobileNumber()).collect(Collectors.toList()));
                workOrderMessageToDingTip.setDingTipMessage("您的工单已完成");
                break;
            case "关闭": //完成
                //设置传入状态
                workOrderMessageToDingTip.setType("关闭");
                break;
            default:
                break;
        }


       /* List<String> empIdList = new ArrayList<>();
        empIdList.add(subOrder.getReceiver());*/
        // List<User> userList = userMapper.findUserByEmpId(empIdList);
        if (ObjectUtil.isNotEmpty(userList)){
            for (int i=0;i<userList.size();i++){
                if ("nina.ge@ape.cn".equals(userList.get(i).getEmail())){
                    userList.remove(i);
                }
            }

        }

        workOrderMessageToDingTip.setReceiver(subOrder.getJobNumber());
        workOrderMessageToDingTip.setSponsor(subOrder.getCreatedPerson());
        workOrderMessageToDingTip.setSendToUserPhoneList(userList.stream().map(e->e.getMobileNumber()).collect(Collectors.toList()));
        //发送钉钉
         dingTipForGrateOrder(workOrderMessageToDingTip);

    }


    /**
     * 钉钉推送（工单创建的时候发到默认服务台下人）
     * @param
     * @param
     * @param
     * @return
     */
    public String dingTipForGrateOrder(WorkOrderMessageToDingTip workOrderMessage) {

        String messages = workOrderMessage.getDingTipMessage();
        //通过服务分类条目查询其服务台中其他人员数据

        //用户手机号
        List<String> phoneNoList = workOrderMessage.getSendToUserPhoneList();

        //删除名单
        phoneNoList.remove("18036112924");

        //钉钉用户号
        List<String> dingDingUserIdList = new ArrayList<>();

        for (String phone : phoneNoList) {
            String userId = DingDingUtil.getUserIdByMobile(phone);
            dingDingUserIdList.add(userId);
        }
        //发送信息
        for (String userId : dingDingUserIdList) {
            DingDingUtil.sendDDMessage(userId,messages,workOrderMessage);
        }
        return dingDingUserIdList.toString();
    }



}
