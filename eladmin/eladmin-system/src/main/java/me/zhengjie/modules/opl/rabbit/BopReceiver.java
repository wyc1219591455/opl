package me.zhengjie.modules.opl.rabbit;

import me.zhengjie.utils.ListenerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: eladmin
 * @description: test
 * @author: ming.cao
 * @create: 2021-03-24 11:07
 **/
@Component
public class BopReceiver {

    private Logger logger = LoggerFactory.getLogger(BopReceiver.class);

/*    @Autowired
    ListenerUtil listenerUtil;

    *//**
     * <p>方法描述：监听消息队列存储数据到数库 </p >
     * @param message 日志消息
     * @author zlh
     * @since 2019/9/19 11:32
     * @return
     *//*
    @RabbitListener(queues = "bopLog")
    public Boolean listenerBopLog(String message){
        String tableName = "log_bop";
        Boolean bopLog = listenerUtil.lister(message, tableName, logger, "存储业务日志失败");
        return bopLog;
    }*/
}
