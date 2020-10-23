package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.security.service.dto.AuthUserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @website https://docs.auauz.net
 * @Description:
 * @Auther: chao.ji
 * @Date: 2020/10/22
 */
@Mapper
public interface FindUserNameMapper {

    Integer findByUserName(String userName);

    String findPasswordByUsername(String userName);

    void updatePasswordByName(AuthUserDto authUserDto);

    void deleteByUserName(String userName);
}
