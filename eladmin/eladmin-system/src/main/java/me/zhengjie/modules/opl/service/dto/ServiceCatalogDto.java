package me.zhengjie.modules.opl.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-21 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogDto {

    @ApiModelProperty(value = "服务分类条目ID")
    private Integer catalogId;

    @ApiModelProperty(value = "服务分类名称")
    private String catalogName;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "图标地址")
    private String icon;

    @ApiModelProperty(value = "状态")
    private Integer status;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;

    @ApiModelProperty(value = "创建人工号")
    private String createUserId;

    //创建人
    @ApiModelProperty("创建人名称")
    private String createPerson;

    @ApiModelProperty("修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp modifyDateTime;

    @ApiModelProperty(value = "修改人工号")
    private String modifyUserId;

    //创建人
    @ApiModelProperty("修改人名称")
    private String modifyPerson;

    @ApiModelProperty("服务子分类列表")
    private List<SubServiceCatalogDto> subServiceCatalogDtoList;

}
