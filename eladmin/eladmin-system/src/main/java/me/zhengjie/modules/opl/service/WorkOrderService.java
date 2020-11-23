package me.zhengjie.modules.opl.service;

import me.zhengjie.modules.system.domain.User;


/**
 * @author admin
 * @version 1.0.0
 * @ClassName WorkOrderService.java
 * @Description TODO
 * @createTime 2020年10月28日 11:01:00
 */

public interface WorkOrderService {
    /**
     * 获取当前用户信息
     * @return
     */
   User queryUserInfo();
}
