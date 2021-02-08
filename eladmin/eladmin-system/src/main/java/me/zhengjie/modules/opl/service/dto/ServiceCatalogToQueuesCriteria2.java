package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 服务关联支持组参数一对一关系
 * @author: ming.cao
 * @create: 2021-02-08 14:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogToQueuesCriteria2 implements Serializable {
    @ApiModelProperty(value = "服务分类Id")
    private Integer catalogId;

    @ApiModelProperty(value = "支持组Id")
    private Integer queuesId;
}
