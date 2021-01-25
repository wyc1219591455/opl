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



  @ApiModelProperty(value = "主键")
  private Integer id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "父节点ID")
  private Integer parentId;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
  private Timestamp createDateTime ;

  @ApiModelProperty(value = "创建人")
  private Integer createUserId;

  @ApiModelProperty(value = "状态")
  private Integer status;

  @ApiModelProperty(value = "根节点Id")
  private Integer rootId;

}
