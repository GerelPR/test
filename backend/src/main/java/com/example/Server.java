package com.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Server {
    public static void main(String[] args) throws LifecycleException {
        // Create an embedded Tomcat server
        Tomcat tomcat = new Tomcat();

        // Set the port for the server
        tomcat.setPort(8080);

        // Set the base directory (for temporary files)
        tomcat.setBaseDir("temp");

        // Add a web application context
        String webAppDir = System.getProperty("user.dir") + "/src/main/webapp/";
        tomcat.addWebapp("/", webAppDir);

        // Start the server
        tomcat.start();
        System.out.println("Server started on http://localhost:8080");

        // Keep the server running
        tomcat.getServer().await();
    }
}
