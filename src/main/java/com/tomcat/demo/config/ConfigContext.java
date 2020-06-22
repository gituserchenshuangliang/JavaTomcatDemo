package com.tomcat.demo.config;

import java.io.File;
import java.util.logging.Logger;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import com.tomcat.demo.filter.FilterOne;
import com.tomcat.demo.listener.OneListener;
import com.tomcat.demo.servlet.ServletThree;
import com.tomcat.demo.servlet.ServletTwo;

/**
 * configure Resources,Servlet,Filter,Listener
 * 
 * @author Cherry 2020年5月25日
 */
public class ConfigContext {
    public static Logger logger = Logger.getLogger(ConfigContext.class.getName());
    public static void configure(StandardContext context, Tomcat tomcat,
            String contextPath) {
        configureResources(context);
        configureContextParameters(context);
        configureListeners(context);
        configureFilters(context);
        configureServlets(contextPath, tomcat, context);
    }

    public static void configureResources(StandardContext context) {
        String WORK_HOME = System.getProperty("user.dir");
        
        File classesDir = new File(WORK_HOME, "target/classes");
        File jarDir = new File(WORK_HOME, "lib");
        
        DirResourceSet dirSet = null;
        WebResourceRoot resources = new StandardRoot(context);
        if (classesDir.exists()) {
            dirSet = new DirResourceSet(resources,"/WEB-INF/classes", classesDir.getAbsolutePath(), "/");
            
            //dirSet = new DirResourceSet();
            //dirSet.setRoot(resources);
            //dirSet.setWebAppMount("/WEB-INF/classes");
            //dirSet.setBase(classesDir.getAbsolutePath());
            //dirSet.setInternalPath("/");
            
            resources.addPreResources(dirSet);
            
            logger.info("Classes Resources added to /WEB-INF/classes");
        } else if (jarDir.exists()) {
            dirSet = new DirResourceSet(resources,"/WEB-INF/lib", jarDir.getAbsolutePath(), "/");
            
            resources.addJarResources(dirSet);
            
            logger.info("JarLib Resources added to /WEB-INF/lib");
        } else {
            
            resources.addPreResources(new EmptyResourceSet(resources));
            
            logger.info("Resources is empty");
        }
        
        context.setResources(resources);
    }
    
    //配置servlet
    public static void configureServlets(String contextPath, Tomcat tomcat,
            StandardContext context) {

        // servlet第一种加载方法
        tomcat.addServlet(contextPath, "two", ServletTwo.class.getName());
        // 注意不要忘记设置Servlet路径映射
        context.addServletMappingDecoded("/two/*", "two");

        // servlet第二种加载方法
        Wrapper wrapper = context.createWrapper();
        wrapper.setName("three");
        wrapper.setServletClass(ServletThree.class.getName());
        context.addChild(wrapper);
        context.addServletMappingDecoded("/three/*", "three");

        // servlet第三种注解自动扫描
    }
    
    //配置Context初始参数
    public static void configureContextParameters(StandardContext context) {
        context.addParameter("A", "1");
        context.addParameter("B", "2");
    }

    public static void configureFilters(StandardContext context) {
        // Filter定义
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("filterOne");
        filterDef.setFilterClass(FilterOne.class.getName());
        filterDef.addInitParameter("paramA", "paramA");
        filterDef.addInitParameter("paramB", "paramA");
        context.addFilterDef(filterDef);
        // Filter路径映射
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("filterOne");
        filterMap.addURLPattern("/*");
        context.addFilterMap(filterMap);
        
        //过滤器注解扫描
    }

    public static void configureListeners(StandardContext context) {
        context.addApplicationEventListener(new OneListener());
    }
}
