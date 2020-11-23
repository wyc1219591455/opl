package me.zhengjie.modules.opl.service.impl;

import me.zhengjie.modules.opl.mapper.UserMapper;
import me.zhengjie.modules.opl.service.WorkOrderService;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName WorkOrderServiceImpl.java
 * @Description TODO
 * @createTime 2020年10月28日 11:02:00
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserInfo() {
        User user = new User();
        user.setId(userMapper.findIdByUsername(SecurityUtils.getCurrentUser().getUsername()));
        user.setUsername(SecurityUtils.getCurrentUser().getUsername());
        return user;
    }
}
