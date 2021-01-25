package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.ServiceCatalogToQueues;
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
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.domain.ServiceCatalogToQueues>
     * @throws
     */
    List<ServiceCatalogToQueues> findByQueuesId(Integer id);

}
