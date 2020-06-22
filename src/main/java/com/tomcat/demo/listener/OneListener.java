package com.tomcat.demo.listener;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Server;

public class OneListener implements LifecycleListener {

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (event != null && event.getLifecycle() != null) {
            System.out.println(event.getLifecycle().getStateName() + "tom");
        }

        if (!(event.getLifecycle() instanceof Server)) {
            return;
        }

        if (!Lifecycle.AFTER_START_EVENT.equals(event.getType())) {
            return;
        }

        Server server = (Server) event.getLifecycle();
        
        System.out.println("server:" + server);
    }
}
