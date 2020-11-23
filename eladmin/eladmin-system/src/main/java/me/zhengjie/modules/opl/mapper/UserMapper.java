package me.zhengjie.modules.opl.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @website https://docs.auauz.net
 * @Description:
 * @Auther: chao.ji
 * @Date: 2020/10/28
 */
@Mapper
public interface UserMapper {
    /**
     * 根据工号查询用户的id
     * @return
     */
    Long findIdByUsername(String username);
}
