package me.zhengjie.modules.opl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.CrmWorkOrder;
import me.zhengjie.modules.opl.domain.Pageable;
import me.zhengjie.modules.opl.mapper.CrmWorkOrderMapper;
import me.zhengjie.modules.opl.service.CrmWorkOrderService;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderCriteria;
import me.zhengjie.modules.opl.service.dto.CrmWorkOrderDto;
import me.zhengjie.utils.PageHelpResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: eladmin
 * @description: crm业务逻辑实现
 * @author: ming.cao
 * @create: 2021-01-13 19:25
 **/
@Service
@RequiredArgsConstructor
public class CrmWorkOrderServiceImpl implements CrmWorkOrderService {

    private final CrmWorkOrderMapper crmWorkOrderMapper;

    @Override
    public void insert(CrmWorkOrderCriteria crmWorkOrderCriteria) {
        //获取opl工单号
        String maxSerialNo = getOplMaxNo();
        crmWorkOrderCriteria.setSerialNo(maxSerialNo);
        crmWorkOrderMapper.insert(crmWorkOrderCriteria);
    }

    @Override
    public void update(CrmWorkOrder crmWorkOrder) {
        crmWorkOrderMapper.update(crmWorkOrder);
    }

    @Override
    public List<CrmWorkOrder> findCrmOrderById(Integer id) {
        return  crmWorkOrderMapper.findCrmOrderById(id);
    }

    @Override
    public Map<String, Object> findAll(CrmWorkOrderCriteria criteria, Pageable pageable) {
        PageHelper.startPage(pageable.getPage(),pageable.getSize());
        List<CrmWorkOrderDto> tempList= crmWorkOrderMapper.findAll(criteria);
        PageInfo<CrmWorkOrderCriteria> pageInfo = new PageInfo(tempList);
        return PageHelpResultUtil.toPage(pageInfo);
    }


    @Override
    public List<CrmWorkOrder> findCrmOrderByOrderType(Integer type) {
        return crmWorkOrderMapper.findCrmOrderByOrderType(type);
    }

    private String getOplMaxNo(){
        //获取crm同步到opl最大编号的数据
        List<CrmWorkOrder> maxCrmWorkOrderList = crmWorkOrderMapper.findOplByMaxId();
        //获取时间编号
        String tempMaxSerialNo="";
        if (ObjectUtil.isNotEmpty(maxCrmWorkOrderList)) {
            tempMaxSerialNo = maxCrmWorkOrderList.get(0).getSerialNo();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String uid = "opl"+simpleDateFormat.format(new Date());
        System.out.println("uid"+uid);
        String serialNumber="";
        //如果不为空，进行判断，如果有数据则判断是否是今天的数据。如果没有今天的数据初始化为第一个，否则+1；
        //如果为空，直接设为今天第一个
        if (ObjectUtil.isNotEmpty(maxCrmWorkOrderList)){
               /* String tempMaxSerialNo = maxCrmWorkOrderList.get(0).getSerialNo();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String uid = "crm"+simpleDateFormat.format(new Date());*/
            System.out.println( "序号"+tempMaxSerialNo.substring(0, 11));
            if (uid.equals(tempMaxSerialNo.substring(0,11))){
                Integer intNumber=Integer.parseInt(tempMaxSerialNo.substring(11));
                intNumber++;
                serialNumber= uid+String.format("%04d",Integer.valueOf(intNumber));

            }else{
                serialNumber= uid+"0001";
            }
        }else{
            serialNumber= uid+"0001";
        }

        System.out.println(serialNumber);
        return serialNumber;
    }
}
