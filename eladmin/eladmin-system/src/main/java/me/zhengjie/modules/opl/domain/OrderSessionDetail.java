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



  @ApiModelProperty(value = "����")
  private Integer detailId;

  @ApiModelProperty(value = "orderSessionId")
  private Integer sessionId;

  @ApiModelProperty(value = "����Id")
  private Integer transId;

  @ApiModelProperty(value = "��������")
  private String propName;

  @ApiModelProperty(value = "������")
  private String oldValue;

  @ApiModelProperty(value = "������")
  private String newValue;

  @ApiModelProperty(value = "�����˹���")
  private String createUserId;

  //��������
  @ApiModelProperty("��������")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime;

}
