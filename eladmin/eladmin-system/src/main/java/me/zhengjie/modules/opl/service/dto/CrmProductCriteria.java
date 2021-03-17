package me.zhengjie.modules.opl.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: eladmin
 * @description: crm产品信息
 * @author: ming.cao
 * @create: 2021-03-17 10:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrmProductCriteria implements Serializable {

    //产品编码
    private String productNo;

    //产品名称
    private String productName;

}
