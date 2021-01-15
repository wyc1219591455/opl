package me.zhengjie.modules.opl.mapper;


import me.zhengjie.modules.opl.domain.CrmProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: meeting_structure
 * @description: crm产品数据访问层
 * @author: ming.cao
 * @create: 2021-01-14 15:10
 **/
@Mapper
public interface CrmProductMapper {

    List<CrmProduct> findAll();


    //List<CrmProduct> findCrmProductByMaxId();
}
