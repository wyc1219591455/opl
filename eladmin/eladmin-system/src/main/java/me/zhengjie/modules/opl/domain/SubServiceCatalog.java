package me.zhengjie.modules.opl.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "服务分类条目子表")
public class SubServiceCatalog {



  @ApiModelProperty(value = "服务分类条目ID")
  private long catalogId;

  @ApiModelProperty(value = "服务分类名称")
  private String catalogName;

  @ApiModelProperty(value = "描述")
  private String description;

  @ApiModelProperty(value = "父节点ID")
  private long parentId;

  @ApiModelProperty(value = "状态")
  private long status;

  @ApiModelProperty(value = "默认服务组")
  private long defaultQueueId;

  @ApiModelProperty(value = "创建日期")
  private java.sql.Timestamp createDateTime;

  @ApiModelProperty(value = "创建人")
  private long createUserId;

  @ApiModelProperty(value = "修改日期")
  private java.sql.Timestamp modifyDateTime;

  @ApiModelProperty(value = "修改人")
  private long modifyUserId;

}
