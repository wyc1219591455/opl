package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.OrderSessionDetail;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;
import me.zhengjie.modules.opl.service.dto.OrderSessionDetailDto;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SessionDetailCriteria;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-21 11:28
 **/
@Mapper
public interface ServiceCatalogToDeptMapper {

    /**
     * @title: insert
     * @description: 新增关联关系
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param serviceCatalogToDept
     * @return void
     * @throws
     */
    void insertCatalogToDeptAssociation(ServiceCatalogToDept serviceCatalogToDept);


    /**
     * @title: 根据人员ID查找服务分类子表
     * @description: 根据人员ID查找服务分类子表
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param userId
     * @return SubServiceCatalogDto
     * @throws
     */
    List<SubServiceCatalogDto> findAssociationById(String userId);


    /**
     * @title: 根据人员ID查找服务分类父表
     * @description: 根据人员ID查找服务分类父表
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param userId
     * @return ServiceCatalogDto
     * @throws
     */
    List<ServiceCatalogDto> findParentAssociationById(String userId);
}
