package me.zhengjie.modules.opl.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "���������Ŀ�ӱ�")
public class SubServiceCatalog {



  @ApiModelProperty(value = "���������ĿID")
  private long catalogId;

  @ApiModelProperty(value = "�����������")
  private String catalogName;

  @ApiModelProperty(value = "����")
  private String description;

  @ApiModelProperty(value = "���ڵ�ID")
  private long parentId;

  @ApiModelProperty(value = "״̬")
  private long status;

  @ApiModelProperty(value = "Ĭ�Ϸ�����")
  private long defaultQueueId;

  @ApiModelProperty(value = "��������")
  private java.sql.Timestamp createDateTime;

  @ApiModelProperty(value = "������")
  private long createUserId;

  @ApiModelProperty(value = "�޸�����")
  private java.sql.Timestamp modifyDateTime;

  @ApiModelProperty(value = "�޸���")
  private long modifyUserId;

}
