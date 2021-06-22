package io.muzoo.ooc.webapp.basic;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.util.logging.FileHandler;

public class Webapp {

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        File doceBase = new File("src/main/webapp/");
        doceBase.mkdirs();

        try {
            Context ctx = tomcat.addWebapp("",doceBase.getAbsolutePath());

            HomeServlet homeServlet = new HomeServlet();
            Tomcat.addServlet(ctx, HomeServlet.class.getSimpleName(), homeServlet);

            ctx.addServletMapping("/index.jsp", HomeServlet.class.getSimpleName());

            AdminServlet adminServlet = new AdminServlet();
            Tomcat.addServlet(ctx, AdminServlet.class.getSimpleName(), adminServlet);
            ctx.addServletMapping("/admin", AdminServlet.class.getSimpleName());

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }


}
