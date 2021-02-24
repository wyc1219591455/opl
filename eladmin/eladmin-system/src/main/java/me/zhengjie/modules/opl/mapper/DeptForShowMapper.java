package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.DeptForShow;
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

    List<DeptForShow> findDeptByOrgId(String orgId);

    List<DeptForShow> findDeptByParentId(String deptId);

    /**
     * 获取最大上级公司下面的部门
     * @return
     */
    List<DeptForShow> findDeptByGradeOrg();


}
