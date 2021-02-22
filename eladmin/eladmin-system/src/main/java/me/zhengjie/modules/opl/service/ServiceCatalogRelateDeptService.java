package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.ServiceCatalogRelateDept;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: 服务分类条目关联服务部门业务逻辑
 * @author: ming.cao
 * @create: 2021-02-08 16:01
 **/
public interface ServiceCatalogRelateDeptService {
    /**
     * @title: batchInsert
     * @description: 批量新增
     * @date: 2021/2/8 16:04
     * @author: ming.cao
     * @param list
     * @return void
     * @throws
     */
    void batchInsert(List<ServiceCatalogRelateDept> list) ;

    /**
     * @title: batchUpdate
     * @description: 批量修改
     * @date: 2021/2/22 11:51
     * @author: ming.cao
     * @param criteria
     * @return void
     * @throws
     */
    void batchUpdate(ServiceCatalogRelateDeptCriteria criteria) ;

    /**
     * @title: deleteByCatalogId
     * @description: 根据服务分类条目id删除
     * @date: 2021/2/8 16:05
     * @author: ming.cao
     * @param catalogId
     * @return void
     * @throws
     */
    void deleteByCatalogId(Integer catalogId);

    /**
     * @title: findByCatalogId
     * @description: 根据服务分类条目id获取服务部门数据
     * @date: 2021/2/8 16:06
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto>
     * @throws
     */
    Map<String,Object> findByCatalogId(Integer catalogId);

}
