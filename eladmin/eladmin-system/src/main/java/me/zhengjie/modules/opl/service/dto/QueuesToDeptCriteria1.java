package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 单个新增传入的支持组明细
 * @author: ming.cao
 * @create: 2021-02-24 17:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueuesToDeptCriteria1 implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 服务分类Id
     */
    @ApiModelProperty(value = "支持组Id")
    private Integer queuesId;

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
