package ru.netology;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Application {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        // Создаем контекст Tomcat
        Context context = tomcat.addContext("", null);

        // Контекст Spring
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);

        // Регистрация DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(appContext);
        String servletName = "dispatcher";
        Tomcat.addServlet(context, servletName, servlet);
        context.addServletMappingDecoded("/*", servletName);

        // Запуск сервера
        tomcat.start();
        tomcat.getServer().await();
    }
}