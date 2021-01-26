package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestQueues;
import me.zhengjie.modules.opl.service.RequestQueuesService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.RequestQueuesCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 支持组控制层
 * @author: ming.cao
 * @create: 2021-01-20 16:26
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "OPL:支持组管理")
@RequestMapping("/api/requestQueues")
public class RequestQueuesController {

    private final RequestQueuesService requestQueuesService;

    @Log("获取所有产品信息")
    @ApiOperation("获取所有支持组信息")
    @GetMapping
    public Map<String ,Object> getRequestQueues(CrmWorkOrderCriteria criteria, Pageable pageable){
        return  requestQueuesService.findAll(pageable);
    }

    @Log("新增支持组")
    @ApiOperation("新增支持组")
    @PostMapping
    public void addRequestQueues(@RequestBody RequestQueuesCriteria criteria){
        requestQueuesService.addRequestQueues(criteria);
    }

    @Log("修改支持组")
    @ApiOperation("修改支持组")
    @PutMapping
    public void updateRequestQueues(@RequestBody RequestQueuesCriteria criteria){
        requestQueuesService.updateRequestQueues(criteria);
    }

    @Log("删除支持组")
    @ApiOperation("删除支持组")
    @DeleteMapping
    public void delRequestQueues(@RequestBody List<Integer> ids){
        requestQueuesService.delete(ids);
    }

}
