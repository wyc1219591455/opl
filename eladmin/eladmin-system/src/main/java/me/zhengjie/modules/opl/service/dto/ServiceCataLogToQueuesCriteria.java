package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 服务关联支持组参数
 * @author: ming.cao
 * @create: 2021-01-25 11:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCataLogToQueuesCriteria implements Serializable {

    @ApiModelProperty(value = "服务分类Id")
    private Integer cataLogId;

    @ApiModelProperty(value = "支持组Ids")
    private List<Integer> queuesIds;
}
