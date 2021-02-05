package me.zhengjie.modules.opl.service.impl;


import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToDeptMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @program: eladmin
 * @description:
 * @author: yuchao.wang
 * @create: 2021-01-13 19:56
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogToDeptServiceImpl implements ServiceCatalogToDeptService {

    private final ServiceCatalogToDeptMapper serviceCatalogToDeptMapper;

    @Override
    public List<SubServiceCatalogDto> findSubCatalogById(String userId) {
       return  serviceCatalogToDeptMapper.findAssociationById(userId);
    }

    @Override
    public List<ServiceCatalogDto> findParentCatalogById(String userId) {
        return  serviceCatalogToDeptMapper.findParentAssociationById(userId);
    }


    @Override
    public List<ServiceCatalogDto> findAllCatalogById() {
        String jobNumber = SecurityUtils.getCurrentUsername();
        List<ServiceCatalogDto> serviceCatalogDtoList= serviceCatalogToDeptMapper.findParentAssociationById(jobNumber);
        List<SubServiceCatalogDto> subServiceCatalogs=serviceCatalogToDeptMapper.findAssociationById(jobNumber);
        if(ObjectUtil.isNotEmpty(serviceCatalogDtoList))
        {
            for(ServiceCatalogDto serviceCatalogDto:serviceCatalogDtoList)
            {
                List<Integer> catalogList=new ArrayList<>();
                catalogList.add(serviceCatalogDto.getCatalogId());
                List<SubServiceCatalogDto> serviceList = subServiceCatalogs.stream().filter(catalogDto -> catalogList.contains(catalogDto.getParentId())).collect(Collectors.toList());
                serviceCatalogDto.setSubServiceCatalogDtoList(serviceList);
            }
        }
        return serviceCatalogDtoList;
    }

    @Override
    public void insertCatalogToDeptAssociation(ServiceCatalogToDept serviceCatalogToDept) {

        serviceCatalogToDeptMapper.insertCatalogToDeptAssociation(serviceCatalogToDept);

    }
}
