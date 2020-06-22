package com.tomcat.demo.servlet;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTwo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte[] in = Files.readAllBytes(Paths.get("src/main/resources/1.mp4"));
        String contentType = URLConnection.getFileNameMap().getContentTypeFor("1.mp4");
        response.setHeader("Content-type", contentType);
        response.getOutputStream().write(in);
    }
    
}
