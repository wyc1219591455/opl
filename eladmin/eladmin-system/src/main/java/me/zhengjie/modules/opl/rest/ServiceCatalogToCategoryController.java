package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.ServiceCatalogToCategoryService;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "工单分类")
@RequestMapping("/api/category")
public class ServiceCatalogToCategoryController {

    private final ServiceCatalogToCategoryService serviceCatalogToCategoryService;

    @Log("查询服务分类的工单分类")
    @ApiOperation("查询服务分类的工单分类")
    @GetMapping
    public Object findAllCatalogById(Integer catalogId) {
        return serviceCatalogToCategoryService.findAllCatalogById(catalogId);
    }

    @Log("查询全部工单分类")
    @ApiOperation("查询全部工单分类")
    @GetMapping("/all")
    public Map<String ,Object> findAllCatalog(Pageable pageable) {
        return serviceCatalogToCategoryService.findAllCatalog(pageable);
    }

    @Log("查询正在使用的工单分类")
    @ApiOperation("查询正在使用的工单分类")
    @GetMapping("/used")
    public Map<String ,Object> findUsedCatalog(Pageable pageable) {
        return serviceCatalogToCategoryService.findUsedCatalog(pageable);
    }

}
