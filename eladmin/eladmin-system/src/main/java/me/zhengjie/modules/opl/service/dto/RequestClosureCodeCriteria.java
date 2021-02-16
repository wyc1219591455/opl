package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: eladmin
 * @description: 关闭类型参数
 * @author: ming.cao
 * @create: 2021-02-09 09:33
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestClosureCodeCriteria implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 0)
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
}
