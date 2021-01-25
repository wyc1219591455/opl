package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.OrgAs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @program: eladmin
 * @description: 公司接口数据
 * @author: ming.cao
 * @create: 2021-01-21 10:47
 **/
@Mapper
public interface OrgAsMapper {

    List<OrgAs> findGradeOrg();

    List<OrgAs> findAllChildrenOrg();

}
