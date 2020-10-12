package me.zhengjie.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value="ResultVO", description="返回模型")
@Data
public class ResultVO<T> implements Serializable {

    @ApiModelProperty(value = "返回码 200：请求成功，500：未知错误，10001：账号不存在或密码错误，40300：请求无权限，10004：用户已禁用，10005：用户未登录")
    private Integer code;

    @ApiModelProperty(value = "响应文案 一般为提示信息")
    private String message;

    @ApiModelProperty(value = "请求是否成功")
    private Boolean isError = false;

    @ApiModelProperty(value = "具体返回内容")
    private T data;

    @ApiModelProperty(value = "后台验证错误信息")
    private T validationErrors;
}
