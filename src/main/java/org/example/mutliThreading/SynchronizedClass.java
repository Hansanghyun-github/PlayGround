package org.example.mutliThreading;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SynchronizedClass {
    private final InternalClass internalClass;
    public synchronized void method1() {
        internalClass.internalMethod1();
        System.out.println("method1");
    }
    public synchronized void method2() {
        internalClass.internalMethod2();
        System.out.println("method2");
    }

}
