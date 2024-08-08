package org.example.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ThreadUtils {
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class DaemonThread extends Thread {
        public DaemonThread(Runnable runnable) {
            super(runnable);
            setDaemon(true);
        }
    }

    public static void log(String message) {
        System.out.println(String.format("[%-6s %10s] %s", LocalTime.now(), Thread.currentThread().getName(), message));

    }

    public static class RunnableTask implements Runnable {

        long count;

        public RunnableTask(long count) {
            this.count = count;
        }

        @Override
        public void run() {
            log("Task start");
            sleep(count);
            log("Task end");
        }
    }
}
