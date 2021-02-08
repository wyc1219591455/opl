package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 工单状态传参
 * @author: ming.cao
 * @create: 2021-02-08 10:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusCriteria implements Serializable {
    @Id
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", position = 0)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 1)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", position = 2)
    private String desc;

    /**
     * 关闭计时器
     */
    @ApiModelProperty(value = "关闭计时器", position = 3)
    private Integer isCloseTimer;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色", position = 4)
    private String color;

}
