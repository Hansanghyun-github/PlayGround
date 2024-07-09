package org.example.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class SyncWork extends Thread{
    private CountDownLatch latch;

    public SyncWork(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + Thread.currentThread().getId() + " is running!");

        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread " + Thread.currentThread().getId() + " has finished!");

    }
}
