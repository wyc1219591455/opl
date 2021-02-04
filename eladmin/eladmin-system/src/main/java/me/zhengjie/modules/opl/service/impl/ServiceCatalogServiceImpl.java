package me.zhengjie.modules.opl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.domain.ServiceCatalog;
import me.zhengjie.modules.opl.mapper.ServiceCatalogMapper;
import me.zhengjie.modules.opl.mapper.SubServiceCatalogMapper;
import me.zhengjie.modules.opl.service.ServiceCatalogService;
import me.zhengjie.modules.opl.service.dto.ServiceCatalogDto;
import me.zhengjie.utils.PageHelpResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public void insertCatalog(ServiceCatalog serviceCatalog) {

        serviceCatalogMapper.insertCatalog(serviceCatalog);

    }
}
