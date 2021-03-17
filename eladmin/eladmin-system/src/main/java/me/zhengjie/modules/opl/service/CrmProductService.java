package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.service.dto.CrmProductCriteria;

import java.util.Map;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-15 16:11
 **/

public interface CrmProductService {
    /**
    * @title: findAll
    * @description: 获取所有
    * @date: 2021/1/26 10:02
    * @author: ming.cao
    * @param pageable
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @throws
    */
Map<String,Object> findAll(CrmProductCriteria criteria, Pageable pageable);

}
