package org.example.mutliThreading.executor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

    @Nested
    class ThreadPoolManagement {
        @Test
        void before_task_service_thread_pool_is_empty() throws Exception {
            // when
            ExecutorService service = Executors.newFixedThreadPool(2);
            sleep(500);

            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
            assertThat(executor.getPoolSize()).isZero();
        }

        @Test
        void Executors_newFixedThreadPool_is_fixed_thread_size() throws Exception {
            // given
            ExecutorService service = Executors.newFixedThreadPool(2);

            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
            assertThat(executor.getCorePoolSize())
                    .isEqualTo(executor.getMaximumPoolSize());
        }
        
        @Test
        void if_task_overflow_queue_new_thread_is_created() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            RunnableTask task = new RunnableTask(2000L);
            for(int i = 0; i < 4; i++) {
                es.execute(task);
            }
            sleep(500);
            assertThat(((ThreadPoolExecutor) es).getPoolSize()).isEqualTo(2);
            
            // when
            es.execute(task);
            sleep(500);

            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            assertThat(executor.getPoolSize()).isEqualTo(3);
        }

        @Test
        void es_create_new_thread_until_max_pool_size() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            for(int i = 0; i < 4; i++) {
                es.execute(new RunnableTask(2000L, "name" + i));
            }

            // when
            sleep(500);
            es.execute(new RunnableTask(2000L, "name4"));
            es.execute(new RunnableTask(2000L, "name5"));

            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            assertThat(executor.getPoolSize()).isEqualTo(4);
        }

        @Test
        void if_queue_is_full_new_thread_proceed_recent_task() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            for(int i = 0; i < 4; i++) {
                es.execute(new RunnableTask(2000L, "name" + i));
            }

            // when
            sleep(500);
            es.execute(new RunnableTask(2000L, "name4"));

            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            BlockingQueue<Runnable> queue = executor.getQueue();
            assertThat(queue)
                    .extracting("name")
                    .doesNotContain("name4");
        }
        
        @Test
        void if_pool_and_queue_are_full_new_task_is_rejected() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            for(int i = 0; i < 6; i++) {
                es.execute(new RunnableTask(2000L, "name" + i));
            }
            sleep(500);
            
            // when // then
            assertThatThrownBy(() -> es.execute(new RunnableTask(2000L, "name6")))
                    .isInstanceOf(RejectedExecutionException.class);
        }
        
        @Test
        void if_created_additional_thread_does_not_work_then_remove() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            for(int i = 0; i < 6; i++) {
                es.execute(new RunnableTask(1000L, "name" + i));
            }
            sleep(500);
            assertThat(((ThreadPoolExecutor) es).getPoolSize()).isEqualTo(4);
            
            // when
            sleep(1000);
            
            // then
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            assertThat(executor.getPoolSize()).isEqualTo(2);
        }
        
        @Test
        void prestartAllCoreThreads_method_create_threads_if_task_is_not_entered() throws Exception {
            // given
            ExecutorService es = new ThreadPoolExecutor(2, 4,
                    100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
            
            // when
            ThreadPoolExecutor executor = (ThreadPoolExecutor) es;
            executor.prestartAllCoreThreads();
            
            // then
            assertThat(executor.getPoolSize()).isEqualTo(2);
        }
    }

}
