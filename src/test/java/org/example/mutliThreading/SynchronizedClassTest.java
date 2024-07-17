package org.example.mutliThreading;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SynchronizedClassTest {

    @Spy
    InternalClass internalClass;

    @InjectMocks
    SynchronizedClass synchronizedClass;
    BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    @Test
    void sleep을_이용해_스레드_순서_설정() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        doAnswer(invocation -> {
            System.out.println("override internalMethod1");
            Thread.sleep(1000);
            return null;
        }).when(internalClass).internalMethod1();

        doAnswer(invocation -> {
            System.out.println("override internalMethod2");
            return null;
        }).when(internalClass).internalMethod2();

        // when
        Future<?> future1 = executorService.submit(() -> {
            synchronizedClass.method1();
            queue.add(1);
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronizedClass.method2();
            queue.add(2);
        });
        future1.get();
        future2.get();

        // then
        assertThat(queue.poll()).isEqualTo(1);
        assertThat(queue.poll()).isEqualTo(2);
    }

    @Test
    void CountDownLatch를_이용해_스레드_순서_설정() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            latch.countDown();
            System.out.println("override internalMethod1");
            Thread.sleep(500); // 의도적으로 0.5초 대기
            return null;
        }).when(internalClass).internalMethod1();

        doAnswer(invocation -> {
            System.out.println("override internalMethod2");
            return null;
        }).when(internalClass).internalMethod2();

        // when
        Future<?> future1 = executorService.submit(() -> {
            synchronizedClass.method1();
            queue.add(1);
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronizedClass.method2();
            queue.add(2);
        });
        future1.get();
        future2.get();

        // then
        assertThat(queue.poll()).isEqualTo(1);
        assertThat(queue.poll()).isEqualTo(2);
    }

}