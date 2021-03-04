package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.dto.*;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    @PostMapping("/remark")
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

    @Log("取消工单")
    @ApiOperation("取消工单")
    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody OrderSession orderSession){
         crmWorkOrderService.cancelOrder(orderSession);
    }

    @Log("完成工单")
    @ApiOperation("完成工单")
    @PostMapping("/complete")
    public void completeOrder(@RequestBody CompleteOrderDto completeOrderDto){
        crmWorkOrderService.completeOrder(completeOrderDto);
    }

    @Log("关闭工单")
    @ApiOperation("关闭工单")
    @PostMapping("/close")
    public void closeOrder(@RequestBody CloseOrderDto closeOrderDto){
        crmWorkOrderService.closeOrder(closeOrderDto);
    }

    @Log("修改抄送")
    @ApiOperation("修改抄送人")
    @PostMapping("/sub")
    public  void UpdateApplyCc(@RequestBody OrderApplyCcCriteria criteria) {
         crmWorkOrderService.UpdateApplyCc(criteria);
    }

    @Log("重开工单")
    @ApiOperation("重开工单")
    @PostMapping("/reset")
    public void resetOrder(@RequestBody OrderSession orderSession){
         crmWorkOrderService.resetOrder(orderSession);
    }

    @Log("查询人员")
    @ApiOperation("查询人员")
    @GetMapping("/user")
    public Object findUser( User user){
       return crmWorkOrderService.findUser(user);
    }

    @Log("查询人员分页")
    @ApiOperation("查询人员分页")
    @GetMapping("/newUser")
    public Object findNewUser( User user, Pageable pageable){
        return crmWorkOrderService.findUser(user,pageable);
    }

    @Log("服务台工单")
    @ApiOperation("服务台工单")
    @GetMapping("/service")
    public Object findServiceOrder(WorkOrderCriteria workOrderCriteria,Pageable pageable) {
        return crmWorkOrderService.findServiceOrder(workOrderCriteria,pageable);
    }

    @Log("修改计划解决日期")
    @ApiOperation("修改计划解决日期")
    @PostMapping("/changetime")
    public void changeTime(@RequestBody OrderSessionDetail orderSessionDetail) throws ParseException {
        crmWorkOrderService.changeTime(orderSessionDetail);
    }

}
