package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 服务分类部门人员可视化传入参数2
 * @author: ming.cao
 * @create: 2021-02-25 10:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogToDeptCriteria1 implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 服务分类Id
     */
    @ApiModelProperty(value = "服务分类Id")
    private Integer catalogId;

    /**
     * 关联的人员或部门Id
     */
    @ApiModelProperty(value = "关联的人员或部门Id")
    private Integer sourceId;

    /**
     * 类型 0代表关联部门 1代表关联人员
     */
    @ApiModelProperty(value = "类型 0代表关联部门 1代表关联人员")
    private Integer type;

    /**
     * 状态 1代表在使用 0代表不再使用
     */
    @ApiModelProperty(value = "状态 1代表在使用 0代表不再使用", position = 4)
    private String status;
}
