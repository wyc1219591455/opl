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
@ApiModel(value = "��������ͷ�����������")
public class ServiceCatalogToCategory {



  @ApiModelProperty(value = "����")
  private Integer id;

  @ApiModelProperty(value = "�������ID")
  private Integer catalogId;

  @ApiModelProperty(value = "��������Id")
  private Integer categoryId;

  @ApiModelProperty("����ʱ��")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime ;

  @ApiModelProperty(value = "�����˹���")
  private String createUserId;

  @ApiModelProperty(value = "״̬")
  private Integer status;

}
