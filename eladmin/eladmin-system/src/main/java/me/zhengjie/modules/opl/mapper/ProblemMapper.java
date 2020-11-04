package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.Problem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Mapper
public interface ProblemMapper {
    /**
     * 发起人发起问题
     * @param problem
     * @return void
     */
    Integer createProblem(Problem problem);
}
