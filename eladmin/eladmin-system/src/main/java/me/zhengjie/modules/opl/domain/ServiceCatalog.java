package me.zhengjie.modules.opl.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "���������Ŀ����")
public class ServiceCatalog {



  @ApiModelProperty(value = "���������ĿID")
  private long catalogId;

  @ApiModelProperty(value = "�����������")
  private String catalogName;

  @ApiModelProperty(value = "���")
  private String shortName;

  @ApiModelProperty(value = "ͼ���ַ")
  private String icon;

  @ApiModelProperty(value = "״̬")
  private String status;

  @ApiModelProperty(value = "��������")
  private java.sql.Timestamp createDateTime;

  @ApiModelProperty(value = "�����˹���")
  private String createUserId;

  @ApiModelProperty(value = "�޸�����")
  private java.sql.Timestamp modifyDateTime;

  @ApiModelProperty(value = "�޸��˹���")
  private String modifyUserId;

}
