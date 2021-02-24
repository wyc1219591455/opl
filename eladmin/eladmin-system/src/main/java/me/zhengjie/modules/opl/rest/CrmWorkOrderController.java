package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.OrderSession;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.SerialDto;
import me.zhengjie.modules.opl.service.dto.TransferOrderDto;
import me.zhengjie.modules.opl.service.dto.WorkOrderCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: crm控制层
 * @author: ming.cao
 * @create: 2021-01-13 19:36
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL：工单管理")
@RequestMapping("/api/crmWorkOrder")
public class CrmWorkOrderController {

    private final CrmWorkOrderService crmWorkOrderService;

    @Log("工单报表")
    @ApiOperation("工单报表")
    @GetMapping
    public Object getCrmWorkOrder(WorkOrderCriteria criteria, Pageable pageable){
        return  crmWorkOrderService.findAll(criteria,pageable);
    }

    @Log("我发起的工单")
    @ApiOperation("我发起的工单")
    @GetMapping("/create")
    public Object getCrmWorkOrderByMe(WorkOrderCriteria criteria, Pageable pageable){
        return  crmWorkOrderService.findCreatedByMe(criteria,pageable);
    }

    @Log("我处理的工单")
    @ApiOperation("我处理的工单")
    @GetMapping("/treat")
    public Object getTreatOrderByMe(WorkOrderCriteria criteria, Pageable pageable){
        return  crmWorkOrderService.findTreatByMe(criteria,pageable);
    }

    @Log("创建工单")
    @ApiOperation("创建工单")
    @PostMapping
    public void insert(@RequestBody CrmWorkOrderCriteria crmWorkOrderCriteria){
        crmWorkOrderService.insert(crmWorkOrderCriteria);
    }

    @Log("修改工单")
    @ApiOperation("修改工单")
    @PutMapping
    public void update(@RequestBody CrmWorkOrderCriteria crmWorkOrderCriteria){

        crmWorkOrderService.update(crmWorkOrderCriteria);
    }

    @Log("根据工单号查找详情")
    @ApiOperation("根据工单号查找详情")
    @GetMapping("/detail")
    public Object findOrderBySerialNo(SerialDto serialDto){
        return  crmWorkOrderService.findOrderBySerialNo(serialDto);
    }
    @Log("确认受理")
    @ApiOperation("确认受理")
    @PutMapping("/treat")
    public void treatOrder(@RequestBody OrderSession orderSession){
        crmWorkOrderService.treatOrder(orderSession);
    }

    @Log("回复工单")
    @ApiOperation("回复工单")
    @PostMapping("/remarkt")
    public void remarks(@RequestBody OrderSession orderSession){
        crmWorkOrderService.remarks(orderSession);
    }

    @Log("转派工单")
    @ApiOperation("转派工单")
    @PutMapping("/transfer")
    public void transferOrder(@RequestBody TransferOrderDto transferOrderDto){
        crmWorkOrderService.transferOrder(transferOrderDto);
    }

    @Log("拆分工单")
    @ApiOperation("拆分工单")
    @PostMapping("/sell")
    public void sellOrder(@RequestBody SubOrder subOrder){
        crmWorkOrderService.sellOrder(subOrder);
    }

    @Log("测试")
    @ApiOperation("测试")
    @GetMapping("/sub")
    public Object test(Integer orderId){
        return crmWorkOrderService.findSubOplByMaxId(orderId);
    }
}
