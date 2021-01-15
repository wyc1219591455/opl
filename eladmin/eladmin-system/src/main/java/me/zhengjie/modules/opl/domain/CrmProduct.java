package me.zhengjie.modules.opl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @program: meeting_structure
 * @description: crm产品
 * @author: ming.cao
 * @create: 2021-01-14 15:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrmProduct implements Serializable {

    //主键id
    private Long id;

    //产品编码
    private String productNo;

    //产品名称
    private String productName;

    //标准价格
    private BigDecimal priceUnit;

    //可转资产
    private String fscTrackedAsAsset;

    //产品目录
    private String parentId;

    //产品所有人
    private String ownerId;

    //销售单位
    private String unit;

    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp createdAt;

    //最新修改日
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp updatedAt;

    //创建人
    private String createdBy;

    //修改人
    private String updatedBy;

    //启用状态
    private Integer enableStatus;

    //组合产品
    private Boolean configurable;

    //独立销售产品
    private Boolean independentProduct;

    //生效日期
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp startDate;

    //失效日期
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Timestamp endDate;

    //业务类型
    private String entityType;

    //所属部门
    private String dimDepart;

    //汇总计算
    private String aggregate;

    //配置
    private String configurationType;

    //配置时机
    private String configurationEvent;

    //产品型号-标准
    private String fscProductModel;

    //产品规格
    private String fscProductSpec;

    //产品类型
    private String fscProductType;

    //需要回收
    private String fscNeedReturn;

    //crm中的id
    private String crmId;

}
