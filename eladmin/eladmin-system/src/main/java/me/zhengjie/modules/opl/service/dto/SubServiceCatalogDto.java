package me.zhengjie.modules.opl.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "服务分类条目子表")
public class SubServiceCatalogDto {

    @ApiModelProperty(value = "服务分类条目ID")
    private Integer catalogId;

    @ApiModelProperty(value = "服务分类名称")
    private String catalogName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "父节点ID")
    private Integer parentId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "默认服务组")
    private Integer defaultQueueId;

    @ApiModelProperty(value = "默认服务组名称")
    private String defaultQueue;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createUserId;

    //创建人
    @ApiModelProperty("创建人名称")
    private String createPerson;

    @ApiModelProperty("修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyUserId;

    @ApiModelProperty(value = "图标地址")
    private String icon;

    //创建人
    @ApiModelProperty("修改人名称")
    private String modifyPerson;

}
