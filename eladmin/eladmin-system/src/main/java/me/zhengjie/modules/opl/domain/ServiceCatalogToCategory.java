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
@ApiModel(value = "工单分类和服务分类关联表")
public class ServiceCatalogToCategory {



  @ApiModelProperty(value = "主键")
  private Integer id;

  @ApiModelProperty(value = "服务分类ID")
  private Integer catalogId;

  @ApiModelProperty(value = "工单分类Id")
  private Integer categoryId;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime ;

  @ApiModelProperty(value = "创建人工号")
  private String createUserId;

  @ApiModelProperty(value = "状态")
  private Integer status;

}
