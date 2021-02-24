package me.zhengjie.modules.opl.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSessionDto {

    //主键id
    @ApiModelProperty("主键id")
    private Long id;

    //功能完善表名称
    @ApiModelProperty("工单主表ID")
    private String transId;

    //业务类型
    @ApiModelProperty("工单操作 1新建 2确认受理 3转派 4回复工单 5取消工单 6重开工单 7关闭工单 8派发工单")
    private Integer orderType;

    //业务类型
    @ApiModelProperty("工单描述")
    private String orderTypeDesc;

    //所有人
    @ApiModelProperty("具体描述")
    private String description;

    //所有人
    @ApiModelProperty("附件")
    private String problemAttach;


    //创建人
    @ApiModelProperty("创建人")
    private String createUserId;

    //创建人
    @ApiModelProperty("创建人名称")
    private String createPerson;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;

    @ApiModelProperty("明细列表")
    private List<OrderSessionDetailDto>  orderSessionDetailDtoList;

}
