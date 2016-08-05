package com.rayn.jflask.framework.aop;

import com.rayn.jflask.framework.aop.proxy.Proxy;
import com.rayn.jflask.framework.aop.proxy.ProxyChain;

/**
 * AspectProxy
 * Created by Raynxxx on 2016/08/05.
 */
public abstract class AspectProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        return null;
    }

}
