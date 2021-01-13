package me.zhengjie.modules.opl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @website https://docs.auauz.net
 * @description /用于配合Mybatis接收参数
 * 以前的JPA使用的分页从0开始是第一页。
 * 注意mybatis分页0和1都是代表第一页，所以需要从1开始第一页。此处应与前端沟通，让前端页码从1开始写起
 * @author xin.peng
 * @date 2020/10/10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Pageable", description="分页模型")
public class Pageable {

    @ApiModelProperty(value = "页码 默认1 第一页")
    private Integer page = 1;

    @ApiModelProperty(value = "每页数量 默认10")
    private Integer size = 10;

    @ApiModelProperty(value = "总条数" , hidden = true)
    private Long total = 0L;

}