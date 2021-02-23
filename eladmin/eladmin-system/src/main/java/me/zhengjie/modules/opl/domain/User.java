package me.zhengjie.modules.opl.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "工号")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String name;
}
