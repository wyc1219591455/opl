package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.internal.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import me.zhengjie.modules.opl.service.dto.CatalogToDeptCriteria;
import me.zhengjie.modules.opl.service.dto.CatalogToDeptCriteria1;
import me.zhengjie.modules.opl.service.dto.UserForShow;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;


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

  /*  @Log("新增服务分类关联")
    @ApiOperation("新增服务分类关联")
    @PostMapping
    public void insertCatalogToDeptAssociation(@RequestBody ServiceCatalogToDept serviceCatalogToDept)  {
        serviceCatalogToDeptService.insertCatalogToDeptAssociation(serviceCatalogToDept);
    }*/

    @Log("新增服务分类关联")
    @ApiOperation("新增服务分类关联")
    @PostMapping
    public void addCatalogToDept(@RequestBody  CatalogToDeptCriteria1 criteria)  {
        serviceCatalogToDeptService.addCatalogToDept(criteria);
    }

    @Log("删除服务分类关联")
    @ApiOperation("删除服务分类关联")
    @DeleteMapping
    public void deleteCatalogToDept(@RequestBody CatalogToDeptCriteria1 criteria)  {
        serviceCatalogToDeptService.deleteCatalogToDept(criteria);
    }

    @Log("新增服务分类关联")
    @ApiOperation("新增服务分类关联")
    @PostMapping("/batchInsert")
    public void batchAddCatalogToDept(@RequestBody CatalogToDeptCriteria criteria)  {
        serviceCatalogToDeptService.batchAddCatalogToDept(criteria);
    }

    @Log("批量删除服务分类关联")
    @ApiOperation("批量删除服务分类关联")
    @DeleteMapping("/batchDelete")
    public void batchDeleteCatalogToDept(@RequestBody CatalogToDeptCriteria criteria)  {
        serviceCatalogToDeptService.batchDeleteCatalogToDept(criteria);
    }


    @Log("批量获取所有部门人员信息")
    @ApiOperation("批量获取所有部门人员信息")
    @GetMapping("/deptUser")
    public Map<String ,Object> findAllUserByDeptId(@ApiParam("级别") @NotNull Integer level , @ApiParam("服务组id") @NotNull Integer catalogId, @ApiParam("部门id") @NotNull Integer deptId,@ApiParam("人名称")String name ,Pageable pageable){
         return  serviceCatalogToDeptService.findAllUserByDeptId(pageable,level,catalogId,deptId,name);

    }


    @Log("筛选所有人员模糊查询")
    @ApiOperation("筛选所有人员模糊查询")
    @GetMapping("/allUser")
    public Map<String ,Object> findAllUser(@ApiParam("级别") @NotNull Integer level ,@ApiParam("支持组id") @NotNull Integer queuesId, @ApiParam("人名称")  String name){
        return serviceCatalogToDeptService.findAllUserByName(level,queuesId,name);

    }

}
