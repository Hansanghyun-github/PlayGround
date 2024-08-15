package org.example.dynamicProxy;

public class AImpl implements AInterface{
    @Override
    public String call() {
        System.out.println("호출");
        return "a";
    }
}