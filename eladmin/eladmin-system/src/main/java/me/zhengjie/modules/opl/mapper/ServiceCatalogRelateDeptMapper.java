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

    /**
     * @title: findDeptDtoByDeptNeedList
     * @description: 根据ids获取部门
     * @date: 2021/3/1 16:38
     * @author: ming.cao
     * @param list
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto>
     * @throws
     */
    List<ServiceCatalogRelateDeptDto> findDeptDtoByDeptNeedList(List<Integer> list);

    /**
     * @title: getCountByParentId
     * @description: 根据父类id获取总数（做判断条件）
     * @date: 2021/3/1 15:59
     * @author: ming.cao
     * @param parentId
     * @return java.lang.Integer
     * @throws
     */
    Integer getCountByParentId(Integer parentId);

    /**
     * @title: getDeptIdByParentId
     * @description: getDeptIdByParentId
     * @date: 2021/3/1 16:07
     * @author: ming.cao
     * @param parentId
     * @return java.util.List<java.lang.Integer>
     * @throws
     */
    List<Integer> getDeptIdByParentId(Integer parentId);

}
