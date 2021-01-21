package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 支持组明细参数
 * @author: ming.cao
 * @create: 2021-01-20 18:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueuesDetlCriteria implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 0)
    private Integer id;
    /**
     * 主档键值(支持组主键)
     */
    @ApiModelProperty(value = "主档键值(支持组主键)", position = 1)
    private Integer queuesId;

    /**
     * 成员（成员主键）
     */
    @ApiModelProperty(value = "成员（成员主键）", position = 2)
    private String memberId;

    /**
     * 有效状态
     */
    @ApiModelProperty(value = "有效状态", position = 3)
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", position = 4)
    private String memo;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", position = 5)
    private Integer deptId;


}
