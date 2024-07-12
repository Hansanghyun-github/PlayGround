package org.example.countDownLatch;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
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
        CountDownLatch latch1 = new CountDownLatch(100);
        CountDownLatch latch2 = new CountDownLatch(100);
        CountDownLatch latch3 = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new SyncWork(latch1, latch2, latch3).start();
        }

        // finish main
        System.out.println("main thread finished");

        try {
            latch3.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main thread finished");
    }
}
