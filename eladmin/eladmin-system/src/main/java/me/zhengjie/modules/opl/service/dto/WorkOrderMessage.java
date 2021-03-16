package me.zhengjie.modules.opl.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: eladmin
 * @description: 工单信息推送使用
 * @author: ming.cao
 * @create: 2021-03-02 10:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderMessage extends SerialDto implements Serializable {

    @ApiModelProperty("主题")
    private String topic;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("状态")
    private String type;

    @ApiModelProperty("发起部门")
    private String dept;

    @ApiModelProperty("发起人")
    private String sponsor;

    @ApiModelProperty("终端客户")
    private String ultimateCustomer;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDate;

    @ApiModelProperty("期望完成日期")
    @DateTimeFormat(pattern="yyyy-MM-dd") // 入参格式化
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Timestamp hopeCompTime;

    @ApiModelProperty("到服务分类")
    private Integer serviceCatalogId;

    @ApiModelProperty("服务分类名称")
    private String serviceCatalogName;

    @ApiModelProperty("工单展示")
    private List<OrderSessionDto> orderShowDto;


}
