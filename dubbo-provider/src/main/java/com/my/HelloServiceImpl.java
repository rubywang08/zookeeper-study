package com.my;

public class HelloServiceImpl implements IHello{
    @Override
    public String sayHello(String msg) {
        return "hello" + msg;
    }
}
