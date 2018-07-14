package com.my;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Bootstrap620 {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext
                        ("META-INF/spring/dubbo-server-620.xml");
        context.start();
        System.in.read();//阻塞当前进程
    }
}
