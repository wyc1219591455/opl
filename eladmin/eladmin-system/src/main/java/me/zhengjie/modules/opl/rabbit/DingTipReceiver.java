package me.zhengjie.modules.opl.rabbit;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.modules.opl.domain.User;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto;
import me.zhengjie.modules.opl.service.dto.OrderSessionDto;
import me.zhengjie.modules.opl.service.dto.WorkOrderMessage;
import me.zhengjie.modules.opl.service.dto.WorkOrderMessageToDingTip;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.SecurityUtils;
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
 * @description: 钉钉提醒
 * @author: ming.cao
 * @create: 2021-03-22 15:14
 **/

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "DingTipQueue")//监听的队列名称 钉钉
public class DingTipReceiver {
    private final CrmWorkOrderMapper crmWorkOrderMapper;
    private final SubOrderMapper subOrderMapper;
    private final OrderSessionService orderSessionService;
    private final OrderApplyCcMapper orderApplyCcMapper;
    private final UserMapper userMapper;
    private final QueuesToDeptMapper queuesToDeptMapper;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @RabbitHandler
    public void process( CrmWorkOrderDto tempCrmWorkOrderDto ) {

        WorkOrderMessageToDingTip workOrderMessageToDingTip = new WorkOrderMessageToDingTip();

        //获取服务人员部门
        if (ObjectUtil.isNotEmpty(tempCrmWorkOrderDto.getReceiver())){
            Dept tempReceiverDept = userRepository.findByUsername(tempCrmWorkOrderDto.getReceiver()).getDept();
            workOrderMessageToDingTip.setReceiverDept(tempReceiverDept.getName());
        }
        if (tempCrmWorkOrderDto.getOrderType()==0){
            CrmWorkOrderDto crmWorkOrderDto = crmWorkOrderMapper.findOrderBySerialNo(tempCrmWorkOrderDto.getSerialNo());

            crmWorkOrderDto.setNowUser(tempCrmWorkOrderDto.getNowUser());
            crmWorkOrderDto.setOperation("创建");
            //获取完邮件模板后，发送邮件给对应支持（服务部门）组下的人
            List<User> userList =new ArrayList<>();
            switch (tempCrmWorkOrderDto.getOperation()){
                case "创建": //新创建
                    //设置传入状态
                    workOrderMessageToDingTip.setType("新建");
                    userList = crmWorkOrderMapper.findUserByCatalogId(crmWorkOrderDto.getServiceCatalogId()) ;
                    workOrderMessageToDingTip.setDingTipMessage("您有一条新的待处理工单！");
                    //userList = queuesToDeptMapper.findUserInDefaultQueueByCatalogId(crmWorkOrderDto.getServiceCatalogId());
                    break;
                case "转派":
                    workOrderMessageToDingTip.setType("转派");
                    List<String> empIdList = new ArrayList<>();
                    empIdList.add(tempCrmWorkOrderDto.getReceiver());
                    userList = userMapper.findUserByEmpId(empIdList);
                    workOrderMessageToDingTip.setDingTipMessage("您有一条新的待处理工单！");
                    break;
                case "完成": //完成
                    //设置传入状态
                    workOrderMessageToDingTip.setType("完成");
                    List<String> empIdList2 = new ArrayList<>();
                    empIdList2.add(tempCrmWorkOrderDto.getCreatedPerson());
                    userList = userMapper.findUserByEmpId(empIdList2);
                    workOrderMessageToDingTip.setDingTipMessage("您的工单已完成！");
                    break;
                default:
                    break;
            }
            workOrderMessageToDingTip.setTopic(tempCrmWorkOrderDto.getTopic());
            workOrderMessageToDingTip.setHopeCompTime(tempCrmWorkOrderDto.getPlanCompTime());
            workOrderMessageToDingTip.setSponsor(crmWorkOrderDto.getCreatedPerson());
            workOrderMessageToDingTip.setReceiver(crmWorkOrderDto.getReceiver());
            workOrderMessageToDingTip.setSendToUserPhoneList(userList.stream().map(e->e.getMobileNumber()).collect(Collectors.toList()));
            //workOrderMessageToDingTip.setDingTipMessage("您有一条新的待处理工单！");
            dingTipForGrateOrder(workOrderMessageToDingTip);
        }





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
