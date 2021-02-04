package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.OrderApplyCc;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 抄送展示类
 * @author: ming.cao
 * @create: 2021-02-01 10:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderApplyCcDto extends OrderApplyCc implements Serializable{

    @ApiModelProperty(value = "人员名称", position = 2)
    private String humanName;

    @ApiModelProperty(value = "人员编号", position = 2)
    private String userName;

    @ApiModelProperty(value = "邮件", position = 2)
    private String email;

}
