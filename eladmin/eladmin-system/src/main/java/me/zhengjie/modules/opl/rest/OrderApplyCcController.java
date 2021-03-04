package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.OrderApplyCcService;


import me.zhengjie.modules.opl.service.dto.OrderApplyCcCriteria;
import me.zhengjie.modules.opl.service.dto.WorkOrderCriteria;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 工单抄送控制层
 * @author: ming.cao
 * @create: 2021-02-01 11:57
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL：工单抄送")
@RequestMapping("/api/orderApplyCc")
public class OrderApplyCcController {

    private final OrderApplyCcService orderApplyCcService;

    @Log("根据工单id获取抄送信息")
    @ApiOperation("根据工单id获取抄送信息")
    @GetMapping
    public Map<String,Object> getOrderApplyCcByOrderId(Pageable pageable, @NotNull @ApiParam("工单来源 0是主表 1是附表")Integer type, @NotNull @ApiParam("工单ID") Integer orderId) {
       return orderApplyCcService.findCcByOrderId(pageable,orderId,type);
    }

    @Log("抄送我的")
    @ApiOperation("抄送我的")
    @GetMapping("/getCcOrder")
    public Map<String,Object> findCcOrder(WorkOrderCriteria criteria, Pageable pageable){
        return orderApplyCcService.findCcOrder(criteria,pageable);
    }

    @Log("根据用户id获取抄送信息")
    @ApiOperation("根据用户id获取抄送信息")
    @GetMapping("/getCcByUserId")
    public Map<String,Object> getOrderApplyCcByUserId(Pageable pageable,String userId) {
        return orderApplyCcService.findCcByEmpId(pageable,userId);
    }

    @Log("新增工单抄送信息")
    @ApiOperation("新增工单抄送信息")
    @PostMapping
    public void addOrderApplyCcByOrderId(@RequestBody OrderApplyCc orderApplyCc) {
        orderApplyCcService.insert(orderApplyCc);
    }

    @Log("修改工单抄送信息")
    @ApiOperation("修改工单抄送信息")
    @PostMapping("/updateCcOrder")
    public void updateOrderApplyCcByOrderId(@RequestBody OrderApplyCcCriteria criteria){
        orderApplyCcService.updateOrderApplyCcByOrderId(criteria);

    }



}
