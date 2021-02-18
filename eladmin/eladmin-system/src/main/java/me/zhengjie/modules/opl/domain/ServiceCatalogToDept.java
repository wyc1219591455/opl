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
@ApiModel(value = "部门/人员 权限与服务分类关联表")
public class ServiceCatalogToDept {



  @ApiModelProperty(value = "ID")
  private Integer id;

  @ApiModelProperty(value = "关联的人员或部门Id")
  private Integer sourceId;

  @ApiModelProperty(value = "服务分类Id")
  private Integer catalogId;

  @ApiModelProperty(value = "类型 0代表关联部门 1代表关联人员")
  private Integer type;

  @ApiModelProperty(value = "状态 1代表在使用 0代表不再使用")
  private Integer status;

  @ApiModelProperty("创建日期")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime;

  @ApiModelProperty(value = "创建人工号")
  private String createUserId;

  @ApiModelProperty("修改日期")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  private Timestamp modifyDateTime;

  @ApiModelProperty(value = "修改人工号")
  private String modifyUserId;

}
