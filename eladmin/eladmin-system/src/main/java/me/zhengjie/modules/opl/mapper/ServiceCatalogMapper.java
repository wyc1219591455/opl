package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.ServiceCatalog;
import me.zhengjie.modules.opl.domain.SubOrder;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-08 11:28
 **/
@Mapper
public interface ServiceCatalogMapper {


    /**
     * @title: insert
     * @description: 服务分类条目主表新建
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param serviceCatalog
     * @return void
     * @throws
     */
    void insertCatalog(ServiceCatalog serviceCatalog);

    /**
     * @title: update
     * @description: 服务分类条目主表更新
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param serviceCatalog
     * @return void
     * @throws
     */
    void updateCatalog(ServiceCatalog serviceCatalog);

    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalog
     * @throws
     */
    List<ServiceCatalogDto> findAllCatalog();

    /**
     * @title: findOnUsedSub
     * @description: 查找父分类下还在使用的子类数量
     * @date: 2021/1/20 18:55
     * @author: yuchao.wang
     * @param catalogId
     * @return Integer
     * @throws
     */
    Integer findOnUsedSub(Integer catalogId);
}
