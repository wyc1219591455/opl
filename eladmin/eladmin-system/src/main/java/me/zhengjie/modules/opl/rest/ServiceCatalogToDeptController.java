package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Api(tags = "服务分类权限关联（设定人员是否能看到）")
@RequestMapping("/api/association")
public class ServiceCatalogToDeptController {

    private final ServiceCatalogToDeptService serviceCatalogToDeptService;

    @Log("查询人员能看到的服务子分类")
    @ApiOperation("查询人员能看到的服务子分类")
    @GetMapping
    public Object findSubCatalogById(String userId) {
        return serviceCatalogToDeptService.findSubCatalogById(userId);
    }

    @Log("查询人员能看到的服务父分类")
    @ApiOperation("查询人员能看到的服务父分类")
    @GetMapping("/parent")
    public Object findParentCatalogById(String userId) {
        return serviceCatalogToDeptService.findParentCatalogById(userId);
    }

    @Log("查询人员能看到的所有服务分类")
    @ApiOperation("查询人员能看到的所有服务分类")
    @GetMapping("/all")
    public Object findALLCatalogById() {
        return serviceCatalogToDeptService.findAllCatalogById();
    }

    @Log("新增服务分类关联")
    @ApiOperation("新增服务分类关联")
    @PostMapping
    public void insertCatalogToDeptAssociation(@RequestBody ServiceCatalogToDept serviceCatalogToDept)  {
        serviceCatalogToDeptService.insertCatalogToDeptAssociation(serviceCatalogToDept);
    }
}
