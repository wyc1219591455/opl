package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseOrderDto {

    @ApiModelProperty("工单Id")
    private Integer orderId;

    @ApiModelProperty("0代表主单，1代表子单")
    private Integer orderType;

    @ApiModelProperty("详情")
    private String description;

    @ApiModelProperty("附件")
    private String problemAttach;

    @ApiModelProperty("评分")
    private Integer closeScore;
}
