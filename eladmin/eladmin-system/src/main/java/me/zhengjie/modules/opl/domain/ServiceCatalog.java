package me.zhengjie.modules.opl.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "服务分类条目主表")
public class ServiceCatalog {

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

  @ApiModelProperty("修改日期")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp modifyDateTime;

  @ApiModelProperty(value = "修改人工号")
  private String modifyUserId;

}
