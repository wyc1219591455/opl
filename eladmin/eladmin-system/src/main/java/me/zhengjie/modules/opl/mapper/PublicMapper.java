package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.Model;
import me.zhengjie.modules.opl.domain.Project;
import me.zhengjie.modules.opl.domain.Status;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Mapper
public interface PublicMapper {
    /**
     * 查询所有型号
     * @param
     * @return List<Model>
     */
    List<Model> findAllModel();

    /**
     * 查询所有状态
     * @param
     * @return List<Status>
     */
    List<Status> findAllStatus();

    /**
     * 查询所有项目
     * @param
     * @return List<Project>
     */
    List<Project> findAllProject();

    /**
     * 查看今天已记录的问题数量
     * @param
     * @return Integer
     */
    Integer findCountProblemToday(String s);

    /**
     * 根据项目id获取项目负责人
     * @param projectId
     * @return java.lang.Integer
     */
    Integer findProPersonById(Integer projectId);
}
