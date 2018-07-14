package com.my;

public class HelloServiceImplVersion2 implements IHello{
    @Override
    public String sayHello(String msg) {

        return "hello v2.0" + msg;
    }
}
