package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Model;
import me.zhengjie.modules.opl.domain.Project;
import me.zhengjie.modules.opl.domain.Status;
import me.zhengjie.modules.opl.service.PublicService;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.DeptDto;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import me.zhengjie.utils.PageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Api(tags = "OPL：公共接口")
@RestController
@RequestMapping(value = "/opl/public" , produces = "application/json;charset=utf8")
@RequiredArgsConstructor
public class PublicController {
  private final PublicService publicService;
  private final DeptService deptService;

    @Log("查询所有型号")
    @ApiOperation("查询所有型号")
    @GetMapping("/model")
    @AnonymousAccess
    public List<Model> findAllModel(){

        return publicService.findAllModel();
    }

    @Log("查询所有状态")
    @ApiOperation("查询所有状态")
    @GetMapping("/status")
    @AnonymousAccess
    public List<Status> findAllStatus(){

        return publicService.findAllStatus();
    }

    @Log("查询所有项目")
    @ApiOperation("查询所有项目")
    @GetMapping("/project")
    @AnonymousAccess
    public List<Project> findAllProject(){
      return publicService.findAllProject();
    }

    @Log("查询所有部门")
    @ApiOperation("查询所有部门")
    @GetMapping("/dept")
    @AnonymousAccess
  public List<DeptDto> findAllDept() throws Exception {
      DeptQueryCriteria criteria=new DeptQueryCriteria();
      return deptService.queryAll(criteria, true);
  }


  @GetMapping("/num")
  @AnonymousAccess
  public String getNum(String userName){
    return publicService.getNum(userName);
  }
}
