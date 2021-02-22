package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.ServiceCatalogRelateDept;
import me.zhengjie.modules.opl.mapper.ServiceCatalogRelateDeptMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogRelateDeptService;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogRelateDeptDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-02-08 16:07
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogRelateDeptServiceImpl implements ServiceCatalogRelateDeptService {

    private final ServiceCatalogRelateDeptMapper serviceCatalogRelateDeptMapper;

    @Override
    public void batchInsert(List<ServiceCatalogRelateDept> list) {
        serviceCatalogRelateDeptMapper.batchInsert(list);
    }

    @Override
    public void batchUpdate(ServiceCatalogRelateDeptCriteria criteria) {
        //先删
        serviceCatalogRelateDeptMapper.deleteByCatalogId(criteria.getCatalogId());
        if (ObjectUtil.isNotEmpty(criteria.getDeptIds())){
            //关联表
            List<ServiceCatalogRelateDept> list = new ArrayList<>();

            for (Integer deptId : criteria.getDeptIds()) {
                ServiceCatalogRelateDept serviceCatalogRelateDept = new ServiceCatalogRelateDept();
                serviceCatalogRelateDept.setCatalogId(criteria.getCatalogId());
                serviceCatalogRelateDept.setDeptId(deptId);
                serviceCatalogRelateDept.setStatus(1);
                serviceCatalogRelateDept.setCreateDateTime(new Timestamp(new Date().getTime()));
                serviceCatalogRelateDept.setCreateUserId(""+SecurityUtils.getCurrentUserId());
                list.add(serviceCatalogRelateDept);
            }
            //插入
            serviceCatalogRelateDeptMapper.batchInsert(list);
        }

    }

    @Override
    public void deleteByCatalogId(Integer catalogId) {
        serviceCatalogRelateDeptMapper.deleteByCatalogId(catalogId);
    }

    @Override
    public Map<String,Object> findByCatalogId(Integer catalogId) {
        List<ServiceCatalogRelateDeptDto> tempList =serviceCatalogRelateDeptMapper.findDeptDtoByCatalogId(catalogId);
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", tempList);
        map.put("totalElements", tempList.size());
        return map;
    }

}
