package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.system.domain.User;

import java.util.List;

/**
 * @program: eladmin
 * @description: 钉钉需要的数据
 * @author: ming.cao
 * @create: 2021-03-12 13:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderMessageToDingTip extends WorkOrderMessage{
    @ApiModelProperty("服务人")
    private String receiver;

    @ApiModelProperty("发送改的人")
    private List<String> sendToUserPhoneList;

    @ApiModelProperty("发送的消息")
    private String dingTipMessage;

  /*  public WorkOrderMessageToDingTip(WorkOrderMessage workOrderMessage,String receiver){
      return  new WorkOrderMessageToDingTip().set
    }*/

}
