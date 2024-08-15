package org.example.mutliThreading;

import org.example.mutliThreading.producerconsumer.*;
import org.junit.jupiter.api.Test;

import static org.example.util.ThreadUtils.*;

public class ProducerConsumerTest {
    @Test
    void ProducerFirst() throws Exception {
        // given
        Buffer buffer = new SynchronizedBuffer(2);
        Thread producer1 = new DaemonThread(new ProducerTask(buffer, 1));
        Thread producer2 = new DaemonThread(new ProducerTask(buffer, 2));
        Thread producer3 = new DaemonThread(new ProducerTask(buffer, 3));
        Thread consumer1 = new DaemonThread(new ConsumerTask(buffer));
        Thread consumer2 = new DaemonThread(new ConsumerTask(buffer));
        Thread consumer3 = new DaemonThread(new ConsumerTask(buffer));

        // when
        producer1.start();
        sleep(500);
        producer2.start();
        sleep(500);
        producer3.start();
        sleep(500);
        consumer1.start();
        sleep(500);
        consumer2.start();
        sleep(500);
        consumer3.start();
        sleep(500);
    }

    @Test
    void ConsumerFirst() throws Exception {
        // given
        Buffer buffer = new SynchronizedBuffer(2);
        Thread producer1 = new DaemonThread(new ProducerTask(buffer, 1));
        Thread producer2 = new DaemonThread(new ProducerTask(buffer, 2));
        Thread producer3 = new DaemonThread(new ProducerTask(buffer, 3));
        Thread consumer1 = new DaemonThread(new ConsumerTask(buffer));
        Thread consumer2 = new DaemonThread(new ConsumerTask(buffer));
        Thread consumer3 = new DaemonThread(new ConsumerTask(buffer));

        // when
        consumer1.start();
        sleep(500);
        consumer2.start();
        sleep(500);
        consumer3.start();
        sleep(500);
        producer1.start();
        sleep(500);
        producer2.start();
        sleep(500);
        producer3.start();
        sleep(500);
    }

}
