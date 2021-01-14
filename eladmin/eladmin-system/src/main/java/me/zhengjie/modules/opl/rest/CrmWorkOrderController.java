package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
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

    @Log("获取所有工单信息")
    @ApiOperation("获取所有工单信息")
    @GetMapping
    public Map<String ,Object> getCrmWorkOrder(CrmWorkOrderCriteria criteria, Pageable pageable){
        return  crmWorkOrderService.findAll(criteria,pageable);
    }

    @Log("新增工单")
    @ApiOperation("新增工单")
    @PostMapping
    public void insert(CrmWorkOrder crmWorkOrder){
        crmWorkOrderService.insert(crmWorkOrder);
    }

    @Log("修改工单")
    @ApiOperation("修改工单")
    @PutMapping
    public void update(CrmWorkOrder crmWorkOrder){
        crmWorkOrderService.update(crmWorkOrder);
    }

}
