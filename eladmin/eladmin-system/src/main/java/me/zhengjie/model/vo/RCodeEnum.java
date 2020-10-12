package me.zhengjie.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *     请求数据 返回码
 *     200：请求成功，
 *     500：未知错误，
 *     10001：用户不存在 或 密码错误，
 *     40300：请求无权限，
 *     10004：用户已禁用，
 *     10005：用户未登录
 * </p>
 */

@Getter
@AllArgsConstructor
public enum RCodeEnum {
    OK(200, "请求成功"),
    UNKNOWN_ERROR(500, "未知错误"),
    USER_NOT_EXIST(10001, "用户不存在 或 密码错误"),
    USER_UNAUTHORIZED(40300, "请求无权限"),
    USER_FORBIDDEN(10004, "用户已禁用"),
    USER_NOT_LOGIN_IN(10005, "用户未登录"),
    ;

    private final Integer code;
    private final String message;

}
