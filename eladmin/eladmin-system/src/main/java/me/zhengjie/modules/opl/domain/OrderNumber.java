package me.zhengjie.modules.opl.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-07 13:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderNumber {

    @ApiModelProperty("预期工单数量")
    private Integer limitNum;

    @ApiModelProperty("今日到期工单数量")
    private Integer dueNum;

    @ApiModelProperty("待我处理的工单")
    private Integer treatNum;

    @ApiModelProperty("今日新增工单")
    private Integer newNum;
}
