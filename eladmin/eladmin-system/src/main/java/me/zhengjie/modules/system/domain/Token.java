package me.zhengjie.modules.management.domain;

import lombok.Data;

/**
 * @website https://docs.auauz.net
 * @Description:单点登录接受前端传来的token实体类
 * @Auther: chao.ji
 * @Date: 2021/01/11
 */
@Data
public class Token {
    String tokenString;
}
