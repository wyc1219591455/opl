package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.OrderSessionService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 19:36
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "工单主表明细")
@RequestMapping("/api/orderSession")
public class OrderSessionController {
    private final OrderSessionService orderSessionService;

    @Log("根据工单ID获取明细")
    @ApiOperation("根据工单ID获取明细")
    @GetMapping
    public Object getCrmWorkOrder(@ApiParam("工单主表ID")Integer transId){
        return  orderSessionService.findSessionById(transId);
    }



}
