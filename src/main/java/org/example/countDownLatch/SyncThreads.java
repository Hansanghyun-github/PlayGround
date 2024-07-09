package org.example.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class SyncThreads {
    public static void main(String[] args) {
        //syncThreads();
        syncThreads2();

    }

    private static void syncThreads() {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new AsyncWork(latch).start();
        }

        System.out.println("Hello from the main thread!");

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished!");
    }

    private static void syncThreads2() {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new SyncWork(latch).start();
        }

        // finish main
        System.out.println("main thread finished");

    }
}
