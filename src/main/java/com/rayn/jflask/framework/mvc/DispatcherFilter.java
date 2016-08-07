package com.rayn.jflask.framework.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * DispatcherFilter
 * Created by Raynxxx on 2016/08/07.
 */
@WebFilter(filterName = "DispatcherFilter", urlPatterns = "/*")
public class DispatcherFilter implements Filter {

    // logger
    private static Logger logger = LoggerFactory.getLogger(DispatcherFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("[JFlask][DispatcherFilter] {}", request.getServerName());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
