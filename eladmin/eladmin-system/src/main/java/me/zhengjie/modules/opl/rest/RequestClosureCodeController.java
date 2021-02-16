package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.RequestClosureCodeService;
import me.zhengjie.modules.opl.service.dto.RequestClosureCodeCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 关闭类型控制层
 * @author: ming.cao
 * @create: 2021-02-09 10:53
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL:关闭类型管理")
@RequestMapping("/api/requestClosureCode")
public class RequestClosureCodeController {

    private final RequestClosureCodeService requestClosureCodeService;

    @Log("获取所有关闭类型")
    @ApiOperation("获取所有关闭类型")
    @GetMapping
    public Map<String,Object> findAll(Pageable pageable, RequestClosureCodeCriteria criteria){
       return requestClosureCodeService.findAll(pageable,criteria);
    }

    @Log("新增关闭类型")
    @ApiOperation("新增关闭类型")
    @PostMapping
    public void insert(RequestClosureCodeCriteria criteria){
        requestClosureCodeService.insert(criteria);
    }

    @Log("修改关闭类型")
    @ApiOperation("修改关闭类型")
    @PutMapping
    public void update(RequestClosureCodeCriteria criteria){
        requestClosureCodeService.update(criteria);
    }

    @Log("删除关闭类型")
    @ApiOperation("删除关闭类型")
    @DeleteMapping
    public void delete(List<Integer> ids){
        requestClosureCodeService.delete(ids);
    }

}
