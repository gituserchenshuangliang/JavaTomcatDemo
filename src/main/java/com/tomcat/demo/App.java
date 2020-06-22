package com.tomcat.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import com.tomcat.demo.config.ConfigContext;

/**
 * Tomcat嵌入的简单例子
 * 
 * @author Cherry 2020年5月25日
 */
public class App {

    @SuppressWarnings("static-access")
    public static void main(String[] args)throws LifecycleException, IOException {
        //启动Tomcat
        new App().run();
    }
    
    public static void run() throws IOException, LifecycleException {
        //基础目录
        Path baseDir = Files.createTempDirectory("baseDir");
        //文档资源目录
        Path docDir = Files.createTempDirectory("docDir");

        Tomcat tomcat = new Tomcat();
        
        // 设置绑定端口
        Connector connector = new Connector();
        connector.setPort(8080);
        
        //tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);
        
        tomcat.getHost().setName("192.168.0.102");
        tomcat.getHost().setAutoDeploy(false);
        
        tomcat.getEngine().setName("InstanceName");
        
        tomcat.setBaseDir(baseDir.toFile().getAbsolutePath());
        
        // 创建应用上下文
        String contextPath = "/tom";
        StandardContext context = null;
        
        //添加Context到Tomcat容器
        context = (StandardContext) tomcat.addWebapp(contextPath,docDir.toFile().getAbsolutePath());
        //添加Context类加载器
        context.setParentClassLoader(App.class.getClassLoader());
        context.setUseRelativeRedirects(false);
        
        // Tomcat配置Context
        ConfigContext.configure(context, tomcat, contextPath);

        // tomcat 启动jar扫描设置为跳过所有，避免与框架结合出现 jar file not found exception
        System.setProperty("tomcat.util.scan.StandardJarScanFilter.jarsToSkip","\\,*");

        tomcat.start();
        tomcat.getServer().await();
    }
}
