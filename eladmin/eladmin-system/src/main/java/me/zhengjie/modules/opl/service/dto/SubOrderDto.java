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

    //主题
    @ApiModelProperty("描述")
    private String description;

    //工单编号
    @ApiModelProperty("工单编号")
    private String serialNo;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime ;


    //问题附件
    @ApiModelProperty("问题附件")
    private String problemAttach;

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

    @ApiModelProperty("处理人")
    private String receiver;

    @ApiModelProperty("处理人是否一致")
    private Boolean equalsTreat=false;

    @ApiModelProperty("发起人是否一致")
    private Boolean equalsCreate=false;

    @ApiModelProperty("判断当前用户是否是工单创建人 1为是 0为否")
    private Integer equalsCreate;

}


