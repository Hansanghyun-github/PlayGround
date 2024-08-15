package org.example.dynamicProxy;

public class BImpl implements BInterface{
    @Override
    public String call() {
        System.out.println("호출");
        return "b";
    }
}
