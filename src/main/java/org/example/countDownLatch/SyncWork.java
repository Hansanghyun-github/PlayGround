package org.example.countDownLatch;

import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class SyncWork extends Thread{
    private CountDownLatch latch1;
    private CountDownLatch latch2;
    private CountDownLatch latch3;

    public static Map<Long, LocalTime> map1 = new ConcurrentHashMap<>();
    public static Map<Long, LocalTime> map2 = new ConcurrentHashMap<>();
    public static Map<Long, LocalTime> map3 = new ConcurrentHashMap<>();

    public SyncWork(CountDownLatch latch1, CountDownLatch latch2, CountDownLatch latch3) {
        this.latch1 = latch1;
        this.latch2 = latch2;
        this.latch3 = latch3;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        map1.put(Thread.currentThread().getId(), LocalTime.now());

        latch1.countDown();
        try {
            Thread.sleep(1000);
            latch1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        map2.put(Thread.currentThread().getId(), LocalTime.now());

        latch2.countDown();
        try {
            Thread.sleep(1000);
            latch2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        map3.put(Thread.currentThread().getId(), LocalTime.now());

        latch3.countDown();
    }
}
