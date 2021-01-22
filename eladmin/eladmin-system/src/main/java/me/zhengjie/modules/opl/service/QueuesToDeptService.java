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
     * @description: findByQueueId
     * @date: 2021/1/22 14:39
     * @author: ming.cao
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     */
    Map<String,Object> findByQueueId(Pageable pageable, Integer id);

    /**
     * @title: findAllUserByDeptId
     * @description: findAllUserByDeptId
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
     * @description: insert
     * @date: 2021/1/22 14:40
     * @author: ming.cao
     * @param criterias
     * @return void
     * @throws
     */
    void addQueuesToDept(List<QueuesToDeptCriteria> criterias);

    /**
     * @title: update
     * @description: update
     * @date: 2021/1/22 14:40
     * @author: ming.cao
     * @param criterias
     * @return void
     * @throws
     */
    void updateQueuesToDept(List<QueuesToDeptCriteria> criterias);

    /**
     * @title: deleteQueuesToDept
     * @description: deleteQueuesToDept
     * @date: 2021/1/22 15:06
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void deleteQueuesToDept(List<Integer> ids);

}
