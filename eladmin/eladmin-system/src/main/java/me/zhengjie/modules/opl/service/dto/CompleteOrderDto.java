package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderDto {

    @ApiModelProperty("工单Id")
    private Integer orderId;

    @ApiModelProperty("改善措施（完成工单）")
    private String measures;

    @ApiModelProperty("完成状态（完成工单）（成功1，无法处理2，失败3）")
    private Integer completeType;

    @ApiModelProperty("原因分析  (完成工单)")
    private String reason;

    @ApiModelProperty("0代表主单，1代表子单")
    private Integer orderType;

    @ApiModelProperty("详情")
    private String description;

    @ApiModelProperty("附件")
    private String problemAttach;
}
