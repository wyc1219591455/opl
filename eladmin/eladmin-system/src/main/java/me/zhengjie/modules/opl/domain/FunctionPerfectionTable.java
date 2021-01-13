package me.zhengjie.modules.opl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: crm接入数据
 * @author: ming.cao
 * @create: 2021-01-06 16:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionPerfectionTable implements Serializable {

    //crm的id
    private Long id;

    private String name;

    private Long entityType;

    private Long ownerId;

    private Long dimDepart;

    private String workflowStageName;

    private Long applicantId;

    private Long createdBy;

    private Timestamp createdAt;

    private Long updatedBy;

    private Timestamp updatedAt;

    private Integer lockStatus;

    private Integer approvalStatus;

    //主题
    private String customItem1__c;

    //
   // private String customItem16__c;

    //期望完成时间
    private Timestamp customItem2__c;

    //创建人
    private String customItem3__c;

    //创建人工号
    private String customItem4__c;

    //项目名称
    private String customItem5__c;

    //机型
    private String customItem6__c;

    //事项日期
    private Timestamp customItem7__c;

    //SN编号
    private String customItem8__c;

    //终端客户
    private String customItem9__c;

    //问题分类
    private Integer customItem10__c;

    //问题附件
    private String customItem11__c;

    //问题描述：1.什么问题：
    private String customItem12__c;

    //问题描述：2.问题发生位置：
    private String customItem13__c;

    //问题描述：3.现场状况：
    private String customItem14__c;

    //施救动作
    private String customItem15__c;

    //FAE负责人
    private String customItem16__c;

    //工单分类
    private Integer customItem17__c;

    //状态 成功 1   失败 2
    private Integer customItem18__c;

    //原因分析
    private String customItem19__c;

    //改善对策
    private String customItem20__c;

    //附件
    private String customItem21__c;

    //所属部门
    private String customItem22__c;
}
