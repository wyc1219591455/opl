package me.zhengjie.modules.opl.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 19:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSessionDetailDto {


    @ApiModelProperty(value = "主键")
    private long detailId;

    @ApiModelProperty(value = "orderSessionId")
    private long sessionId;

    @ApiModelProperty(value = "工单Id")
    private long transId;

    @ApiModelProperty(value = "工单单号")
    private String transOrderId;

    @ApiModelProperty(value = "操作详情")
    private String propName;

    @ApiModelProperty(value = "旧数据")
    private String oldValue;

    @ApiModelProperty(value = "新数据")
    private String newValue;

    @ApiModelProperty(value = "创建人工号")
    private String createUserId;

    @ApiModelProperty(value = "创建人姓名")
    private String createPerson;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;


    @ApiModelProperty(value = "表来源（0 主表crmworkorder ，1 附表 suborder）")
    private Integer originalType;

}
