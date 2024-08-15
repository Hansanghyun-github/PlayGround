package org.example.dynamicProxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class JdkDynamicProxyTest {
    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        AInterface proxy = (AInterface)
                Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]
                        {AInterface.class}, handler);
        proxy.call();
        System.out.println("targetClass=" + target.getClass());
        System.out.println("proxyClass=" + proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface)
                Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]
                        {BInterface.class}, handler);
        proxy.call();
        System.out.println("targetClass=" + target.getClass());
        System.out.println("proxyClass=" + proxy.getClass());
    }
}
