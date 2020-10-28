package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.WorkOrder;
import me.zhengjie.modules.system.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName WorkOrderController.java
 * @Description TODO
 * @createTime 2020年10月28日 10:57:00
 */
@Api(tags = "流程：创建流程")
@RestController
@RequestMapping(value = "/" , produces = "application/json;charset=utf8")
@RequiredArgsConstructor
public class WorkOrderController {
    @Log("创建流程")
    @ApiOperation("创建流程")
    @PostMapping
    @AnonymousAccess
    public void create(@Validated @RequestBody WorkOrder workOrder){

    }
}
