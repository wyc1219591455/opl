package me.zhengjie.modules.opl.rabbit;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;


import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
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
import me.zhengjie.utils.FatherToChild;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "MailDirectQueue")//监听的队列名称 MailDirectQueue
public class MailDirectReceiver {

    private final CrmWorkOrderMapper crmWorkOrderMapper;
    private final SubOrderMapper subOrderMapper;
    private final OrderSessionService orderSessionService;
    private final OrderApplyCcMapper orderApplyCcMapper;
    private final UserMapper userMapper;
    private final QueuesToDeptMapper queuesToDeptMapper;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @RabbitHandler
    public void process( JSONObject jsonObject) {

        String str = JSON.toJSONString(jsonObject);
        CrmWorkOrderDto tempCrmWorkOrderDto= JSON.parseObject(str,CrmWorkOrderDto.class);
        //CrmWorkOrderDto tempCrmWorkOrderDto = null;
        //System.out.println(tempCrmWorkOrderDto.getReceiver()+"1232132132154214");
        WorkOrderMessage workOrderMessage = new WorkOrderMessage();
        workOrderMessage.setTopic(tempCrmWorkOrderDto.getTopic());
        workOrderMessage.setDescribe(tempCrmWorkOrderDto.getProblemDesc());
        workOrderMessage.setDept(tempCrmWorkOrderDto.getDeptName());
        //发起人
        workOrderMessage.setSponsor(tempCrmWorkOrderDto.getCreatedPerson());
        workOrderMessage.setUltimateCustomer(tempCrmWorkOrderDto.getUltimateCustomer());
        workOrderMessage.setCreateDate(tempCrmWorkOrderDto.getCreatedAt());
        workOrderMessage.setHopeCompTime(tempCrmWorkOrderDto.getHopeCompTime());
        workOrderMessage.setServiceCatalogId(tempCrmWorkOrderDto.getServiceCatalogId());
        workOrderMessage.setReceiver(tempCrmWorkOrderDto.getReceiverName());
        workOrderMessage.setReceiverDept("");


    /*    WorkOrderMessageToDingTip workOrderMessageToDingTip = new WorkOrderMessageToDingTip();
        try{
            FatherToChild.fatherToChild(workOrderMessage,workOrderMessageToDingTip);
            workOrderMessageToDingTip.setReceiver(tempCrmWorkOrderDto.getReceiver());

        }catch (Exception e){
            throw  new BadRequestException(e.getMessage());
        }
        List<String> empIdList = new ArrayList<>();
        empIdList.add(tempCrmWorkOrderDto.getReceiver());
        List<User> userList = userMapper.findUserByEmpId(empIdList);
        workOrderMessageToDingTip.setSendToUserPhoneList(userList.stream().map(e->e.getMobileNumber()).collect(Collectors.toList()));
        workOrderMessageToDingTip.setReceiver(tempCrmWorkOrderDto.getJobNumber());
        workOrderMessageToDingTip.setSponsor(tempCrmWorkOrderDto.getCreatedPerson());
        workOrderMessageToDingTip.setDingTipMessage("您的工单已完成");
        //发送钉钉
        dingTipForGrateOrder(workOrderMessageToDingTip);*/



        //获取服务人员部门
        if (ObjectUtil.isNotEmpty(tempCrmWorkOrderDto.getReceiver())){
            Dept tempReceiverDept = userRepository.findByUsername(tempCrmWorkOrderDto.getReceiver()).getDept();
            workOrderMessage.setReceiverDept(tempReceiverDept.getName());
        }
       /* //设置传入状态
        workOrderMessage.setType("完成");*/
        //查询工单要传的数据

        Integer transId = tempCrmWorkOrderDto.getId();

        //取下面的活动明细
        List<OrderSessionDto> dtoList= new ArrayList<>();
        String serialNo =tempCrmWorkOrderDto.getSerialNo();

        CrmWorkOrderDto crmWorkOrderDto = crmWorkOrderMapper.findOrderBySerialNo(serialNo);
        if (subOrderMapper.findSubOrderByParentId(transId)!=null){
            crmWorkOrderDto.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(transId));
        }
        crmWorkOrderDto.setOrderApplyCcDtos(orderApplyCcMapper.findCcByTransId(transId));


        List<CrmWorkOrderDto> crmWorkOrderDtoList=new ArrayList<>();
        List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSessionById(transId);
        crmWorkOrderDtoList.add(crmWorkOrderDto);

        dtoList=orderSessionDtoList;

