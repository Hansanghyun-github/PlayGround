package org.example.thread;


import org.assertj.core.api.Assertions;
import org.example.util.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.State.*;
import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;

class ThreadTest {

    // todo Thread.sleep -> Thread Synchronization

    @Test
    void NEW_state_test() throws Exception {
        // when
        Thread thread = new Thread(() -> {});
        thread.setDaemon(true);

        // then
        assertThat(thread.getState()).isEqualTo(NEW);
    }

    @Test
    void RUNNABLE_state_test() throws Exception {
        // when // then
        assertThat(Thread.currentThread().getState())
                .isEqualTo(RUNNABLE);
    }
    
    @Test
    void TIMED_WAITING_state_test() throws Exception {
        // given
        Thread thread = new Thread(() -> {
            sleep(2000);
        });
        thread.setDaemon(true);
        
        // when
        thread.start();
        sleep(300);
        
        // then
        assertThat(thread.getState()).isEqualTo(TIMED_WAITING);
    }

    @Test
    void TERMINATED_state_test() throws Exception {
        // given
        Thread thread = new Thread(() -> {
            sleep(2000);
        });
        thread.setDaemon(true);

        // when
        thread.start();
        thread.join();

        // then
        assertThat(thread.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void WAITING_state_test() throws Exception {
        // given
        Thread thread = new Thread(() -> {
            sleep(2000);
        });
        Thread waitingThread = new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(true);
        waitingThread.setDaemon(true);

        // when
        thread.start();
        waitingThread.start();
        sleep(1000);

        // then
        assertThat(waitingThread.getState()).isEqualTo(WAITING);
    }

    @Test
    void BLOCKED_state_test() throws Exception {
        // given
        Object lock = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock){
                sleep(1000);
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock){
            }
        });
        thread1.setDaemon(true);
        thread2.setDaemon(true);

        // when
        thread1.start();
        thread2.start();
        sleep(500);

        // then
        assertThat(thread2.getState()).isEqualTo(BLOCKED);
    }

    // todo 왜 lock.lock()은 WAITING 인거지?

}