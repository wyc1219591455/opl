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

    List<OrderSessionDetail> orderSessionDetailDtoList;

    CrmWorkOrderCriteria crmWorkOrderCriteria;

    OrderSession orderSession;
}
