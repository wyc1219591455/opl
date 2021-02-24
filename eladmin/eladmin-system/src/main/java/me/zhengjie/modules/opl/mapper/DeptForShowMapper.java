package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.DeptForShow;
import me.zhengjie.modules.opl.service.dto.DeptVo;
import me.zhengjie.modules.opl.service.dto.UserForShow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: eladmin
 * @description: 部门数据访问层
 * @author: ming.cao
 * @create: 2021-01-21 11:18
 **/
@Mapper
public interface DeptForShowMapper {

    /**
     * @title: findDeptByOrgId
     * @description: 根据公司id查找部门
     * @date: 2021/2/23 10:47
     * @author: ming.cao
     * @param orgId
     * @return java.util.List<me.zhengjie.modules.opl.domain.DeptForShow>
     * @throws
     */
    List<DeptForShow> findDeptByOrgId(String orgId) ;

    /**
     * @title: findDeptByParentId
     * @description: 根据父类查找部门
     * @date: 2021/2/23 10:46
     * @author: ming.cao
     * @param deptId
     * @return java.util.List<me.zhengjie.modules.opl.domain.DeptForShow>
     * @throws
     */
    List<DeptForShow> findDeptByParentId(String deptId);

    /**
     * @title: findDeptByGradeOrg
     * @description: 获取最大上级公司下面的部门
     * @date: 2021/2/23 10:46
     * @author: ming.cao
     * @param
     * @return java.util.List<me.zhengjie.modules.opl.domain.DeptForShow>
     * @throws
     */
    List<DeptForShow> findDeptByGradeOrg();

    /**
     * @title: findDeptVoNotInCatalogId
     * @description: 获取服务id下不在此服务组的部门信息
     * @date: 2021/2/23 10:46
     * @author: ming.cao
     * @param catalogId
     * @param deptVo
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.DeptVo>
     * @throws
     */
    List<DeptVo> findDeptVoNotInCatalogId(Integer catalogId,DeptVo deptVo);

    /**
     * @title: findDeptVoInCatalogId
     * @description: 获取服务id下在此服务组的部门信息
     * @date: 2021/2/24 13:52
     * @author: ming.cao
     * @param catalogId
     * @return java.util.List<me.zhengjie.modules.opl.service.dto.DeptVo>
     * @throws
     */
    List<DeptVo> findDeptVoInCatalogId(Integer catalogId);


}
