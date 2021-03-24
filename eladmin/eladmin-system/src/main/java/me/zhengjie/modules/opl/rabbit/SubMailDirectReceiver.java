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
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.domain.User;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.*;
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

/**
 * @program: eladmin
 * @description: 监听MailDirectQueue2
 * @author: ming.cao
 * @create: 2021-03-20 15:07
 **/
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "MailDirectQueue2")//监听的队列名称 MailDirectQueue
public class SubMailDirectReceiver {

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
       // CrmWorkOrderDto tempCrmWorkOrderDto= JSON.parseObject(jsonStr,CrmWorkOrderDto.class);
        SubOrderDto subOrder= JSON.parseObject(JSON.toJSONString(jsonObject),SubOrderDto.class);
        CrmWorkOrderDto tempCrmWorkOrderDto = crmWorkOrderMapper.findOrderById(subOrder.getParentNo());
        //转派工单发送 发出邮件给执行服务者
        WorkOrderMessage workOrderMessage = new WorkOrderMessage();
        workOrderMessage.setTopic(subOrder.getTopic());
        workOrderMessage.setDescribe(subOrder.getDescription());
        workOrderMessage.setDept(subOrder.getDeptId()+"");
        //发起人
        workOrderMessage.setSponsor(tempCrmWorkOrderDto.getCreatedPerson());
        //workOrderMessage.setUltimateCustomer(tempCrmWorkOrderDto.getUltimateCustomer());
        workOrderMessage.setUltimateCustomer(tempCrmWorkOrderDto.getUltimateCustomer());
        workOrderMessage.setCreateDate(tempCrmWorkOrderDto.getCreatedAt());
        workOrderMessage.setHopeCompTime(tempCrmWorkOrderDto.getPlanCompTime());
        workOrderMessage.setServiceCatalogId(tempCrmWorkOrderDto.getServiceCatalogId());
        workOrderMessage.setReceiver(subOrder.getReceiverName());
        workOrderMessage.setReceiverDept("");
        //获取服务人员部门
        if (ObjectUtil.isNotEmpty(subOrder.getReceiver())){
            Dept tempReceiverDept = userRepository.findByUsername(subOrder.getReceiver()).getDept();
            workOrderMessage.setReceiverDept(tempReceiverDept.getName());
        }

        //设置传入状态
        workOrderMessage.setType("完成");


        //获取完邮件模板后，发送邮件给对应支持（服务部门）组下的人
        List<User> userList =new ArrayList<>();
        List<String> empIdList = new ArrayList<>();
        empIdList.add(subOrder.getReceiver());
        userList = userMapper.findUserByEmpId(empIdList);
        switch (subOrder.getOperation()){
            case "拆分": //拆分
                //设置传入状态
                workOrderMessage.setType("拆分");
               /* List<String> empIdList = new ArrayList<>();
                empIdList.add(subOrder.getReceiver());
                userList = userMapper.findUserByEmpId(empIdList);*/
                break;
            case "转派":
                workOrderMessage.setType("转派");
               /* List<String> empIdList2 = new ArrayList<>();
                empIdList2.add(subOrder.getReceiver());
                 userList = userMapper.findUserByEmpId(empIdList2);*/
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
                break;
            default:
                break;
        }

        WorkOrderMessageToDingTip workOrderMessageToDingTip = new WorkOrderMessageToDingTip();
        try{
            FatherToChild.fatherToChild(workOrderMessage,workOrderMessageToDingTip);
            workOrderMessageToDingTip.setReceiver(subOrder.getReceiver());

        }catch (Exception e){
            throw  new BadRequestException(e.getMessage());
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

        workOrderMessageToDingTip.setSendToUserPhoneList(userList.stream().map(e->e.getMobileNumber()).collect(Collectors.toList()));
        workOrderMessageToDingTip.setReceiver(subOrder.getJobNumber());
        workOrderMessageToDingTip.setSponsor(subOrder.getCreatedPerson());
        workOrderMessageToDingTip.setDingTipMessage("您的工单已完成");
        //发送钉钉
       // dingTipForGrateOrder(workOrderMessageToDingTip);

        //查询工单要传的数据
        Integer transId = subOrder.getId();

        //取下面的活动明细
        List<OrderSessionDto> dtoList= new ArrayList<>();
        String subSerialNo = subOrder.getSerialNo();
        SubOrderDto subOrderDto = subOrderMapper.findSubOrderBySerialNo(subSerialNo);

        if (subOrderDto.getJobNumber().equals(subOrder.getJobNumber())) {
            subOrderDto.setEqualsCreate(1);
        } else subOrderDto.setEqualsCreate(0);
        if (subOrderDto.getReceiver()!=null&subOrder.getJobNumber().equals(subOrderDto.getReceiver())) {
            subOrderDto.setEqualsReceiver(1);
        } else subOrderDto.setEqualsReceiver(0);

        List<OrderSessionDto> orderSessionDtoList = orderSessionService.findSubSessionById(transId);
        CrmWorkOrderDto crmWorkOrder=crmWorkOrderMapper.findOrderById(subOrderDto.getParentNo());

        //crmWorkOrder.setIsAllSubCom(isCom);
        crmWorkOrder.setSubOrderDtoList(subOrderMapper.findSubOrderByParentId(subOrderDto.getParentNo()));
        crmWorkOrder.setSubOrderDto(subOrderDto);
        List<SubOrderDto> crmWorkOrderDtoList=new ArrayList<>();
        crmWorkOrderDtoList.add(subOrderDto);

        dtoList=orderSessionDtoList;
        workOrderMessage.setSerialNo(subOrderDto.getSerialNo());
        workOrderMessage.setOrderShowDto(dtoList);

        //获取抄送人员的信息
        List<User> ccUserList = queuesToDeptMapper.findCcUserByTransId(transId);

        //人员循环
       // String operationUser =userRepository.findByUsername(SecurityUtils.getCurrentUsername()).getNickName();
        String operationUser = userRepository.findByUsername(subOrder.getNowUser()).getNickName();
        //邮件模板(所有)
        EmailVo emailInfoList = setMailInfo(workOrderMessage,subOrder.getSerialNo(),dtoList,operationUser,userList,ccUserList);

        //邮件发送

        if (userList.size()>0){
            emailService.send(emailInfoList,emailService.find());
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
