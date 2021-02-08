package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 服务条目id与部门参数
 * @author: ming.cao
 * @create: 2021-02-08 16:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogRelateDeptCriteria implements Serializable {

    @ApiModelProperty(value = "服务分类id")
    private Integer catalogId;

    @ApiModelProperty(value = "部门ids")
    private List<Integer> deptIds;

}
