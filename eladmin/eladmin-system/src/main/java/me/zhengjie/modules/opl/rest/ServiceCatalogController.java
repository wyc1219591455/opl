package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalog;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;
import me.zhengjie.modules.opl.service.ServiceCatalogService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "服务分类")
@RequestMapping("/api/serviceCatalog")
public class ServiceCatalogController {

    private final ServiceCatalogService serviceCatalogService;

    @Log("查询全部服务分类")
    @ApiOperation("查询全部服务分类")
    @GetMapping
    public Map<String, Object> findAllCatalog(Pageable pageable) {
        return serviceCatalogService.findAllCatalog(pageable);
    }

    @Log("查询父服务分类")
    @ApiOperation("查询父服务分类")
    @GetMapping("/parent")
    public Map<String, Object> findParentCatalog(Pageable pageable) {
        return serviceCatalogService.findParentCatalog(pageable);
    }

    @Log("根据parentID查询子服务分类")
    @ApiOperation("根据parentID查询子服务分")
    @GetMapping("/sub")
    public Map<String, Object> findSubCatalog(Integer parentId,Pageable pageable) {
        return serviceCatalogService.findSubCatalog(parentId,pageable);
    }

    @Log("新增服务分类父表")
    @ApiOperation("新增服务分类父表")
    @PostMapping("/parent")
    public void insertParentCatalog(@RequestBody ServiceCatalog serviceCatalog) {
        serviceCatalogService.insertCatalog(serviceCatalog);
    }
    @Log("新增服务分类子表")
    @ApiOperation("新增服务分类子表")
    @PostMapping("/sub")
    public void insertSubCatalog(@RequestBody SubServiceCatalog subServiceCatalog) {
        serviceCatalogService.insertSubCatalog(subServiceCatalog);
    }

    @Log("修改服务分类父表")
    @ApiOperation("修改服务分类父表")
    @PutMapping("/parent")
    public void updateParentCatalog(@RequestBody ServiceCatalog serviceCatalog) {
        serviceCatalogService.updateParentCatalog(serviceCatalog);
    }

    @Log("修改服务分类子表")
    @ApiOperation("修改服务分类子表")
    @PutMapping("/sub")
    public void updateSubCatalog(@RequestBody SubServiceCatalog subServiceCatalog) {
        serviceCatalogService.updateSubCatalog(subServiceCatalog);
    }



}
