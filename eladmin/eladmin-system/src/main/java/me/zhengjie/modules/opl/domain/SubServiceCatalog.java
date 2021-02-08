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
@ApiModel(value = "服务分类条目子表")
public class SubServiceCatalog {



  @ApiModelProperty(value = "服务分类条目ID")
  private Integer catalogId;

  @ApiModelProperty(value = "服务分类名称")
  private String catalogName;

  @ApiModelProperty(value = "描述")
  private String description;

  @ApiModelProperty(value = "父节点ID")
  private Integer parentId;

  @ApiModelProperty(value ="状态")
  private Integer status;

  @ApiModelProperty(value = "默认服务组")
  private Integer defaultQueueId;

  //��������
  @ApiModelProperty("创建日期")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime;

  @ApiModelProperty(value = "创建人")
  private String createUserId;

  @ApiModelProperty("修改日期")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp modifyDateTime;

  @ApiModelProperty(value = "修改人")
  private String modifyUserId;

  @ApiModelProperty(value = "图标")
  private String icon;

}
