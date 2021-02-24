package me.zhengjie.modules.opl.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.modules.opl.domain.ServiceCatalogRelateDept;
import me.zhengjie.modules.opl.domain.ServiceCatalogToCategory;
import me.zhengjie.modules.opl.domain.ServiceCatalogToQueues;
import me.zhengjie.modules.opl.domain.SubServiceCatalog;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogCriteria {

    @ApiModelProperty(value = "服务分类条目子表")
    private SubServiceCatalog subServiceCatalog;

    @ApiModelProperty(value = "服务分类条目中的工单分类")
    private List<ServiceCatalogToCategory>      serviceCatalogToCategoryList;

    @ApiModelProperty(value = "服务分类条目中的服务台")
    private ServiceCatalogToQueues serviceCatalogToQueues;

    @ApiModelProperty(value = "服务分类条目中的关联部门")
    private List<ServiceCatalogRelateDept> serviceCatalogRelateDept;
}
