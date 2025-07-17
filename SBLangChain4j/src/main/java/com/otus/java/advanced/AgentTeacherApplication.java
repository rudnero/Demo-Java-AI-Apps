package com.otus.java.advanced;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AgentTeacherApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AgentTeacherApplication.class)
                .registerShutdownHook(true)
                .run();
    }
}