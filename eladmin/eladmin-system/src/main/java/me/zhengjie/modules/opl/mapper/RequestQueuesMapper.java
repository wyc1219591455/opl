package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.RequestQueues;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 支持组
 * @author: ming.cao
 * @create: 2021-01-20 15:44
 **/
@Mapper
public interface RequestQueuesMapper {
    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/20 15:49
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.RequestQueues>
     * @throws
     */
    List<RequestQueues> findAll() ;

    /**
     * @title: insert
     * @description: insert
     * @date: 2021/1/20 15:49
     * @author: ming.cao
     * @param requestQueues
     * @return void
     * @throws
     */
    void insert(RequestQueues requestQueues);

    /**
     * @title: update
     * @description: update
     * @date: 2021/1/20 15:50
     * @author: ming.cao
     * @param requestQueues
     * @return void
     * @throws
     */
    void update(RequestQueues requestQueues);

    /**
     * @title: delete
     * @description: delete
     * @date: 2021/1/20 15:55
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: countRequestQueuesByCode
     * @description: countRequestQueuesByCode
     * @date: 2021/1/20 16:51
     * @author: ming.cao
     * @param
     * @return java.lang.Integer
     * @throws
     */
    Integer countRequestQueuesByCode(String code);
}