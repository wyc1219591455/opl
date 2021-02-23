package me.zhengjie.modules.opl.service.impl;


import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.User;
import me.zhengjie.modules.opl.mapper.UserMapper;
import me.zhengjie.modules.opl.service.OrderUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: eladmin
 * @description: 获取人员信息
 * @author: ming.cao
 * @create: 2021-01-13 19:25
 **/
@Service
@RequiredArgsConstructor
public class OrderUserServiceImpl implements OrderUserService {

    private UserMapper userMapper;

    @Override
    public  List<User> findUser(User user)
    {
         return userMapper.findUser(user);
    }
}
