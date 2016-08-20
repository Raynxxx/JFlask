package com.rayn.jflask.framework.orm;

import com.rayn.jflask.framework.annotation.entity.Transactional;
import com.rayn.jflask.framework.aop.proxy.Proxy;
import com.rayn.jflask.framework.aop.proxy.ProxyChain;
import com.rayn.jflask.framework.orm.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * TransactionProxy
 * Created by Raynxxx on 2016/08/05.
 */
public class TransactionProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Method targetMethod = proxyChain.getTargetMethod();
        // @Transactional 注解进行事务管理
        if (targetMethod.isAnnotationPresent(Transactional.class)) {
            try {
                // 开启事务
                DatabaseHelper.beginTransaction();
                logger.debug("[JFlask] begin transaction");

                // 执行方法
                result = proxyChain.doProxyChain();

                // 提交事务
                DatabaseHelper.commitTransaction();
                logger.debug("[JFlask] commit transaction");
            } catch (Exception e) {
                // 异常, 回滚事务
                DatabaseHelper.rollbackTransaction();
                logger.debug("[JFlask] rollback transaction");
                throw e;
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }

}
