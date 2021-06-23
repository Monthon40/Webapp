package io.muzoo.ooc.webapp.basic;

import io.muzoo.ooc.webapp.basic.servlets.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class Webapp {

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        File doceBase = new File("src/main/webapp/");
        doceBase.mkdirs();

        try {
            Context ctx = tomcat.addWebapp("",doceBase.getAbsolutePath());

            ServletRouter servletRouter = new ServletRouter();
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }


}

//http://localhost:8082/login