package org.example.oom;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryExample {
    public static void main(String[] args) {
        // have to set -Xmx256m option

        // make OutOfMemoryError
        System.out.println("Start to make OutOfMemoryError");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            list.add("hello jvm!" + i);
        }

        System.out.println("End to make OutOfMemoryError");

    }
}
