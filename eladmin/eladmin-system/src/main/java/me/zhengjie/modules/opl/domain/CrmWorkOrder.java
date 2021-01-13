package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-07 13:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrmWorkOrder implements Serializable {

    //主键id
    @ApiModelProperty("主键id")
    private Long id;

    //功能完善表名称
    @ApiModelProperty("功能完善表名称")
    private String name;

    //业务类型
    @ApiModelProperty("业务类型")
    private Long entityType;

    //所有人
    @ApiModelProperty("所有人")
    private Long ownerId;

    //所属部门
    @ApiModelProperty("所属部门")
    private Long dimDepart;

    //工作流阶段名称
    @ApiModelProperty("工作流阶段名称")
    private String workflowStageName;

    //审批提交人
    @ApiModelProperty("审批提交人")
    private Long applicantId;

    //创建人
    @ApiModelProperty("创建人")
    private Long createdBy;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createdAt;

    //修改人
    @ApiModelProperty("修改人")
    private Long updatedBy;

    //修改时间
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp updatedAt;

    //锁定状态（未锁定：1，已锁定：2）
    @ApiModelProperty("锁定状态（未锁定：1，已锁定：2）")
    private Integer lockStatus;

    //审批状态（0：待提交，1：审批中，2：审批拒绝，3：审批通过，4：撤回）
    @ApiModelProperty("审批状态（0：待提交，1：审批中，2：审批拒绝，3：审批通过，4：撤回）")
    private Integer approvalStatus;

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

    //是否从crm同步过来， 是： 1    否：0
    @ApiModelProperty("是否从crm同步过来， 是： 1    否：0")
    private Boolean isCrm;

    //crm的id
    @ApiModelProperty("crm的id")
    private Long crmId;

}
