package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Example: Get a bean and use it
        MyService myService = context.getBean(MyService.class);
        myService.performTask();
    }
}
