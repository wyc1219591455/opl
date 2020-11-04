package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Problem;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
public interface ProblemService {

    /**
     * 发起问题
     * @param problem,count
     * @return void
     */
    void createProblem(Problem problem,Integer count);
}
