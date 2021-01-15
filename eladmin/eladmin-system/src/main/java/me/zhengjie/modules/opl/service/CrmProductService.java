package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.Pageable;

import java.util.Map;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-15 16:11
 **/

public interface CrmProductService {
    Map<String,Object> findAll(Pageable pageable) ;

}
