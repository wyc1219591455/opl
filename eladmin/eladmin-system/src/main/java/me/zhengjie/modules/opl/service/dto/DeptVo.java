package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 关联信息时的数据展示用的部门信息
 * @author: ming.cao
 * @create: 2021-02-23 10:03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptVo implements Serializable {

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "部门全称")
    private String deptFullName;

    @ApiModelProperty(value = "部门简称")
    private String deptName;

}
