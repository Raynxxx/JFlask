package com.rayn.jflask.framework.aop;

import com.rayn.jflask.framework.aop.proxy.Proxy;
import com.rayn.jflask.framework.aop.proxy.ProxyChain;

import java.lang.reflect.Method;

/**
 * AspectProxy
 * Created by Raynxxx on 2016/08/05.
 */
public abstract class AspectProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        Object result;
        try {
            if (filter(targetClass, targetMethod, params)) {
                before(targetClass, targetMethod, params);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable ex) {
            afterThrow(targetClass, targetMethod, params, ex);
            throw ex;
        }
        afterReturn(targetClass, targetMethod, params, result);
        return result;
    }

    public boolean filter(Class<?> targetClass, Method targetMethod, Object[] params) {
        return true;
    }

    public void before(Class<?> targetClass, Method targetMethod, Object[] params) {

    }

    public void after(Class<?> targetClass, Method targetMethod, Object[] params) {

    }

    public void afterThrow(Class<?> targetClass, Method targetMethod, Object[] params, Throwable ex) {

    }

    public void afterReturn(Class<?> targetClass, Method targetMethod, Object[] params, Object result) {

    }
}
