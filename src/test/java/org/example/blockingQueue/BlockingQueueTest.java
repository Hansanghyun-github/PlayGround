package org.example.blockingQueue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.assertj.core.api.Assertions.*;

public class BlockingQueueTest {

    @Test
    void addErrorTest() throws Exception {
        // given
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        // when // then

        // add - throw exception
        assertThatThrownBy(() -> queue.add(6))
                .isInstanceOf(IllegalStateException.class);

        // offer - return default value
        assertThat(queue.offer(6)).isFalse();

        // offer with timeout - wait and return default value
        Instant startTime = Instant.now();
        assertThat(queue.offer(6, 2, java.util.concurrent.TimeUnit.SECONDS)).isFalse();
        Instant endTime = Instant.now();
        assertThat(endTime.getEpochSecond() - startTime.getEpochSecond()).isGreaterThanOrEqualTo(2);

        // no put method, because it is blocking method
        // queue.put(6);
    }

    @Test
    void removeErrorTest() throws Exception {
        // given
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // when // then

        // remove - throw exception
        assertThatThrownBy(queue::remove)
                .isInstanceOf(NoSuchElementException.class);

        // offer - return default value
        assertThat(queue.poll()).isNull();

        // offer with timeout - wait and return default value(null)
        Instant startTime = Instant.now();
        assertThat(queue.poll(2, java.util.concurrent.TimeUnit.SECONDS)).isNull();
        Instant endTime = Instant.now();
        assertThat(endTime.getEpochSecond() - startTime.getEpochSecond()).isGreaterThanOrEqualTo(2);

        // no take method, because it is blocking method
        // queue.take();
    }

    @Test
    void getErrorTest() throws Exception {
        // given
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // when // then

        // element
        assertThatThrownBy(queue::element)
                .isInstanceOf(NoSuchElementException.class);

        // peek
        assertThat(queue.peek()).isNull();
    }
}
