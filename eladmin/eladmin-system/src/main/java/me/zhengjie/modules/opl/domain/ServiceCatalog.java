package me.zhengjie.modules.opl.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "服务分类条目主表")
public class ServiceCatalog {



  @ApiModelProperty(value = "服务分类条目ID")
  private long catalogId;

  @ApiModelProperty(value = "服务分类名称")
  private String catalogName;

  @ApiModelProperty(value = "简称")
  private String shortName;

  @ApiModelProperty(value = "图标地址")
  private String icon;

  @ApiModelProperty(value = "状态")
  private String status;

  @ApiModelProperty(value = "创建日期")
  private java.sql.Timestamp createDateTime;

  @ApiModelProperty(value = "创建人工号")
  private String createUserId;

  @ApiModelProperty(value = "修改日期")
  private java.sql.Timestamp modifyDateTime;

  @ApiModelProperty(value = "修改人工号")
  private String modifyUserId;

}
