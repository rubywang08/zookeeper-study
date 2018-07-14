package com.my;

public class Main {
    public static void main(String[] args) {
        //默认会使用Spring容器来启动服务
        com.alibaba.dubbo.container.Main.main(new String[]{"spring","log4j"});
    }
}
