package org.example.mutliThreading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SynchronizedClassTest {

    @Spy
    SynchronizedClass synchronizedClass;
    @Spy
    NormalClass normalClass;
    CountDownLatch latch = new CountDownLatch(2);
    BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();

    @Test
    void synchronized_키워드가_붙으면_메서드의_순서를_보장한다() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        doAnswer(invocation -> {
            synchronized (this){
                Thread.sleep(3000);
                queue.put(1);
                latch.countDown();
            }
            return null;
        }).when(synchronizedClass).method1();

        doAnswer(invocation -> {
            Thread.sleep(1000);
            synchronized (this){
                queue.put(2);
                latch.countDown();
            }
            return null;
        }).when(synchronizedClass).method2();

        // when
        executorService.submit(() -> synchronizedClass.method1());
        executorService.submit(() -> synchronizedClass.method2());
        latch.await();

        // then
        assertThat(queue.poll()).isEqualTo(1);
        assertThat(queue.poll()).isEqualTo(2);

        // verify call method1 -> method2 order

    }

    // 멀티스레딩 환경에서, 특정 스레드가 무조건 먼저 실행됨을 어떻게 보장할 수 있을까

}