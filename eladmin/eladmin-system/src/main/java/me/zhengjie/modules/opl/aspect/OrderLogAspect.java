package me.zhengjie.modules.opl.aspect;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.Log;
import me.zhengjie.modules.opl.annotation.OrderLog;
import me.zhengjie.modules.opl.domain.CrmOrderSyncLog;
import me.zhengjie.modules.opl.service.CrmOrderSyncLogService;

import me.zhengjie.utils.RequestHolder;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ThrowableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: eladmin
 * @description: 工单log切面
 * @author: ming.cao
 * @create: 2021-02-04 11:42
 **/
@Component
@Aspect
@Slf4j
public class OrderLogAspect {

    /**
     * 工单同步log业务
     */
    private final CrmOrderSyncLogService crmOrderSyncLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public OrderLogAspect( CrmOrderSyncLogService crmOrderSyncLogService ) {
        this.crmOrderSyncLogService=crmOrderSyncLogService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(me.zhengjie.modules.opl.annotation.OrderLog)")
    public void orderLogPointcut(){
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }


    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
//    @AfterThrowing(pointcut = "orderLogPointcut()", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        CrmOrderSyncLog log = new CrmOrderSyncLog();
//        currentTime.remove();
//        log.setFailedReason(ThrowableUtil.getStackTrace(e));
//        log.setIsSuccess(0);
//        crmOrderSyncLogService.save(getUsername(),(ProceedingJoinPoint)joinPoint,log );
//
//    }

    private String getUsername() {
        try {
            return SecurityUtils.getCurrentUsername();
        }catch (Exception e){
            return "";
        }
    }

}
