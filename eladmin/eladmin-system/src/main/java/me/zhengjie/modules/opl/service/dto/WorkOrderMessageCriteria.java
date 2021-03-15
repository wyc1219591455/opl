package me.zhengjie.modules.opl.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 邮件要获取数据时要传的参
 * @author: ming.cao
 * @create: 2021-03-05 15:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderMessageCriteria extends SerialDto implements Serializable {

    private String name;

}
