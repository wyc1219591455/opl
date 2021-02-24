package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.OrderSessionDetail;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderDto {

    List<OrderSessionDetail> orderSessionDetailDtoList;

    String userName;

    Integer orderType;

  @ApiModelProperty("  工单状态 1：新创建 2：待受理 3：处理中 4：已完成 5：已关闭 7：已取消")
    Integer orderStatus;

    @ApiModelProperty(" 描述")
    String description;
}
