package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
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

    @Log("新增工单")
    @ApiOperation("新增工单")
    @PostMapping
    public void insert( @RequestBody CrmWorkOrderCriteria crmWorkOrderCriteria){
        crmWorkOrderService.insert(crmWorkOrderCriteria);
    }

    @Log("修改工单")
    @ApiOperation("修改工单")
    @PutMapping
    public void update( @RequestBody CrmWorkOrderCriteria crmWorkOrderCriteria){

        crmWorkOrderService.update(crmWorkOrderCriteria);
    }

    @Log("根据工单号查找主单详情")
    @ApiOperation("根据工单号查找主单详情")
    @GetMapping("/detail")
    public Object findOrderBySerialNo(String SerialNo){
        return  crmWorkOrderService.findOrderBySerialNo(SerialNo);
    }

    @Log("根据工单号查找子单详情")
    @ApiOperation("根据工单号查找子单详情")
    @GetMapping("/subDetail")
    public Object findSubOrderBySerialNo(String SerialNo){
        return  crmWorkOrderService.findSubOrderBySerialNo(SerialNo);
    }
}
