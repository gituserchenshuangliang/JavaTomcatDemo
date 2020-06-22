package com.tomcat.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter(filterName="two",urlPatterns="/two/*")
public class FilterTwo implements Filter{
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FilterTwo");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        System.out.println("doFiltrTwo");
        chain.doFilter(request, response);
    }

}
