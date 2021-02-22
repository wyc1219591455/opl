package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 人员展示
 * @author: ming.cao
 * @create: 2021-01-22 10:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForShow implements Serializable {

    @ApiModelProperty(value = "人员主键")
    private Integer userId;

    @ApiModelProperty(value = "人员工号")
    private String userName;

    @ApiModelProperty(value = "人员姓名")
    private String humanName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编号")
    private Integer deptId;

    @ApiModelProperty(value = "标记 1为已选择，0为未选择")
    private Integer flag;

}
