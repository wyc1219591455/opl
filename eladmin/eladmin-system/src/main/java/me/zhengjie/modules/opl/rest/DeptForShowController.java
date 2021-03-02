package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.DeptForShowService;
import me.zhengjie.modules.opl.service.dto.DeptVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: eladmin
 * @description: 部门控制层
 * @author: ming.cao
 * @create: 2021-01-21 13:55
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL：部门展示管理")
@RequestMapping("/api/deptList")
public class DeptForShowController {

    private final DeptForShowService deptForShowService;

    @Log("获取部门列表")
    @ApiOperation("获取部门列表")
    @GetMapping
    public Map<String,Object> getDeptList(){
        return deptForShowService.getOrgData();
    }

    @Log("获取部门下拉框(支持组中查询用)")
    @ApiOperation("获取部门下拉框(支持组中查询用)")
    @GetMapping("/getDept")
    public Map<String,Object> getRelateDeptList(Pageable pageable, Integer catalogId, DeptVo deptVo){
        return deptForShowService.findDeptVoNotInCatalogId(pageable,catalogId,deptVo);
    }

    @Log("获取部门下拉框(关联一级部门)")
    @ApiOperation("获取部门下拉框(关联一级部门)")
    @GetMapping("/getDept2")
    public Map<String,Object> getRelateDeptList2(Pageable pageable, Integer catalogId, DeptVo deptVo){
        return deptForShowService.findDeptVoNotInCatalogId2(pageable,catalogId,deptVo);
    }



}
