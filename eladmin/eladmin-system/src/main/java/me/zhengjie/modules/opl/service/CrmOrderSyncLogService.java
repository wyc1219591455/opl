package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.opl.domain.CrmOrderSyncLog;

/**
 * @program: eladmin
 * @description: 工单同步log业务
 * @author: ming.cao
 * @create: 2021-02-03 16:36
 **/
public interface CrmOrderSyncLogService {
    /**
     * @param crmOrderSyncLog
     * @return void
     * @throws
     * @title: insert
     * @description: insert
     * @date: 2021/2/3 16:48
     * @author: ming.cao
     */
    void insert(CrmOrderSyncLog crmOrderSyncLog);
}
