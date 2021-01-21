package me.zhengjie.modules.opl.mapper;


import com.sun.org.apache.xpath.internal.operations.String;
import me.zhengjie.modules.opl.domain.RequestQueues;
import me.zhengjie.modules.opl.domain.RequestQueuesDetl;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 支持组下明细数据访问层
 * @author: ming.cao
 * @create: 2021-01-20 17:14
 **/
@Mapper
public interface RequestQueuesDetlMapper {
    /**
     * @title: findByQueuesId
     * @description: 获取明细下人员数据
     * @date: 2021/1/20 18:06
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.RequestQueuesDetlDto>
     * @throws
     */
    List<RequestQueuesDetlDto> findByQueuesId(Integer id);

    /**
     * @title: insertRequestQueues
     * @description: 插入明细下人员数据
     * @date: 2021/1/20 18:06
     * @author: ming.cao
     * @param requestQueuesDetl
     * @return void
     * @throws
     */
    void insert(RequestQueuesDetl requestQueuesDetl);

    /**
     * @title: updateRequestQueues
     * @description: 更新明细下人员数据
     * @date: 2021/1/20 18:20
     * @author: ming.cao
     * @param requestQueuesDetl
     * @return void
     * @throws
     */
    void update(RequestQueuesDetl requestQueuesDetl);
    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/1/20 18:30
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: countMemberInQueue
     * @description: countMemberInQueue
     * @date: 2021/1/20 19:18
     * @author: ming.cao
     * @param queuesId
     * @param memberId
     * @return java.lang.Integer
     * @throws
     */
    Integer countMemberInQueue(Integer queuesId,Integer memberId);
}
