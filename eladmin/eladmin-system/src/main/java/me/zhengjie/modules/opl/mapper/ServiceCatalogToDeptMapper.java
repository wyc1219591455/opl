package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.OrderSessionDetail;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;
import me.zhengjie.modules.opl.service.dto.*;
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

    /**
     * @title: findAllUserInUse
     * @description: 如果是最上层公司，那么就展示已勾选的
     * @date: 2021/2/24 20:31
     * @author: ming.cao
     * @param queuesId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.UserForShow>
     * @throws
     */
    List<UserForShow> findAllUserInUse(Integer queuesId);

    /**
     * @title: getCountByOrgId
     * @description: 获取公司下总数
     * @date: 2021/2/24 20:42
     * @author: ming.cao
     * @param
     * @return java.lang.Integer
     * @throws
     */
    Integer getCountByOrgId(Integer orgId);


    /**
     * @title: getListByOrgId
     * @description: 根据父类id获取list（查出部门下面的部门id）
     * @date: 2021/2/23 15:43
     * @author: ming.cao
     * @param orgId
     * @return java.util.List<java.lang.Integer>
     * @throws
     */
    List<Integer> getListByOrgId(Integer orgId);

    /**
     * @title: getCountByParentId
     * @description: 根据父类id获取总数（判断此部门是不是最底层部门）
     * @date: 2021/2/24 20:49
     * @author: ming.cao
     * @param deptId
     * @return java.lang.Integer
     * @throws
     */
    Integer getCountByParentId(Integer deptId);

    /**
     * @title: getListByParentId
     * @description: 根据父类id获取list（查出部门下面的部门id）
     * @date: 2021/2/23 13:44
     * @author: ming.cao
     * @param deptId
     * @return java.util.List<java.lang.Integer>
     * @throws
     */
    List<Integer> getListByParentId(Integer deptId);

    /**
     * 今天就写到这
     */
    List<UserForShow> findAllUserByDeptIdListAndCatalogId(Integer queuesId,List<Integer> deptList);

    /**
     * @title: findAllUserByDeptId
     * @description: 根据部门id获取所有的人员
     * @date: 2021/1/22 16:06
     * @author: ming.cao
     * @param deptId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.UserForShow>
     * @throws
     */
    List<UserForShow> findAllUserByDeptIdAndCatalogId(Integer queuesId,Integer deptId);

    /**
     * @title: delete
     * @description: 删除
     * @date: 2021/2/25 13:21
     * @author: ming.cao
     * @param id
     * @return void
     * @throws
     */
    void delete(Integer id);

    /**
     * @title: deleteByCatalogIdAndSourceId
     * @description: 根据条目id、人员id删除
     * @date: 2021/2/25 13:23
     * @author: ming.cao
     * @param catalogId
     * @param sourceId
     * @return void
     * @throws
     */
    void deleteByCatalogIdAndSourceId(Integer catalogId,Integer sourceId);

    /**
     * @title: findServiceCatalogToDeptByCatalogId
     * @description: 获取明细下关联数据
     * @date: 2021/2/25 13:37
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.domain.ServiceCatalogToDept>
     * @throws
     */
    List<ServiceCatalogToDept> findServiceCatalogToDeptByCatalogId(Integer catalogId);

    /**
     * @title: batchInsert
     * @description: 批量插入
     * @date: 2021/2/25 13:59
     * @author: ming.cao
     * @param list
     * @return void
     * @throws
     */
    void batchInsert(List<ServiceCatalogToDept> list);

    /**
     * @title: findByCatalogIdAndSourceId
     * @description: 查是否存在此组id和人id的数据
     * @date: 2021/2/24 19:03
     * @author: ming.cao
     * @param queuesId
     * @param sourceId
     * @return java.lang.Integer
     * @throws
     */
    Integer findByCatalogIdAndSourceId(Integer queuesId,Integer sourceId);
}
