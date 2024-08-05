package org.example.thread;

import org.assertj.core.api.Assertions;
import org.example.util.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.State.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;

public class ReentrantLockTest {
    @Test
    void lock() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.lock();
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        
        // then
        assertThat(thread2.getState()).isEqualTo(WAITING);
    }

    @Test
    void lock_not_response_interrupt() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.lock();
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        thread2.interrupt();

        // then
        assertThat(thread2.isInterrupted()).isTrue();
        assertThat(thread2.getState()).isEqualTo(WAITING);
    }

    @Test
    void lockInterruptibly_response_interrupt() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        thread2.interrupt();
        sleep(500);

        // then
        assertThat(thread2.isInterrupted()).isFalse();
        assertThat(thread2.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void tryLock() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.tryLock();
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1000);

        // then
        assertThat(thread2.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void tryLock_with_time() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            try {
                lock.tryLock(2, SECONDS);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        });

        // when // then
        thread1.start();
        thread2.start();
        sleep(1000);
        assertThat(thread2.getState()).isEqualTo(TIMED_WAITING);
        sleep(2000);
        assertThat(thread2.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void tryLock_with_time_response_interrupt() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(10000);
        });
        Thread thread2 = new DaemonThread(() -> {
            try {
                lock.tryLock(2, SECONDS);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1000);
        thread2.interrupt();
        sleep(500);

        // then
        assertThat(thread2.isInterrupted()).isFalse();
        assertThat(thread2.getState()).isEqualTo(TERMINATED);
    }
    @Test
    void unlock() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(1000);
            lock.unlock();
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.lock();
        });

        // when
        thread1.start();
        thread2.start();
        sleep(1500);

        // then
        assertThat(thread2.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void occur_IllegalMonitorStateException_when_not_lock_thread_call_unlock() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(1000);
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.lock();
        });

        // when
        thread1.start();
        thread2.start();
        sleep(500);


        // then
        assertThatThrownBy(lock::unlock)
                .isInstanceOf(IllegalMonitorStateException.class);
    }

    @Test
    void ReentrantLock_Fair_Mode() throws Exception {
        // given
        ReentrantLock lock = new ReentrantLock(true);
        Thread thread1 = new DaemonThread(() -> {
            lock.lock();
            sleep(1000);
            lock.unlock();
        });
        Thread thread2 = new DaemonThread(() -> {
            lock.lock();
            sleep(1000);
        });
        Thread thread3 = new DaemonThread(lock::lock);

        // when
        thread1.start();
        thread2.start();
        thread3.start();
        sleep(1500);

        // then
        assertThat(thread2.getState()).isEqualTo(TIMED_WAITING); // because of sleep(1000)
        assertThat(thread3.getState()).isEqualTo(WAITING);

    }
    
}
