package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @website https://docs.auauz.net
 * @Description:
 * @Auther: yuchao.wang
 * @Date: 2020/10/28
 */
@Mapper
public interface UserMapper {
    /**
     * 根据工号查询用户的id
     * @return
     */
    Long findIdByUsername(String username);


    /**
     * 查询
     * @return
     */
    List<User> findUser(User user);

}
