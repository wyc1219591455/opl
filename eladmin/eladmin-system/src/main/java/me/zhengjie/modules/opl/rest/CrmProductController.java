package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.CrmProductMapper;
import me.zhengjie.modules.opl.service.CrmProductService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: eladmin
 * @description: 产品控制
 * @author: ming.cao
 * @create: 2021-01-15 16:18
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL：产品管理")
@RequestMapping("/api/crmProduct")
public class CrmProductController {

    private final CrmProductService crmProductService;

    @Log("获取所有产品信息")
    @ApiOperation("获取所有产品信息")
    @GetMapping
    public Map<String ,Object> getCrmWorkOrder(CrmWorkOrderCriteria criteria, Pageable pageable){
        return  crmProductService.findAll(pageable);
    }


}
