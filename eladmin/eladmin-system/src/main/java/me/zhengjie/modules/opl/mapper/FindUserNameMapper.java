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

    /**
     * 查询指定用户名信息的数量
     * @param userName
     * @return
     */
    Integer findByUserName(String userName);

    /**
     *通过用户名差密码
     * @param userName
     * @return
     */
    String findPasswordByUsername(String userName);

    /**
     *通过用户名更新密码
     * @param authUserDto
     */
    void updatePasswordByName(AuthUserDto authUserDto);

    /**
     *
     * @param userName
     * @return
     */
    Integer deleteByUserName(String userName);
}
