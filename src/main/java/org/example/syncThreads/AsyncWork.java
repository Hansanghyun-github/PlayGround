package org.example.syncThreads;

import java.util.concurrent.CountDownLatch;

public class AsyncWork extends Thread{
    CountDownLatch latch;

    public AsyncWork(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Hello from a thread! " + Thread.currentThread().getId());
        latch.countDown();
    }
}
