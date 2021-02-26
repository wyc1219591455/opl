package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 抄送传参
 * @author: ming.cao
 * @create: 2021-02-22 20:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderApplyCcCriteria implements Serializable {

    @ApiModelProperty( value = "请求主表编号")
    private Integer transId;

    @ApiModelProperty( value = "人员编号")
    private List<String> empId;

    @ApiModelProperty( value = "表来源（0 主表crmWorkOrder ，1 附表 suborder）")
    private Integer originalType;

}
