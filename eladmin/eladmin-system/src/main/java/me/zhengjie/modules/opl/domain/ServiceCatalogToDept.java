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
@ApiModel(value = "����/��Ա Ȩ���������������")
public class ServiceCatalogToDept {



  @ApiModelProperty(value = "ID")
  private Integer id;

  @ApiModelProperty(value = "��������Ա����Id")
  private Integer sourceId;

  @ApiModelProperty(value = "�������Id")
  private Integer catalogId;

  @ApiModelProperty(value = "���� 0����������� 1���������Ա")
  private Integer type;

  @ApiModelProperty(value = "״̬ 1������ʹ�� 0������ʹ��")
  private Integer status;

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
