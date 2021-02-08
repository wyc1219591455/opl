package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 服务分类条目中的服务部门关联
 * @author: ming.cao
 * @create: 2021-02-08 15:42
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogRelateDeptDto implements Serializable {

    @ApiModelProperty("关联表id")
    private Integer id;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

}
