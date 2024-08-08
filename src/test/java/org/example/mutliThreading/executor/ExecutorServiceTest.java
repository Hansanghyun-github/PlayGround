package org.example.mutliThreading.executor;

import org.assertj.core.api.Assertions;
import org.example.util.ExecutorUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.security.PrivilegedAction;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;

public class ExecutorServiceTest {

    @Nested
    class shutdown {
        @Test
        void shutdown_wait_rest_task() throws Exception {
            // given
            ExecutorService es = Executors.newFixedThreadPool(2);
            RunnableTask shortTask = new RunnableTask(500L);
            RunnableTask longTask = new RunnableTask(2000L);
            es.execute(shortTask);
            es.execute(longTask);

            // when
            sleep(1000);
            es.shutdown();

            // then
            assertThat(es.awaitTermination(10, TimeUnit.SECONDS))
                    .isTrue();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            assertThat(executor.getCompletedTaskCount()).isEqualTo(2);
        }

        @Test
        void shutdown_proceed_rest_task() throws Exception {
            // given
            ExecutorService es = Executors.newFixedThreadPool(1);
            RunnableTask task = new RunnableTask(1000L);
            es.execute(task);
            es.execute(task);

            // when
            sleep(500);
            es.shutdown();

            // then
            assertThat(es.awaitTermination(10, TimeUnit.SECONDS))
                    .isTrue();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            assertThat(executor.getCompletedTaskCount()).isEqualTo(2);
        }

        @Test
        void shutdown_reject_new_task() throws Exception {
            // given
            ExecutorService es = Executors.newSingleThreadExecutor();
            RunnableTask task = new RunnableTask(1000L);

            // when
            es.shutdown();

            // then
            assertThatThrownBy(() -> es.execute(task))
                    .isInstanceOf(RejectedExecutionException.class);
        }
        
        @Test
        void awaitTermination_return_false_if_task_is_not_completed() throws Exception {
            // given
            ExecutorService es = Executors.newSingleThreadExecutor();
            RunnableTask task = new RunnableTask(10_000L);
            es.execute(task);
            
            // when
            es.shutdown();
            
            // then
            assertThat(es.awaitTermination(2, TimeUnit.SECONDS))
                    .isFalse();
        }
        
        @Test
        void shutdownNow_stop_current_task() throws Exception {
            // given
            ExecutorService es = Executors.newSingleThreadExecutor();
            Callable<Integer> task = () -> {
                Thread.sleep(10_000L);
                return 0;
            };
            Future<Integer> submit = es.submit(task);

            // when
            es.shutdownNow();
            
            // then
            assertThatThrownBy(submit::get)
                    .isInstanceOf(ExecutionException.class);
        }
        
        @Test
        void shutdownNow_may_not_stop_task() throws Exception {
            // given
            AtomicBoolean flag = new AtomicBoolean(true);
            ExecutorService es = Executors.newSingleThreadExecutor();
            Callable<Integer> task = () -> {
                // busy waiting, can not receive interrupt signal
                while(flag.get()) { /* do something */ }
                return 0;
            };
            es.submit(task);
            
            // when
            sleep(500);
            es.shutdownNow();
            
            // then
            assertThat(es.awaitTermination(1, TimeUnit.SECONDS))
                    .isFalse();
            flag.set(false);
        }

        @Test
        void make_graceful_shutdown() throws Exception { // below code is example
            // given
            int waitRemainTasksTime = 1;
            int waitForceShutdownTime = 1;
            ExecutorService es = Executors.newSingleThreadExecutor();

            // when
            es.execute(new RunnableTask(1000L));

            es.shutdown();
            try {
                log("awaitTermination start");
                if (!es.awaitTermination(waitRemainTasksTime, TimeUnit.SECONDS)) {
                    log("shutdownNow start");
                    es.shutdownNow();
                    if (!es.awaitTermination(waitForceShutdownTime, TimeUnit.SECONDS)) {
                        log("shutdownNow failed");
                    }
                }
            } catch (InterruptedException e) {
                // if current thread is interrupted, shutdownNow
                es.shutdownNow();
            }
        }
    }

}
