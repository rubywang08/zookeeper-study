package com.my;

public class HelloServiceImplCluster implements IHello{
    @Override
    public String sayHello(String msg) {

        return "hello2 cluster 演示" + msg;
    }
}
