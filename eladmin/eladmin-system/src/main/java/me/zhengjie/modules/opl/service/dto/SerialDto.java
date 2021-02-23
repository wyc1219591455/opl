package me.zhengjie.modules.opl.service.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerialDto {

    //主键id
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("判断主单还是子单")
    private Integer orderType;

    //主题
    @ApiModelProperty("工单号")
    private String serialNo;

}
