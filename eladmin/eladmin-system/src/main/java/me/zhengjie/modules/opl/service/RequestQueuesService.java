package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestQueues;
import me.zhengjie.modules.opl.service.dto.RequestQueuesCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 支持组
 * @author: ming.cao
 * @create: 2021-01-20 15:59
 **/
public interface RequestQueuesService {
    /**
    * @title: findAll
    * @description: 获取所有数据
    * @date: 2021/1/20 16:00
    * @author: ming.cao
    * @param
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @throws
    */
    Map<String ,Object> findAll(Pageable pageable);

    /**
     * @title: addRequestQueues
     * @description: 新增
     * @date: 2021/1/20 16:03
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void addRequestQueues(RequestQueuesCriteria criteria);

    /**
     * @title: updateRequestQueues
     * @description: 修改
     * @date: 2021/1/20 16:03
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void updateRequestQueues(RequestQueuesCriteria criteria);

    /**
     * @title: delete
     * @description: 批量删除
     * @date: 2021/1/20 16:03
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void delete(List<Integer> ids);
}
