package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.domain.OrderSessionDetail;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderDto {

    @ApiModelProperty("工单Id")
    private Integer orderId;

    @ApiModelProperty("工单受理人")
    private String receiver;

    @ApiModelProperty("详情")
    private String description;

    @ApiModelProperty("判断是主单还是子单 1代表主单，0代表子单")
    private Integer orderType;

    @ApiModelProperty("工单状态")
    private Integer orderStatus;

    List<OrderSessionDetail> orderSessionDetailDtoList;
}
