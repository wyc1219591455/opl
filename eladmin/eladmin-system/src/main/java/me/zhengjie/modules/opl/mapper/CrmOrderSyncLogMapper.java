package me.zhengjie.modules.opl.mapper;

import me.zhengjie.modules.opl.domain.CrmOrderSyncLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: eladmin
 * @description: log日志
 * @author: ming.cao
 * @create: 2021-02-03 16:05
 **/
@Mapper
public interface CrmOrderSyncLogMapper {
    /**
     * @title: insert
     * @description: 新增
     * @date: 2021/2/3 16:28
     * @author: ming.cao
     * @param crmOrderSyncLog
     * @return void
     * @throws
     */
    void insert(CrmOrderSyncLog crmOrderSyncLog);


}
