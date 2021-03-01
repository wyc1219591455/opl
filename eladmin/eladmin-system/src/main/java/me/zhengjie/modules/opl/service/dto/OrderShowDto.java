package me.zhengjie.modules.opl.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShowDto {

    private CrmWorkOrderDto crmWorkOrderDto;

    private List<OrderSessionDto> orderSessionDtoList;


    private List<CrmWorkOrderDto> crmWorkOrderDtos;




}
