package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.RequestQueuesDetlService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.RequestQueuesCriteria;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-20 19:30
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL:支持组明细管理")
@RequestMapping("/api/requestQueuesDetl")
public class RequestQueuesDetlController {

    private final RequestQueuesDetlService requestQueuesDetlService;

    @Log("获取所有支持组明细信息")
    @ApiOperation("获取所有支持组明细信息")
    @GetMapping
    public Map<String ,Object> getRequestQueues(@ApiParam("支持组id") Integer id, Pageable pageable){
        return  requestQueuesDetlService.findByQueuesId(pageable,id);
    }

    @Log("新增支持组明细")
    @ApiOperation("新增支持组明细")
    @PostMapping
    public void addRequestQueues(@RequestBody RequestQueuesDetlCriteria criteria){
        requestQueuesDetlService.addRequestQueuesDetl(criteria);
    }


/*    public void addRequestQueues(){

    }*/

    @Log("修改支持组明细")
    @ApiOperation("修改支持组明细")
    @PutMapping
    public void updateRequestQueues(@RequestBody RequestQueuesDetlCriteria criteria){
        requestQueuesDetlService.updateRequestQueuesDetl(criteria);
    }

    @Log("删除支持组明细")
    @ApiOperation("删除支持组明细")
    @DeleteMapping
    public void delRequestQueues(@RequestBody List<Integer> ids){
        requestQueuesDetlService.deleteRequestQueuesDetl(ids);
    }

    @Log("获取所有人员明细信息")
    @ApiOperation("获取所有人员明细信息")
    @GetMapping("/getUserForShow")
    public Map<String ,Object> getUserForShowQueues( Pageable pageable,@ApiParam("支持组id") Long deptId){
       return requestQueuesDetlService.getUserForShow(pageable,deptId,null);
    }
}
