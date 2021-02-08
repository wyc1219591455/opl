package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.OrderApplyCc;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.OrderApplyCcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public Map<String,Object> getOrderApplyCcByOrderId(Pageable pageable,Integer orderId){
       return orderApplyCcService.findCcByOrderId(pageable,orderId);
    }

    @Log("新增工单抄送信息")
    @ApiOperation("新增工单抄送信息")
    @PostMapping
    public void addOrderApplyCcByOrderId(OrderApplyCc orderApplyCc){
        orderApplyCcService.insert(orderApplyCc);
    }

}
