package me.zhengjie.modules.opl.service.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrequestCategoryDto {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "父ID")
    private Integer parentId;

    @ApiModelProperty(value = "父节点名称")
    private String parentName;

    @ApiModelProperty(value = "根ID")
    private Integer rootId;

    @ApiModelProperty(value = "根节点名称")
    private String rootName;


    @ApiModelProperty(value = "子节点")
    private List<TrequestCategoryDto> trequestCategoryDtos;

}
