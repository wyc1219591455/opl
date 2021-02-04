package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.service.dto.QueuesToDeptCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 支持组到部门
 * @author: ming.cao
 * @create: 2021-01-22 14:34
 **/
public interface QueuesToDeptService {
    /**
     * @title: findByQueueId
     * @description: 根据组id获取数据
     * @date: 2021/1/22 14:39
     * @author: ming.cao
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findByQueueId(Pageable pageable, Integer id);

    /**
     * @title: findDeptByCatalogId
     * @description: 根据服务分类条目获取部门数据
     * @date: 2021/2/2 15:32
     * @author: ming.cao
     * @param catalogId
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findDeptByCatalogId(Integer catalogId);

    /**
     * @title: findAllUserByDeptId
     * @description: 根据部门id获取所有人
     * @date: 2021/1/22 17:24
     * @author: ming.cao
     * @param pageable
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findAllUserByDeptId(Pageable pageable, Integer id);


    /**
     * @title: insert
     * @description: 批量新增
     * @date: 2021/1/22 14:40
     * @author: ming.cao
     * @param criterias
     * @return void
     * @throws
     */
    void addQueuesToDept(List<QueuesToDeptCriteria> criterias);

    /**
     * @title: update
     * @description: 批量修改
     * @date: 2021/1/22 14:40
     * @author: ming.cao
     * @param criterias
     * @return void
     * @throws
     */
    void updateQueuesToDept(List<QueuesToDeptCriteria> criterias);

    /**
     * @title: deleteQueuesToDept
     * @description: 批量删除
     * @date: 2021/1/22 15:06
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void deleteQueuesToDept(List<Integer> ids);

}
