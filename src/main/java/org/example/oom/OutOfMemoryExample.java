package org.example.oom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OutOfMemoryExample {
    public static void main(String[] args) throws InterruptedException {
        // have to set -Xmx256m option
        // when you want to make heap dump file, you can set -XX:+HeapDumpOnOutOfMemoryError option

        // make OutOfMemoryError
        System.out.println("Start to make OutOfMemoryError");

        for(int i = 0; i < 1000000000;i++) {
            doSomething();
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            list.add("hello jvm!" + " " + i + " " + System.currentTimeMillis() + " " + System.nanoTime() + " " + System.nanoTime());
        }

        System.out.println("End to make OutOfMemoryError");

        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        bq.offer(1, 1000, java.util.concurrent.TimeUnit.MILLISECONDS);

        bq.forEach(System.out::println);
        LocalDateTime.now();

    }

    private static void doSomething() {
        List<String> list = new ArrayList<>();
        list.add("hello jvm!");
        list.clear();
    }
}
