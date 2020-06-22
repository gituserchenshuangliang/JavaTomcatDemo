package com.tomcat.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * one servlet for tomcat
 * @author Cherry
 * 2020年5月25日
 */
@WebServlet(name="one",urlPatterns="/one")
public class ServletOne extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Content-type", "text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("中国梦---ServletOne");
    }
}