        workOrderMessage.setOrderShowDto(dtoList);

        //获取完邮件模板后，发送邮件给对应支持（服务部门）组下的人
        List<User> userList =new ArrayList<>();
        switch (tempCrmWorkOrderDto.getOperation()){
            case "创建": //新创建
                //设置传入状态
                workOrderMessage.setType("新建");
                userList = queuesToDeptMapper.findUserInDefaultQueueByCatalogId(crmWorkOrderDto.getServiceCatalogId());
                break;
            case "转派":
                workOrderMessage.setType("转派");
                List<String> empIdList = new ArrayList<>();
                empIdList.add(tempCrmWorkOrderDto.getReceiver());
                userList = userMapper.findUserByEmpId(empIdList);
                break;
            case "完成": //完成
                //设置传入状态
                workOrderMessage.setType("完成");
                List<String> empIdList2 = new ArrayList<>();
                empIdList2.add(tempCrmWorkOrderDto.getCreatedPerson());
                userList = userMapper.findUserByEmpId(empIdList2);
                break;
            case "关闭": //完成
                //设置传入状态
                workOrderMessage.setType("关闭");
                List<String> empIdList3 = new ArrayList<>();
                empIdList3.add(tempCrmWorkOrderDto.getReceiver());
                userList = userMapper.findUserByEmpId(empIdList3);
                break;
            default:
                break;
        }




        if (ObjectUtil.isNotEmpty(userList)){
            for (int i=0;i<userList.size();i++){
                if ("nina.ge@ape.cn".equals(userList.get(i).getEmail())){
                    userList.remove(i);
                }
            }

        }

        //获取抄送人员的信息
        List<User> ccUserList = queuesToDeptMapper.findCcUserByTransId(transId);

        //List<User> userList = queuesToDeptMapper.findUserInDefaultQueueByCatalogId(crmWorkOrderCriteria.getCatalogId());
        //人员循环
        String operationUser =userRepository.findByUsername(tempCrmWorkOrderDto.getNowUser()).getNickName();



        //邮件模板(所有)
        EmailVo emailInfoList = setMailInfo(workOrderMessage,serialNo,dtoList,operationUser,userList,ccUserList);

        //邮件发送
        if (userList.size()>0) {
            emailService.send(emailInfoList, emailService.find());
        }
    }


    public EmailVo setMailInfo(WorkOrderMessage workOrderMessage,String orderSerialNo,List<OrderSessionDto> orderShowDto,String operationUser,List<User> userList,List<User> ccUserList) {
        //邮件模板list
        List<EmailVo> emailVoList = new ArrayList<>();


        //  获取到所有的人员的邮箱信息

        // user.getEmail()
        EmailVo emailVo = new EmailVo();
        emailVo.setSubject("【OPL服务平台】");
        Map<String, Object> data = new HashMap<>(14);
        //所有人的邮件
        List<String> userEmailList = userList.stream().map(e->e.getEmail()).collect(Collectors.toList());

        //设置邮件发部参数
        data.put("topic",workOrderMessage.getTopic() );
        data.put("describe",workOrderMessage.getDescribe());
        data.put("sponsor",workOrderMessage.getSponsor());
        data.put("dept",workOrderMessage.getDept());
        data.put("serviceDept",workOrderMessage.getReceiverDept());
        data.put("serviceUser",workOrderMessage.getReceiver());

        data.put("hopeCompTime",workOrderMessage.getHopeCompTime());
        data.put("serviceCatalogName",workOrderMessage.getServiceCatalogName());
        data.put("ultimateCustomer",workOrderMessage.getUltimateCustomer());
        //设置操作人
        data.put("operationUser",operationUser);
        data.put("orderType",workOrderMessage.getType());
        data.put("serialNo",orderSerialNo);
        data.put("orderShowDto",orderShowDto);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/orderEmail.ftl");
        emailVo.setContent(template.render(data));
        //设置邮件收件人
        userEmailList.remove("nina.ge@ape.cn");
        emailVo.setTos(userEmailList);

        List<String> removeList = new ArrayList<>();
        removeList.add("nina.ge@ape.cn");
        //设置抄送人
        if (ccUserList!=null&&ccUserList.size()>0){
            List<String> ccUserListStr=ccUserList.stream().map(e->e.getEmail()).collect(Collectors.toList());
            ccUserListStr.removeAll(removeList);
            emailVo.setCcs(ccUserListStr);
        }
        emailVoList.add(emailVo);

        return emailVo;
    }


}
