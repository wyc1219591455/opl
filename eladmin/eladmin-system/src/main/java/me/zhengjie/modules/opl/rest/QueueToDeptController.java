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
import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria1;
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

    @Log("单个新增支持组明细")
    @ApiOperation("单个新增支持组明细")
    @PostMapping
    public void addRequestQueues1(@RequestBody QueuesToDeptCriteria1 criteria){
        queuesToDeptService.addQueuesToDept(criteria);
    }

    @Log("单个删除支持组明细")
    @ApiOperation("单个删除支持组明细")
    @DeleteMapping
    public void updateRequestQueues1(@RequestBody QueuesToDeptCriteria1 criteria){
        queuesToDeptService.deleteQueuesToDept(criteria);
    }


    @Log("批量新增支持组明细")
    @ApiOperation("批量新增支持组明细")
    @PostMapping("/batchInsert")
    public void batchAddRequestQueues(@RequestBody QueuesToDeptCriteria criteria){
        queuesToDeptService.batchAddQueuesToDept(criteria);
    }

    @Log("批量修改支持组明细")
    @ApiOperation("批量修改支持组明细")
    @DeleteMapping("/batchDelete")
    public void batchDeleteRequestQueues(@RequestBody QueuesToDeptCriteria criteria){
        queuesToDeptService.batchDeleteQueuesToDept(criteria);
    }


   /* @Log("批量修改支持组明细")
    @ApiOperation("批量修改支持组明细")
    @PutMapping("/batchUpdate")
    public void batchUpdateRequestQueues(@RequestBody QueuesToDeptCriteria criteria){
        queuesToDeptService.batchUpdateQueuesToDept(criteria);
    }*/



 /*   @Log("删除支持组明细")
    @ApiOperation("删除支持组明细")
    @DeleteMapping
    public void delRequestQueues( @RequestBody List<Integer> ids){
        queuesToDeptService.deleteQueuesToDept(ids);
    }*/

    @Log("获取所有部门人员信息")
    @ApiOperation("获取所有部门人员信息")
    @GetMapping("/deptUser")
    public Map<String ,Object> findAllUserByDeptId(@ApiParam("级别") @NotNull Integer level ,@ApiParam("支持组id") @NotNull Integer queuesId,@ApiParam("部门id") @NotNull Integer deptId, @ApiParam("人名称")  String name,Pageable pageable){
      return  queuesToDeptService.findAllUserByDeptId(pageable,level,queuesId,name,deptId);
    }

    @Log("根据服务分类条目获取部门信息")
    @ApiOperation("根据服务分类条目获取部门信息")
    @GetMapping("/findDeptByCatalogId")
    public Map<String ,Object> findDeptByCatalogId(@ApiParam("服务条目id") @NotNull Integer catalogId){
        return queuesToDeptService.findDeptByCatalogId(catalogId);
    }

}
