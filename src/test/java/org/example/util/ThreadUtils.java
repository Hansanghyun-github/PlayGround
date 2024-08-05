package org.example.util;

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
}
