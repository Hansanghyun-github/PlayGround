package org.example.mutliThreading;

import org.junit.jupiter.api.Test;

import static java.lang.Thread.State.*;
import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;

public class WaitNotifyTest {
    @Test
    void wait_have_to_call_when_object_is_monitor_owner() throws Exception {
        // given

        // when // then
        assertThatThrownBy(this::wait)
                .isInstanceOf(IllegalMonitorStateException.class);
    }
    
    @Test
    void wait_make_WAITING_state() throws Exception {
        // given
        Runnable runnable = () -> {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        };
        Thread thread = new DaemonThread(runnable);
        
        // when
        thread.start();
        sleep(1000);
        
        // then
        assertThat(thread.getState()).isEqualTo(WAITING);
    }
    
    @Test
    void wait_response_interrupt() throws Exception {
        // given
        Runnable runnable = () -> {
            synchronized (this) {
                try {
                    wait(); // equal this.wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        };
        Thread thread = new DaemonThread(runnable);

        // when
        thread.start();
        sleep(1000);
        thread.interrupt();
        sleep(1000);
        
        // then
        assertThat(thread.isInterrupted()).isFalse();
        assertThat(thread.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void notify_awake_waiting_thread() throws Exception {
        // given;
        Thread waitingThread = new DaemonThread(() -> {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        });
        Thread notifyThread = new DaemonThread(() -> {
            synchronized (this) {
                notify();
            }
        });

        // when
        waitingThread.start();
        sleep(1000);
        notifyThread.start();
        sleep(1000);

        // then
        assertThat(waitingThread.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void notify_make_waiting_thread_to_blocked() throws Exception {
        // given
        Thread waitingThread = new DaemonThread(() -> {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        });
        Thread notifyThread = new DaemonThread(() -> {
            synchronized (this) {
                notify();
                sleep(1000);
            }
        });

        // when
        waitingThread.start();
        sleep(1000);
        notifyThread.start();
        sleep(500);

        // then
        assertThat(waitingThread.getState()).isEqualTo(BLOCKED);
    }

}
