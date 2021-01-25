package me.zhengjie.modules.opl.service.impl;


import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.ServiceCatalogToDept;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToDeptMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToDeptService;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void insertCatalogToDeptAssociation(ServiceCatalogToDept serviceCatalogToDept) {

        serviceCatalogToDeptMapper.insertCatalogToDeptAssociation(serviceCatalogToDept);

    }
}
