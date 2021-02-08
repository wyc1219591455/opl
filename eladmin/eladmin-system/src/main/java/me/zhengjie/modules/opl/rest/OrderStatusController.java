package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.OrderStatus;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.OrderStatusService;
import me.zhengjie.modules.opl.service.dto.OrderStatusCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 获取工单状态接口
 * @author: ming.cao
 * @create: 2021-02-08 11:06
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL：工单状态")
@RequestMapping("/api/orderStatus")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @Log("根据参数获取所有状态")
    @ApiOperation("根据参数获取所有状态")
    @GetMapping
    public Map<String,Object> findAllStatus(Pageable pageable, OrderStatusCriteria criteria){
        return orderStatusService.findAllStatus(pageable,criteria);
    }

    @Log("新增工单状态")
    @ApiOperation("新增工单状态")
    @PostMapping
    public void insert(OrderStatusCriteria orderStatus){
        orderStatusService.insert(orderStatus);
    }

    @Log("修改工单状态")
    @ApiOperation("修改工单状态")
    @PutMapping
    public void update(OrderStatusCriteria orderStatus){
        orderStatusService.update(orderStatus);
    }

    @Log("删除工单状态")
    @ApiOperation("删除工单状态")
    @DeleteMapping
    public void delete(List<Integer> ids){
        orderStatusService.delete(ids);
    }

    @Log("根据工单id获取工单状态")
    @ApiOperation("根据工单id获取工单状态")
    @GetMapping("/getStatusById")
    public Map<String,Object> getOrderStatusById(Integer id){
        return orderStatusService.findStatusById(id);
    }

}
