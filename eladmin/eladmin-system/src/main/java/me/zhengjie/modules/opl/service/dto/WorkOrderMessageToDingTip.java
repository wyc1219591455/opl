package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  /*  public WorkOrderMessageToDingTip(WorkOrderMessage workOrderMessage,String receiver){
      return  new WorkOrderMessageToDingTip().set
    }*/

}
