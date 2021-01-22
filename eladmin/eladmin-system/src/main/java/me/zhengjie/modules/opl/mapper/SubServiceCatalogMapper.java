package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.ServiceCatalog;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-22 11:28
 **/
@Mapper
public interface SubServiceCatalogMapper {

    /**
     * @title: insert
     * @description: 服务分类条目子表新建
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param subServiceCatalog
     * @return void
     * @throws
     */
    void insertSubCatalog(SubServiceCatalog subServiceCatalog);

    /**
     * @title: update
     * @description: update
     * @date: 2021/1/19 19:32
     * @author: yuchao.wang
     * @param subServiceCatalog
     * @return void
     * @throws
     */
    void updateSubCatalog(SubServiceCatalog subServiceCatalog);

    /**
     * @title: findAll
     * @description: findAll
     * @date: 2021/1/19 19:46
     * @author: yuchao.wang
     * @param
     * @return SubServiceCatalog
     * @throws
     */
    List<SubServiceCatalogDto> findSubCatalogByParentId(Integer ParentId);
}
