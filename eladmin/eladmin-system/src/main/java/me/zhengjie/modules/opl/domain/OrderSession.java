package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * @program: 工单具体信息
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-20 13:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSession {

    //主键id
    @ApiModelProperty("主键id")
    private Integer id;

    //功能完善表名称
    @ApiModelProperty("工单主表ID")
    private String transId;

    //业务类型
    @ApiModelProperty("工单操作 1新建 2确认受理 3转派 4回复工单 5取消工单 6重开工单 7关闭工单 8派发工单")
    private Integer orderType;

    //所有人
    @ApiModelProperty("具体描述")
    private String description;


    //创建人
    @ApiModelProperty("创建人")
    private String createUserId;

    //创建日期
    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp createDateTime;

}
