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
 * @create: 2021-01-13 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderCriteria {
    //主题
    @ApiModelProperty("主题")
    private String topic;

    //工单编号
    @ApiModelProperty("工单编号")
    private String serialNo;


    //创建人工号
    @ApiModelProperty("创建人工号,前端不传")
    private String jobNumber;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createAt ;

    //机型
    @ApiModelProperty("机型")
    private String model;


    //工单分类（质量：1，需求：2）
    @ApiModelProperty("工单分类（质量：1，需求：2）")
    private Integer workOrderType;


    @ApiModelProperty("opl工单状态 工单状态 0：新创建 1：待受理 2：处理中 3：已完成 4：已关闭 5：已取消")
    private List<Integer> orderStatusList;


    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp startTime ;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp endTime ;

}
