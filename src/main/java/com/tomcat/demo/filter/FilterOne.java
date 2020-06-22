package com.tomcat.demo.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
public class FilterOne implements Filter {
    public static Logger logger = Logger.getLogger(FilterOne.class.getName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Filter 初始化参数!");
        Enumeration<String> fConfig = filterConfig.getInitParameterNames();
        fConfig.asIterator().forEachRemaining((s) -> {
            String k = s;
            String v = filterConfig.getInitParameter(k);
            logger.info(k+":"+v);
        });
        
        ServletContext context = filterConfig.getServletContext();
        logger.info("Context 初始化参数!");
        Enumeration<String> cConfig = context.getInitParameterNames();
        cConfig.asIterator().forEachRemaining((s) -> {
            String k = s;
            String v = context.getInitParameter(k);
            logger.info(k+":"+v);
        });
        
        String msg = context.getServerInfo();
        logger.info("servletInfo:"+msg);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        //logger.info(String.format("Filter doFilter: remote host: [%s]",request.getRemoteHost()));
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
