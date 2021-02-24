package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.ServiceCatalogToQueues;
import me.zhengjie.modules.opl.service.dto.CatalogCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogToQueuesDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 服务支持组明细数据访问
 * @author: ming.cao
 * @create: 2021-01-22 13:13
 **/
@Mapper
public interface ServiceCatalogToQueuesMapper {
    /**
     * @title: findByQueuesId
     * @description: findByQueuesId
     * @date: 2021/1/22 13:17
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.domain.ServiceCatalogToQueues>
     * @throws
     */
    List<ServiceCatalogToQueuesDto> findByQueuesId(Integer catalogId);


    List<ServiceCatalogToQueuesDto> findQueuesByUserId(Integer userId);

    /**
     * @title: batchInsert
     * @description: 批量新增
     * @date: 2021/1/25 10:34
     * @author: ming.cao
     * @param queues
     * @return void
     * @throws
     */
    void batchInsert(List<ServiceCatalogToQueues> queues);

    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/8 14:49
     * @author: ming.cao
     * @param queues
     * @return void
     * @throws
     */
    void insert(ServiceCatalogToQueues queues);

    /**
     * @title: batchUpdate
     * @description: 批量修改
     * @date: 2021/1/25 10:42
     * @author: ming.cao
     * @param queues
     * @return void
     * @throws
     */
    void batchUpdate(List<ServiceCatalogToQueues> queues);

    /**
     * @title: delByCatalogId
     * @description: delByCatalogId
     * @date: 2021/1/25 10:44
     * @author: ming.cao
     * @param catalogId
     * @return void
     * @throws
     */
    void delByCatalogId(Integer catalogId);

    /**
     * @title: getQueuesByUserId
     * @description:
     * @date: 2021/2/23 19:50
     * @author: ming.cao
     * @param catalogCriteria
     * @return java.lang.Integer
     * @throws
     */
    Integer getCountByCatalogName( CatalogCriteria catalogCriteria );
}
