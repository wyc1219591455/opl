package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: eladmin
 * @description: 服务分类条目到服务部门关联表
 * @author: ming.cao
 * @create: 2021-02-08 15:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogRelateDept implements Serializable {
    @ApiModelProperty(value = "主键Id")
    private Integer id;

    @ApiModelProperty(value = "服务分类条目Id")
    private Integer catalogId;

    @ApiModelProperty(value = "部门Id")
    private Integer deptId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createUserId;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyUserId;

}
