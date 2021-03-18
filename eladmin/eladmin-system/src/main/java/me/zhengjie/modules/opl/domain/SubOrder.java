package me.zhengjie.modules.opl.domain;


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
public class SubOrder {
    //主键
    @ApiModelProperty("主键")
    private Integer id;

    //主题
    @ApiModelProperty("父工单Id")
    private Integer parentNo;

    //主题
    @ApiModelProperty("主题")
    private String topic;

    //工单编号
    @ApiModelProperty("工单编号")
    private String serialNo;


    //创建人工号
    @ApiModelProperty("创建人工号")
    private String jobNumber;


    @ApiModelProperty("所属部门Id")
    private Integer deptId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createTime ;


    //问题附件
    @ApiModelProperty("问题附件")
    private String problemAttach;

    @ApiModelProperty("opl工单状态 工单状态 0：新创建 1：待受理 2：处理中 3：已完成 4：已关闭 5：已取消")
    private Integer orderStatus;

    @ApiModelProperty("工单分类描述")
    private String typeDesc;

    @ApiModelProperty("是否允许转派 1可以转派  0不可以转派")
    private Integer isTransfer;

    @ApiModelProperty("描述")
    private String orderDesc;

    @ApiModelProperty("接收人")
    private String receiver;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("判断是主单还是子单 1代表主单，0代表子单")
    private Integer orderType;

    @ApiModelProperty("最终开始处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp realOpTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp finishDateTime;

    @ApiModelProperty("完成人")
    private String finishUserId;

    @ApiModelProperty("取消时间")
    private Timestamp cancelDateTime;

    @ApiModelProperty("取消人")
    private String cancelUserId;

    @ApiModelProperty("关闭得分")
    private Integer closeScore;



    @ApiModelProperty("改善措施（完成工单）")
    private String measures;

    @ApiModelProperty("完成状态（完成工单）（成功1，无法处理2，失败3）")
    private Integer completeType;

    @ApiModelProperty("原因分析  (完成工单)")
    private String reason;

}
