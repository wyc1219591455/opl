package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.service.dto.ServiceCataLogToQueuesCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCataLogToQueuesDto;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 服务关联支持组业务逻辑层
 * @author: ming.cao
 * @create: 2021-01-25 11:23
 **/
public interface ServiceCatalogToQueuesService {
    /**
     * @title: getQueueByCatalogId
     * @description: 获取服务组id
     * @date: 2021/1/25 11:26
     * @author: ming.cao
     * @param id
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.ServiceCataLogToQueuesDto>
     * @throws
     */
    Map<String,Object> getQueueByCatalogId(Integer id );

    /**
     * @title: batchInsert
     * @description: 批量新增
     * @date: 2021/1/25 11:53
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void batchInsert(ServiceCataLogToQueuesCriteria criteria);

    /**
     * @title: batchUpdate
     * @description: 批量修改
     * @date: 2021/1/26 9:58
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void batchUpdate(ServiceCataLogToQueuesCriteria criteria);

}
