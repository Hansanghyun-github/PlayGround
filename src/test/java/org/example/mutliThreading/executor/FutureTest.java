package org.example.mutliThreading.executor;

import org.example.util.ExecutorUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.example.util.ThreadUtils.*;

public class FutureTest {
    @Test
    void Future() throws Exception {
        // given
        ExecutorService service = Executors.newFixedThreadPool(2);
        Callable<Integer> task = () -> {
            sleep(1000);
            return new Random().nextInt();
        };
        Future<Integer> submit1 = service.submit(task);
        Future<Integer> submit2 = service.submit(task);

        // when
        Integer i1 = submit1.get();
        Integer i2 = submit2.get();

        // then
        assertThat(submit1.isDone()).isTrue();
        assertThat(submit2.isDone()).isTrue();
        log("i1=" + i1 + ", i2=" + i2);
        ExecutorUtils.printState(service);
    }

    @Test
    void cancel() throws Exception {
        // given
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> submit = service.submit(() -> {
            sleep(1000);
            return new Random().nextInt();
        });

        // when
        sleep(500);
        submit.cancel(true);
        //submit.cancel(false);

        // then
        // just true if future is called with cancel
        assertThat(submit.isCancelled()).isTrue();
        assertThat(submit.isDone()).isTrue();
        assertThatThrownBy(submit::get)
                .isInstanceOf(CancellationException.class);
        ExecutorUtils.printState(service);
    }

    @Test
    void get_receive_exception() throws Exception {
        // given
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> submit = service.submit(() -> {
            throw new RuntimeException("error");
        });

        // when // then
        assertThatThrownBy(submit::get)
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("error");
        ExecutorUtils.printState(service);
    }

    @Test
    void ExecutorService_Monitoring() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Callable<Integer> task = () -> {
            sleep(1000);
            return new Random().nextInt();
        };

        ExecutorUtils.printState(service);
        Future<Integer> submit1 = service.submit(task);
        Future<Integer> submit2 = service.submit(task);
        Future<Integer> submit3 = service.submit(task);
        Future<Integer> submit4 = service.submit(task);
        sleep(500);
        ExecutorUtils.printState(service);
        sleep(1000);
        ExecutorUtils.printState(service);
        Integer i1 = submit1.get();
        Integer i2 = submit2.get();
        Integer i3 = submit3.get();
        Integer i4 = submit4.get();
        log("i1=" + i1 + ", i2=" + i2 + ", i3=" + i3 + ", i4=" + i4);
        ExecutorUtils.printState(service);
    }

}
