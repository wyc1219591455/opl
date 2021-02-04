package me.zhengjie.modules.opl.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.CrmOrderSyncLog;
import me.zhengjie.modules.opl.mapper.CrmOrderSyncLogMapper;
import me.zhengjie.modules.opl.service.CrmOrderSyncLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: eladmin
 * @description: 工单同步业务log实现
 * @author: ming.cao
 * @create: 2021-02-03 16:49
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CrmOrderSyncLogServiceImpl implements CrmOrderSyncLogService {

    private final CrmOrderSyncLogMapper crmOrderSyncLogMapper;

    @Override
    public void insert(CrmOrderSyncLog crmOrderSyncLog) {
        crmOrderSyncLogMapper.insert(crmOrderSyncLog);
    }

}
