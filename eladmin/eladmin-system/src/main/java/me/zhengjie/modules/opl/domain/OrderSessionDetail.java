package me.zhengjie.modules.opl.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OrderSessionDetail")
public class OrderSessionDetail {



  @ApiModelProperty(value = "主键")
  private Integer detailId;

  @ApiModelProperty(value = "orderSessionId")
  private Integer sessionId;

  @ApiModelProperty(value = "工单Id")
  private Integer transId;

  @ApiModelProperty(value = "操作详情")
  private String propName;

  @ApiModelProperty(value = "旧数据")
  private String oldValue;

  @ApiModelProperty(value = "新数据")
  private String newValue;

  @ApiModelProperty(value = "创建人工号")
  private String createUserId;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime;

}
