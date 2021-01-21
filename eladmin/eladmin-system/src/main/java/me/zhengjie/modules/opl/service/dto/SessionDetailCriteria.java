package me.zhengjie.modules.opl.service.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailCriteria {

    @ApiModelProperty(value = "orderSessionId")
    private long sessionId;

    @ApiModelProperty(value = "工单Id")
    private long transId;


}
