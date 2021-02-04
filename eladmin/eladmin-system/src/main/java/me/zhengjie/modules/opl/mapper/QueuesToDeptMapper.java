package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.DeptForShow;
import me.zhengjie.modules.opl.domain.QueuesToDept;
import me.zhengjie.modules.opl.service.dto.SysDeptDto;
import me.zhengjie.modules.opl.service.dto.UserForShow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 部门到支持组数据访问层
 * @author: ming.cao
 * @create: 2021-01-22 14:00
 **/
@Mapper
public interface QueuesToDeptMapper {
    /**
     * @title: findByQueueId
     * @description: 获取明细下人员数据
     * @date: 2021/1/22 14:02
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.QueuesToDept>
     * @throws
     */
    List<UserForShow> findByQueueId(Integer id) ;

    /**
     * @title: insert
     * @description: insert
     * @date: 2021/1/22 14:05
     * @author: ming.cao
     * @param queuesToDept
     * @return void
     * @throws
     */
    void insert(QueuesToDept queuesToDept);

    /**
     * @title: batchInsert
     * @description: 批量新增
     * @date: 2021/1/22 14:28
     * @author: ming.cao
     * @param queuesList
     * @return void
     * @throws
     */
    void batchInsert(List<QueuesToDept> queuesList);

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/1/22 14:06
     * @author: ming.cao
     * @param queuesToDept
     * @return void
     * @throws
     */
    void update(QueuesToDept queuesToDept);

    /**
     * @title: batchUpdate
     * @description: 批量修改
     * @date: 2021/1/22 14:29
     * @author: ming.cao
     * @param queuesList
     * @return void
     * @throws
     */
    void batchUpdate(List<QueuesToDept> queuesList);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/1/22 14:06
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: deleteByQueue
     * @description: 根据支持组删
     * @date: 2021/1/22 15:20
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void deleteByQueue(Integer queuesId);

    /**
     * @title: findAllUserByDeptId
     * @description: 根据部门id获取所有的人员
     * @date: 2021/1/22 16:06
     * @author: ming.cao
     * @param deptId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.UserForShow>
     * @throws
     */
    List<UserForShow> findAllUserByDeptId(Integer deptId);

    /**
     * @title: findDeptByQueueId
     * @description: 根据服务分类条目id获取部门信息
     * @date: 2021/2/2 15:15
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.SysDeptDto>
     * @throws
     */
    List<SysDeptDto> findDeptByCatalogId(Integer catalogId);

    /**
     * @title: findAllUserByDeptId2
     * @description: 根据部门id获取所有的人员
     * @date: 2021/2/2 16:01
     * @author: ming.cao
     * @param deptId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.UserForShow>
     * @throws
     */
    List<UserForShow> findAllUserByDeptId2(Integer deptId);

    /**
     * @title: findServiceCatalogByUserId
     * @description: 根据用户id获取所有服务分类条目
     * @date: 2021/2/4 13:15
     * @author: ming.cao
     * @param userId
     * @return java.util.List<java.lang.Integer>
     * @throws
     */
    List<Integer> findServiceCatalogByUserId(Integer userId);
}
