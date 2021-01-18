package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Model;
import me.zhengjie.modules.opl.domain.Project;
import me.zhengjie.modules.opl.domain.Status;

import java.util.Date;
import java.util.List;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
public interface PublicService {
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
      * 查询当天已记录的问题数量
      * @param
      * @return java.lang.Integer
      */
     Integer findCountProblemToday(Date date);

     String getNum(String userName);

}
