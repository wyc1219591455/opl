package me.zhengjie.modules.opl.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: eladmin
 * @description: 服务分类关联服务组显示
 * @author: ming.cao
 * @create: 2021-01-25 11:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCataLogToQueuesDto implements Serializable {
    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 服务分类ID
     */
    @ApiModelProperty(value = "服务分类ID")
    private Integer catalogId;

    /**
     * 支持组ID
     */
    @ApiModelProperty(value = "支持组ID")
    private Integer queuesId;

    /**
     * 支持组ID
     */
    @ApiModelProperty(value = "支持组Name")
    private String queuesName;

    /**
     * 状态  1代表在使用 0代表不再使用
     */
    @ApiModelProperty(value = "状态  1代表在使用 0代表不再使用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
    private Timestamp createDateTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Integer createUserId;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 出参格式化
    private Timestamp modifyDateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Integer modifyUserId;

}
