package org.example.util;

import lombok.Getter;

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

    @Getter
    public static class RunnableTask implements Runnable {

        long count;
        String name = "";

        public RunnableTask(long count) {
            this.count = count;
        }

        public RunnableTask(long count, String name) {
            this.count = count;
            this.name = name;
        }

        @Override
        public void run() {
            log(name + " task start");
            sleep(count);
            log(name + " task end");
        }
    }
}
