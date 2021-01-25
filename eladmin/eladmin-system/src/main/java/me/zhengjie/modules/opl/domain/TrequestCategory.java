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
@ApiModel(value = "null")
public class TrequestCategory {



  @ApiModelProperty(value = "����")
  private Integer id;

  @ApiModelProperty(value = "����")
  private String name;

  @ApiModelProperty(value = "���ڵ�ID")
  private Integer parentId;

  @ApiModelProperty("����ʱ��")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime ;

  @ApiModelProperty(value = "������")
  private Integer createUserId;

  @ApiModelProperty(value = "״̬")
  private Integer status;

  @ApiModelProperty(value = "���ڵ�Id")
  private Integer rootId;

}
