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
@ApiModel(value = "���������Ŀ����")
public class ServiceCatalog {

  @ApiModelProperty(value = "���������ĿID")
  private Integer catalogId;

  @ApiModelProperty(value = "�����������")
  private String catalogName;

  @ApiModelProperty(value = "���")
  private String shortName;

  @ApiModelProperty(value = "ͼ���ַ")
  private String icon;

  @ApiModelProperty(value = "״̬")
  private String status;

  //��������
  @ApiModelProperty("��������")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime;

  @ApiModelProperty(value = "�����˹���")
  private String createUserId;

  @ApiModelProperty("�޸�����")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp modifyDateTime;

  @ApiModelProperty(value = "�޸��˹���")
  private String modifyUserId;

}
