package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.CrmProduct;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.CrmProductMapper;
import me.zhengjie.modules.opl.service.CrmProductService;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.utils.PageHelpResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description:
 * @author: ming.cao
 * @create: 2021-01-15 16:11
 **/
@Service
@RequiredArgsConstructor
public class CrmProductServiceImpl implements CrmProductService {

    private final CrmProductMapper crmProductMapper;

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<CrmProduct> crmProductList = crmProductMapper.findAll();
        PageInfo<CrmProduct> pageInfo1 = new PageInfo<>(crmProductList);
        return PageHelpResultUtil.toPage(pageInfo1);
    }
}
