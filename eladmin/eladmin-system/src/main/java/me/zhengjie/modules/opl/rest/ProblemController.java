/*
package me.zhengjie.modules.opl.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.opl.domain.Model;
import me.zhengjie.modules.opl.domain.Problem;
import me.zhengjie.modules.opl.service.ProblemService;
import me.zhengjie.modules.opl.service.PublicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

*/
/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 *//*

@Api(tags = "OPL：发起问题")
@RestController
@RequestMapping(value = "/opl" , produces = "application/json;charset=utf8")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final PublicService publicService;
    @Log("发起问题")
    @ApiOperation("发起问题")
    @PostMapping("/create")
    @AnonymousAccess
    public void createProblem(@Validated @RequestBody Problem problem){

        //查找当天已存在的问题个数，进行累计
        Integer count = publicService.findCountProblemToday(new Date());
        problemService.createProblem(problem,count+1);
    }
}
*/
