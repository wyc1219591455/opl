package me.zhengjie.modules.opl.service;


import me.zhengjie.modules.opl.domain.User;
import me.zhengjie.modules.opl.service.dto.TrequestCategoryDto;

import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
public interface OrderUserService {

    /**
     * @title: findUser
     * @description: 获取人员信息
     * @date: 2021/1/20 18:35
     * @author: yuchao.wang
     * @param
     * @return ServiceCatalogDto
     * @throws
     */
    List<User> findUser(User user);
}
