package me.zhengjie.modules.opl.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
/**
 * @program: 工单子单
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-19 13:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderDto {

    //主键
    @ApiModelProperty("主键")
    private Integer id;

    //主题
    @ApiModelProperty("父工单")
    private String parentNo;

    //主题
    @ApiModelProperty("主题")
    private String topic;

    //工单编号
    @ApiModelProperty("工单编号")
    private String serialNo;

    //期望完成时间
    @ApiModelProperty("期望完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp planCompTime;

    //工单创建人
    @ApiModelProperty("工单创建人")
    private String createdPerson;

    //创建人工号
    @ApiModelProperty("创建人工号")
    private String jobNumber;

    @ApiModelProperty("所属部门")
    private String deptName;

    @ApiModelProperty("所属部门Id")
    private Integer deptId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createAt ;

    //项目名称
    @ApiModelProperty("项目名称")
    private String projectName;

    //机型
    @ApiModelProperty("机型")
    private String model;

    //事项日期
    @ApiModelProperty("事项日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp matterDate;

    //SN编码
    @ApiModelProperty("SN编码")
    private String snNo;

    //终端客户
    @ApiModelProperty("终端客户")
    private String ultimateCustomer;

    //问题分类（外观不良：1 功能不良：2）
    @ApiModelProperty("问题分类（外观不良：1 功能不良：2）")
    private Integer problemType;

    //问题附件
    @ApiModelProperty("问题附件")
    private String problemAttach;

    //问题描述：1.什么问题：
    @ApiModelProperty("问题描述：1.什么问题：")
    private String problemDesc;

    //问题描述：2.问题发生位置
    @ApiModelProperty("问题描述：2.问题发生位置")
    private String problemPath;

    //问题描述：3.现场状况
    @ApiModelProperty("问题描述：3.现场状况")
    private String siteCondition;

    //问题描述：4.施救动作
    @ApiModelProperty("问题描述：4.施救动作")
    private String rescueAction;

    //FAE负责人
    @ApiModelProperty("FAE负责人")
    private String faeHeader;

    //工单分类（质量：1，需求：2）
    @ApiModelProperty("工单分类（质量：1，需求：2）")
    private Integer workOrderType;

    @ApiModelProperty("opl工单状态 工单状态 0：新创建 1：待受理 2：处理中 3：已完成 4：已关闭 5：已取消")
    private Integer orderStatus;

    @ApiModelProperty("工单分类描述")
    private String typeDesc;

    @ApiModelProperty("是否允许转派 1可以转派  0不可以转派")
    private Integer isTransfer;

    @ApiModelProperty("工单状态描述")
    private String orderDesc;



}


