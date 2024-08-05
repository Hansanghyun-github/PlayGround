package org.example.thread;

import org.assertj.core.api.Assertions;
import org.example.util.ThreadUtils;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.State.*;
import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;
import static org.example.util.ThreadUtils.sleep;

public class SynchronizedTest {
    @Test
    void Race_Condition_Test() throws Exception {
        // given
        Counter counter = new Counter();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new DaemonThread(runnable);
        Thread thread2 = new DaemonThread(runnable);

        // when
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // then
        assertThat(counter.getCount()).isLessThan(2000);
        System.out.println("counter.getCount() = " + counter.getCount());
    }

    @Test
    void Synchronized_Test() throws Exception {
        // given
        SynchronizedCounter counter = new SynchronizedCounter();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new DaemonThread(runnable);
        Thread thread2 = new DaemonThread(runnable);

        // when
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // then
        assertThat(counter.getCount()).isEqualTo(2000);
    }
    
    @Test
    void Synchronized_BlOCKED_State_Test() throws Exception {
        // given
        Runnable runnable = () -> {
            synchronized (this) {
                sleep(10000);
            }
        };
        Thread thread1 = new DaemonThread(runnable);
        Thread thread2 = new DaemonThread(runnable);

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        
        // then
        assertThat(thread2.getState()).isEqualTo(BLOCKED);
    }

    @Test
    void BLOCKED_State_Interrupt_Test() throws Exception {
        // given
        Runnable runnable = () -> {
            synchronized (this) {
                sleep(10000);
            }
        };
        Thread thread1 = new DaemonThread(runnable);
        Thread thread2 = new DaemonThread(runnable);

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        thread2.interrupt();

        // then
        assertThat(thread2.isInterrupted()).isTrue();
        assertThat(thread2.getState()).isEqualTo(BLOCKED);
    }

    private static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }


    private static class SynchronizedCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }
}
