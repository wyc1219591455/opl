package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.*;

import java.io.Serializable;
import java.util.List;

/**
 * @program: eladmin
 * @description: 服务分类子数据前端页面展示类
 * @author: ming.cao
 * @create: 2021-02-24 11:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCatalogVo implements Serializable {
    @ApiModelProperty(value = "服务分类条目子表")
    private SubServiceCatalog subServiceCatalog;

    @ApiModelProperty(value = "服务分类条目中的未被选择的工单分类")
    private List<TrequestCategory> categoryLeftList;

    @ApiModelProperty(value = "服务分类条目中的已被选择的工单分类")
    private List<TrequestCategory> categoryRightList;

    @ApiModelProperty(value = "服务分类条目中的服务台")
    private RequestQueues requestQueues;

    @ApiModelProperty(value = "服务分类条目中的关联部门")
    private List<DeptVo> serviceCatalogRelateDept;
}
