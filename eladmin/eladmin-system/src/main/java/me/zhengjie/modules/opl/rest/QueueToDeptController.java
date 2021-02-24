package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.service.QueuesToDeptService;
import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlCriteria;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 支持组控制层
 * @author: ming.cao
 * @create: 2021-01-22 15:30
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL:支持组到部门管理")
@RequestMapping("/api/queueToDept")
public class QueueToDeptController {

    private final QueuesToDeptService queuesToDeptService;

    @Log("获取所有支持组部门信息")
    @ApiOperation("获取所有支持组部门信息")
    @GetMapping
    public Map<String ,Object> getQueueToDept( Integer queueId, Pageable pageable){
        return  queuesToDeptService.findByQueueId(pageable,queueId);
    }

    @Log("新增支持组明细")
    @ApiOperation("新增支持组明细")
    @PostMapping
    public void addRequestQueues(@RequestBody QueuesToDeptCriteria criteria){
        queuesToDeptService.addQueuesToDept(criteria);
    }

    @Log("修改支持组明细")
    @ApiOperation("修改支持组明细")
    @PutMapping
    public void updateRequestQueues(@RequestBody QueuesToDeptCriteria criteria){
        queuesToDeptService.updateQueuesToDept(criteria);
    }

    @Log("删除支持组明细")
    @ApiOperation("删除支持组明细")
    @DeleteMapping
    public void delRequestQueues( @RequestBody List<Integer> ids){
        queuesToDeptService.deleteQueuesToDept(ids);
    }

    @Log("获取所有部门人员信息")
    @ApiOperation("获取所有部门人员信息")
    @GetMapping("/deptUser")
    public Map<String ,Object> findAllUserByDeptId(@ApiParam("支持组id") @NotNull Integer queuesId,@ApiParam("部门id") @NotNull Integer deptId, Pageable pageable){
      return  queuesToDeptService.findAllUserByDeptId(pageable,queuesId,deptId);
    }

    @Log("根据服务分类条目获取部门信息")
    @ApiOperation("根据服务分类条目获取部门信息")
    @GetMapping("/findDeptByCatalogId")
    public Map<String ,Object> findDeptByCatalogId(@ApiParam("服务条目id") @NotNull Integer catalogId){
        return queuesToDeptService.findDeptByCatalogId(catalogId);
    }

}
