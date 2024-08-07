package org.example.mutliThreading.producerconsumer;

import java.util.ArrayDeque;
import java.util.Queue;

public class SynchronizedBuffer implements Buffer{
        private final Queue<Integer> queue = new ArrayDeque<>();
        private final int max;
        public SynchronizedBuffer(int max) {
            this.max = max;
        }

        public synchronized void produce(Integer data) {
            if (queue.size() == max) {
                System.out.println("queue is full");
                return;
            }
            queue.offer(data);
        }

        public synchronized Integer consume() {
            if (queue.isEmpty()) {
                return null;
            }
            return queue.poll();
        }
        @Override
        public String toString() {
            return queue.toString();
        }
}
