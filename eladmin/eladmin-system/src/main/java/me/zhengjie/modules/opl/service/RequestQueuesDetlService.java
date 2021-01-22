package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.RequestQueuesDetl;
import me.zhengjie.modules.opl.service.dto.OrgTreeDto;
import me.zhengjie.modules.opl.service.dto.RequestQueuesDetlCriteria;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 组明细表业务逻辑层
 * @author: ming.cao
 * @create: 2021-01-20 18:34
 **/
public interface RequestQueuesDetlService {
    /**
    * @title: findByQueuesId
    * @description: 获取对应支持组下的明细数据
    * @date: 2021/1/20 18:35
    * @author: ming.cao
    * @param
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @throws
    */
    Map<String,Object> findByQueuesId(Pageable pageable,Integer id);

    /**
     * @title: addRequestQueuesDetl
     * @description: 新增支持组明细数据
     * @date: 2021/1/20 18:55
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void addRequestQueuesDetl(RequestQueuesDetlCriteria criteria);

    /**
     * @title: updateRequestQueuesDetl
     * @description: 更新支持组明细数据
     * @date: 2021/1/20 18:56
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void updateRequestQueuesDetl(RequestQueuesDetlCriteria criteria);

    /**
     * @title: deleteRequestQueuesDetl
     * @description: 删除支持组明细数据
     * @date: 2021/1/20 18:57
     * @author: ming.cao
     * @param ids
     * @return void
     * @throws
     */
    void deleteRequestQueuesDetl(List<Integer> ids);

    /**
     * @title: getOrgData
     * @description: 获取公司部门数据
     * @date: 2021/1/21 10:35
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.OrgTreeDto>
     * @throws
     */
    List<OrgTreeDto> getOrgData();
}
