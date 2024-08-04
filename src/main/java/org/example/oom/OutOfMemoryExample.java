package org.example.oom;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryExample {
    public static void main(String[] args) throws InterruptedException {
        // have to set -Xmx256m option
        Thread.sleep(10000);

        // make OutOfMemoryError
        System.out.println("Start to make OutOfMemoryError");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            list.add("hello jvm!" + " " + i + " " + System.currentTimeMillis() + " " + System.nanoTime() + " " + System.nanoTime());
        }

        System.out.println("End to make OutOfMemoryError");

    }
}
