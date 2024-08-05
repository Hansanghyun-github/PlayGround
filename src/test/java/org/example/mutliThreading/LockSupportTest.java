package org.example.mutliThreading;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.State.*;
import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.sleep;

public class LockSupportTest {
    @Test
    void Park_Make_WAITING_State() throws Exception {
        // given
        Runnable runnable = LockSupport::park;
        Thread thread = new Thread(runnable);

        // when
        thread.start();
        sleep(1000);
        
        // then
        assertThat(thread.getState()).isEqualTo(WAITING);
    }
    
    @Test
    void ParkNanos_Test() throws Exception {
        // given
        Runnable runnable = () -> {
            LockSupport.parkNanos(2_000_000_000);
        };
        Thread thread = new Thread(runnable);



        // when // then
        thread.start();
        sleep(1000);
        assertThat(thread.getState()).isEqualTo(TIMED_WAITING);
        sleep(2000);
        assertThat(thread.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void Unpark_Test() throws Exception {
        // given
        Runnable runnable = LockSupport::park;
        Thread thread = new Thread(runnable);

        // when
        thread.start();
        sleep(1000);
        LockSupport.unpark(thread);
        sleep(500);

        // then
        assertThat(thread.getState()).isEqualTo(TERMINATED);
    }

    @Test
    void Park_Interrupt_Test() throws Exception {
        // given
        Runnable runnable = () -> {
            LockSupport.park();
        };
        Thread thread = new Thread(runnable);

        // when
        thread.start();
        sleep(1000);
        thread.interrupt();
        sleep(1000);

        // then
        assertThat(thread.getState()).isEqualTo(TERMINATED);
        assertThat(thread.isInterrupted()).isTrue();
    }

    
}
