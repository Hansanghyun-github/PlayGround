package org.example.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorUtils {
    public static void printState(ExecutorService service){
        if(service instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            ThreadUtils.log("[pool=" + pool + ", active=" + active + ", queuedTasks=" +
                    queuedTasks + ", completedTasks=" + completedTask + "]");
        }
        else {
            ThreadUtils.log(service.toString());
        }
    }
}
