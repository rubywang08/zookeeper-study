package com.my;

public class ProtocolImpl implements IProtocol {
    @Override
    public String printProtocol(String msg) {
        return "I'm protocol :" + msg;
    }
}
