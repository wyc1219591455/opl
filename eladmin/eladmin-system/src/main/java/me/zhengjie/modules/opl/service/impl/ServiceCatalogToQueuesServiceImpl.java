package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.ServiceCatalogToQueues;
import me.zhengjie.modules.opl.mapper.ServiceCatalogToQueuesMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogToQueuesService;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogToQueuesCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogToQueuesCriteria2;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogToQueuesDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: eladmin
 * @description: 服务关联支持组业务逻辑实现
 * @author: ming.cao
 * @create: 2021-01-25 11:27
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogToQueuesServiceImpl implements ServiceCatalogToQueuesService {

    private final ServiceCatalogToQueuesMapper serviceCatalogToQueuesMapper;

    @Override
    public Map<String,Object> getQueueByCatalogId(Integer catalogId) {
        Map<String,Object> map = new LinkedHashMap<>();
        List<ServiceCatalogToQueuesDto> dtos = serviceCatalogToQueuesMapper.findByQueuesId(catalogId);
        map.put("content",dtos);
        map.put("totalElements",dtos.size());
        return map;
    }

    @Override
    @Transactional
    public void batchInsert(ServiceCatalogToQueuesCriteria criteria) {
        //非空校验
        isNotEmptyTest(criteria);

        //新增
        List<ServiceCatalogToQueues> tempList = criteria.getQueuesIds().stream().map(queuesId->{
            ServiceCatalogToQueues serviceCatalogToQueues = new ServiceCatalogToQueues();
            serviceCatalogToQueues.setCatalogId(criteria.getCatalogId());
            serviceCatalogToQueues.setQueuesId(queuesId);
            serviceCatalogToQueues.setCreateDateTime(new Timestamp(new Date().getTime()));
            serviceCatalogToQueues.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
            return serviceCatalogToQueues;
        }).collect(Collectors.toList());

        serviceCatalogToQueuesMapper.batchInsert(tempList);
    }

    @Override
    public void insert(ServiceCatalogToQueuesCriteria2 criteria2) {
        //非空校验
        if (ObjectUtil.isEmpty(criteria2.getCatalogId())){
            throw new BadRequestException("服务分类id不能为空！");
        }
        if (ObjectUtil.isEmpty(criteria2.getQueuesId())){
            throw new BadRequestException("支持组id不能为空！");
        }
        ServiceCatalogToQueues serviceCatalogToQueues = new ServiceCatalogToQueues();
        serviceCatalogToQueues.setCatalogId(criteria2.getCatalogId());
        serviceCatalogToQueues.setQueuesId(criteria2.getQueuesId());
        serviceCatalogToQueues.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
        serviceCatalogToQueues.setCreateDateTime(new Timestamp(new Date().getTime()));
        serviceCatalogToQueuesMapper.insert(serviceCatalogToQueues);
    }


    @Override
    @Transactional
    public void batchUpdate(ServiceCatalogToQueuesCriteria criteria) {
        //非空校验
        isNotEmptyTest(criteria);

        //删除以前全部服务关联支持组数据
        serviceCatalogToQueuesMapper.delByCatalogId(criteria.getCatalogId());

        //新增所有服务关联支持组部分
        List<ServiceCatalogToQueues> tempList = criteria.getQueuesIds().stream().map(queuesId->{
            ServiceCatalogToQueues serviceCatalogToQueues = new ServiceCatalogToQueues();
            serviceCatalogToQueues.setCatalogId(criteria.getCatalogId());
            serviceCatalogToQueues.setQueuesId(queuesId);
            serviceCatalogToQueues.setCreateDateTime(new Timestamp(new Date().getTime()));
            serviceCatalogToQueues.setCreateUserId(SecurityUtils.getCurrentUserId().intValue());
            return serviceCatalogToQueues;
        }).collect(Collectors.toList());

        serviceCatalogToQueuesMapper.batchInsert(tempList);

    }

    //非空校验
    private void isNotEmptyTest(ServiceCatalogToQueuesCriteria criteria){
        if (ObjectUtil.isEmpty(criteria)){
            throw new BadRequestException("参数不能为空！");
        }else {
            if (ObjectUtil.isEmpty(criteria.getCatalogId())){
                throw new BadRequestException("服务分类id不能为空！");
            }
            if (ObjectUtil.isEmpty(criteria.getQueuesIds())){
                throw new BadRequestException("支持组id不能为空！");
            }
        }
    }

}
