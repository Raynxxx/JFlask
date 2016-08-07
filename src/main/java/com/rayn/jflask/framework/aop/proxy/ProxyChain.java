package com.rayn.jflask.framework.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ProxyChain
 * Created by Raynxxx on 2016/08/05.
 */
public class ProxyChain {

    private List<Proxy> proxyList;
    private int proxyIndex = 0;

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final Object[] methodParams;
    private final MethodProxy methodProxy;


    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,
                      Object[] methodParams, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
