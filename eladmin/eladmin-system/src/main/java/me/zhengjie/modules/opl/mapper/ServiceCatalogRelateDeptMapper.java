package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.ServiceCatalogRelateDept;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 服务分类条目到服务部门关联表mapper
 * @author: ming.cao
 * @create: 2021-02-08 15:20
 **/
@Mapper
public interface ServiceCatalogRelateDeptMapper {
    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/8 15:21
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void batchInsert(List<ServiceCatalogRelateDept> list);

    /**
     * @title: update
     * @description: 修改
     * @date: 2021/2/8 15:22
     * @author: ming.cao
     * @param
     * @return void
     * @throws
     */
    void batchUpdate(List<ServiceCatalogRelateDept> list);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/2/8 15:22
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: deleteByCatalogId
     * @description: 通过服务分类条目id删除关联关系
     * @date: 2021/2/8 15:39
     * @author: ming.cao
     * @param catalogId
     * @return void
     * @throws
     */
    void deleteByCatalogId(Integer catalogId);

    /**
     * @title: findDeptDtoByCatalogId
     * @description: 根据服务分类条目id查找数据
     * @date: 2021/2/8 15:56
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto>
     * @throws
     */
    List<ServiceCatalogRelateDeptDto> findDeptDtoByCatalogId(Integer catalogId);


}
