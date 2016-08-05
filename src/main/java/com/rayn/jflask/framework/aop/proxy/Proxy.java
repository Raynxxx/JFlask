package com.rayn.jflask.framework.aop.proxy;

/**
 * Proxy
 * Created by Raynxxx on 2016/08/05.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
