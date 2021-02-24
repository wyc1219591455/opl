package me.zhengjie.modules.opl.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.TrequestCategory;
import me.zhengjie.modules.opl.service.ServiceCatalogToCategoryService;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "工单分类")
@RequestMapping("/api/category")
public class ServiceCatalogToCategoryController {

    private final ServiceCatalogToCategoryService serviceCatalogToCategoryService;

    @Log("新增服务分类")
    @ApiOperation("新增服务分类")
    @PostMapping("/category")
    public void insertCategory(@RequestBody TrequestCategory trequestCategory) {
        serviceCatalogToCategoryService.insertCategory(trequestCategory);
    }


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

    @Log("查询正在使用的工单分类根节点")
    @ApiOperation("查询正在使用的工单分类根节点")
    @GetMapping("/root")
    public Map<String ,Object> findRootCatalog(Pageable pageable) {
        return serviceCatalogToCategoryService.findRootCatalog(pageable);
    }

    @Log("根据ID查找工单分类")
    @ApiOperation("根据ID查找工单分类")
    @GetMapping("/search")
    public Object findCategoryById(Integer id) {
        return serviceCatalogToCategoryService.findCategoryById(id);
    }

    @Log("根据ID修改")
    @ApiOperation("根据ID修改")
    @PutMapping("/update")
    public void updateCategory(@RequestBody TrequestCategory trequestCategory){
         serviceCatalogToCategoryService.updateCategory(trequestCategory);
    }

    @Log("根据ID删除")
    @ApiOperation("根据ID删除")
    @PutMapping("/delete")
    public void deleteCategoryById(@RequestBody  List<Integer> idList){
        serviceCatalogToCategoryService.deleteCategoryById(idList);
    }

    @Log("获取当前的parent分类List")
    @ApiOperation("获取当前的parent分类List")
    @GetMapping("/list")
    public Object getcategoryList(TrequestCategoryDto trequestCategoryDto){
        return serviceCatalogToCategoryService.getcategoryList(trequestCategoryDto);
    }
}
