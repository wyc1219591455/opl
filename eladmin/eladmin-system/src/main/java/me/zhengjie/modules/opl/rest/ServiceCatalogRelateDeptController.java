package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.service.ServiceCatalogRelateDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: eladmin
 * @description: 服务分类条目中的服务部门关联控制层
 * @author: ming.cao
 * @create: 2021-02-19 09:24
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "服务分类")
@RequestMapping("/api/serviceCatalogRelateDept")
public class ServiceCatalogRelateDeptController {

    private final ServiceCatalogRelateDeptService serviceCatalogRelateDeptService;

    @Log("查询服务分类条目下的服务部门")
    @ApiOperation("查询服务分类条目下的服务部门")
    @GetMapping
    public Map<String ,Object> findServiceCatalogRelateDept(Integer catalogId){

        return serviceCatalogRelateDeptService.findByCatalogId(catalogId);
    }

}
