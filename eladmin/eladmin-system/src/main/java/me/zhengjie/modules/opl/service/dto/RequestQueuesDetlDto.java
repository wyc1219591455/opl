package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 明细展示
 * @author: ming.cao
 * @create: 2021-01-20 17:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueuesDetlDto implements Serializable {

    @ApiModelProperty(value = "人员主键")
    private Integer userId;

    @ApiModelProperty(value = "工号")
    private String userName;


    @ApiModelProperty(value = "人员姓名")
    private String humanName;


    @ApiModelProperty(value = "所属部门")
    private String deptName;


    @ApiModelProperty(value = "支持组编号")
    private String code;


    @ApiModelProperty(value = "支持组名称")
    private String name;

    @ApiModelProperty(value = "支持组id")
    private Integer id;
}
