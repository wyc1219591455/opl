package me.zhengjie.modules.opl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.opl.domain.*;
import me.zhengjie.modules.opl.mapper.*;
import me.zhengjie.modules.opl.service.ServiceCatalogService;
import me.zhengjie.modules.opl.service.dto.CatalogCriteria;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.modules.opl.service.dto.SubServiceCatalogDto;
import me.zhengjie.utils.PageHelpResultUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * @program: eladmin
 * @description: 服务分类条目
 * @author: yuchao.wang
 * @create: 2021-01-20 16:05
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServiceCatalogServiceImpl implements ServiceCatalogService {


    private final ServiceCatalogMapper serviceCatalogMapper;
    private final SubServiceCatalogMapper subServiceCatalogMapper;
    private final ServiceCatalogToCategoryMapper serviceCatalogToCategoryMapper;
    private final ServiceCatalogToQueuesMapper serviceCatalogToQueuesMapper;
    private final ServiceCatalogRelateDeptMapper serviceCatalogRelateDeptMapper;

    @Override
    public Map<String, Object> findAllCatalog(Pageable pageable) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<ServiceCatalogDto> serviceCatalogDtoList = serviceCatalogMapper.findAllCatalog();
        for(ServiceCatalogDto serviceCatalogDto:serviceCatalogDtoList)
        {
            serviceCatalogDto.setSubServiceCatalogDtoList(subServiceCatalogMapper.findSubCatalogByParentId(serviceCatalogDto.getCatalogId()));
        }
        PageInfo<ServiceCatalogDto> pageInfo = new PageInfo(serviceCatalogDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findParentCatalog(Pageable pageable) {

        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<ServiceCatalogDto> serviceCatalogDtoList = serviceCatalogMapper.findAllCatalog();
        PageInfo<ServiceCatalogDto> pageInfo = new PageInfo(serviceCatalogDtoList);
        return PageHelpResultUtil.toPage(pageInfo);
    }

    @Override
    public Map<String, Object> findSubCatalog(Integer parentId,Pageable pageable) {

        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<SubServiceCatalogDto> subServiceCatalogDtos = subServiceCatalogMapper.findSubCatalogByParentId(parentId);
        PageInfo<SubServiceCatalogDto> pageInfo = new PageInfo(subServiceCatalogDtos);
        return PageHelpResultUtil.toPage(pageInfo);
    }



    @Override
    public void insertCatalog(ServiceCatalog serviceCatalog) {

        String createName = SecurityUtils.getCurrentUsername();
        Timestamp createDate = new Timestamp(new Date().getTime());
        serviceCatalog.setCreateDateTime(createDate);
        serviceCatalog.setCreateUserId(createName);
        serviceCatalog.setStatus(1);
        serviceCatalogMapper.insertCatalog(serviceCatalog);

    }
    @Override
    public void insertSubCatalog(CatalogCriteria catalogCriteria) {
        SubServiceCatalog subServiceCatalog=catalogCriteria.getSubServiceCatalog();
        String createName = SecurityUtils.getCurrentUsername();
        Timestamp createDate = new Timestamp(new Date().getTime());
        subServiceCatalog.setCreateDateTime(createDate);
        subServiceCatalog.setCreateUserId(createName);
        subServiceCatalog.setStatus(1);
        subServiceCatalogMapper.insertSubCatalog(subServiceCatalog);
        List<ServiceCatalogToCategory> serviceCatalogToCategoryList =catalogCriteria.getServiceCatalogToCategoryList();
        for(ServiceCatalogToCategory serviceCatalogToCategory:serviceCatalogToCategoryList)
        {
            serviceCatalogToCategory.setCreateDateTime(createDate);
            serviceCatalogToCategory.setCreateUserId(createName);
            serviceCatalogToCategory.setStatus(1);
        }
        serviceCatalogToCategoryMapper.batchInsert(serviceCatalogToCategoryList);
        ServiceCatalogToQueues serviceCatalogToQueues =catalogCriteria.getServiceCatalogToQueues();
        serviceCatalogToQueuesMapper.insert(serviceCatalogToQueues);
        List<ServiceCatalogRelateDept> serviceCatalogRelateDeptList=catalogCriteria.getServiceCatalogRelateDept();
        for(ServiceCatalogRelateDept serviceCatalogRelateDept:serviceCatalogRelateDeptList)
        {
            serviceCatalogRelateDept.setCreateDateTime(createDate);
            serviceCatalogRelateDept.setCreateUserId(createName);
        }
        serviceCatalogRelateDeptMapper.batchInsert(serviceCatalogRelateDeptList);


    }

//    @Override
//    public void insertSubCatalog(SubServiceCatalog subServiceCatalog) {
//
//        String createName = SecurityUtils.getCurrentUsername();
//        Timestamp createDate = new Timestamp(new Date().getTime());
//        subServiceCatalog.setCreateDateTime(createDate);
//        subServiceCatalog.setCreateUserId(createName);
//        subServiceCatalog.setStatus(1);
//        subServiceCatalogMapper.insertSubCatalog(subServiceCatalog);
//
//    }

    @Override
    public void updateParentCatalog(ServiceCatalog serviceCatalog) {

        String modifyName = SecurityUtils.getCurrentUsername();
        Timestamp modifyDate = new Timestamp(new Date().getTime());
        serviceCatalog.setModifyDateTime(modifyDate);
        serviceCatalog.setModifyUserId(modifyName);
        deleteOk(serviceCatalog.getCatalogId());
        serviceCatalogMapper.updateCatalog(serviceCatalog);

    }

    @Override
    public void updateSubCatalog(SubServiceCatalog subServiceCatalog) {

        String modifyName = SecurityUtils.getCurrentUsername();
        Timestamp modifyDate = new Timestamp(new Date().getTime());
        subServiceCatalog.setModifyDateTime(modifyDate);
        subServiceCatalog.setModifyUserId(modifyName);
        subDeleteOk(subServiceCatalog.getCatalogId());
        subServiceCatalogMapper.updateSubCatalog(subServiceCatalog);

    }

    /**
     * 服务分类主表检验是否还有在使用的字表
     *
     * @param catalogId
     * @return void
     */
    private void deleteOk(Integer catalogId) {

        // 若查询结果非空，即数据库中存在使用中的此控制器编号
        if (serviceCatalogMapper.findOnUsedSub(catalogId) != 0) {
            throw new BadRequestException("该服务分类条目下还存在正在使用的子条目");
        }
    }

    /**
     * 服务分类子表检验是否还有在使用的工单
     *
     * @param catalogId
     * @return void
     */
    private void subDeleteOk(Integer catalogId) {

        // 若查询结果非空，即数据库中存在使用中的此控制器编号
        if (subServiceCatalogMapper.findOnUsedOrder(catalogId) != 0) {
            throw new BadRequestException("改服务分类下存在未关闭的工单");
        }
    }

}
