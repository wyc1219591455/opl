package me.zhengjie.modules.opl.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-13 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrmWorkOrderCriteria implements Serializable {
    //主键
    @ApiModelProperty("主键")
    private Integer id;

    //主题
    @ApiModelProperty("主题")
    private String topic;

    //工单编号
    @ApiModelProperty("工单编号")
    private String serialNo;

    //期望完成时间
    @ApiModelProperty("期望完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Timestamp planCompTime;

    //期望完成时间
    @ApiModelProperty("计划完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Timestamp hopeCompTime;

    //工单创建人
    @ApiModelProperty("工单创建人")
    private String createdPerson;

    //工单创建人
    @ApiModelProperty("工单修改人人")
    private String modifyPerson;

    //创建人工号
    @ApiModelProperty("创建人工号")
    private String jobNumber;

    @ApiModelProperty("所属部门")
    private String deptName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime ;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp modifyDateTime ;

    //项目名称
    @ApiModelProperty("项目名称")
    private String projectName;

    //机型
    @ApiModelProperty("机型")
    private String model;

    //事项日期
    @ApiModelProperty("事项日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
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

    @ApiModelProperty("工单分类描述")
    private String TypeDesc;

    @ApiModelProperty("opl工单状态 工单状态 0：新创建 1：待受理 2：处理中 3：已完成 4：已关闭 5：已取消")
    private Integer orderStatus;

    @ApiModelProperty("服务分类条目ID")
    private Integer catalogId;

    @ApiModelProperty("判断是主单还是子单 1代表主单，0代表子单")
    private Integer orderType;

    @ApiModelProperty("处理人（opl系统中的处理人）")
    private String receiver;


    @ApiModelProperty("改善措施（完成工单）")
    private String measures;

    @ApiModelProperty("完成状态（完成工单）（成功1，无法处理2，失败3）")
    private Integer completeType;

    @ApiModelProperty("原因分析  (完成工单)")
    private String reason;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp finishDateTime ;

    //工单创建人
    @ApiModelProperty("完成人")
    private String finishUserId;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp closeDateTime ;

    //工单创建人
    @ApiModelProperty("完成人")
    private String closeUserId;


    @ApiModelProperty("取消时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp cancelDateTime ;

    //工单创建人
    @ApiModelProperty("取消人")
    private String cancelUserId;

    //工单创建人
    @ApiModelProperty("评分")
    private Integer closeScore;





}
