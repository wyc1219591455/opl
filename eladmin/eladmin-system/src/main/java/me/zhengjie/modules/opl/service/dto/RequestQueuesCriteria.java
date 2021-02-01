package me.zhengjie.modules.opl.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: eladmin
 * @description: 支持组
 * @author: ming.cao
 * @create: 2021-01-20 16:13
 **/
@Data
public class RequestQueuesCriteria implements Serializable {
    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 0)
    private Integer id;
    /**
     * 组代码
     */
    @ApiModelProperty(value = "组代码", position = 1)
    private String code;

    /**
     * 组名称
     */
    @ApiModelProperty(value = "组名称", position = 2)
    private String name;

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
     * 是否默认组
     */
    @ApiModelProperty(value = "是否默认组", position = 5)
    private Integer isDefault;


}
