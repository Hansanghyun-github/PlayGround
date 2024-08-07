package org.example.mutliThreading.producerconsumer;

import org.example.util.ThreadUtils;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.util.ThreadUtils.*;

public class LockConditionBuffer implements Buffer{
    private final Queue<Integer> queue = new ArrayDeque<>();
    private final int max;
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition producerCond = lock.newCondition();
    private final Condition consumerCond = lock.newCondition();

    public LockConditionBuffer(int max) {
        this.max = max;
    }

    @Override
    public void produce(Integer data) {
        lock.lock();
        try{
            if (queue.size() == max) {
                log("queue is full, waiting");
                producerCond.await();
                log("awake");
            }
            queue.offer(data);
            log("signal consumer");
            consumerCond.signal();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer consume() {
        lock.lock();
        try{
            if (queue.isEmpty()) {
                log("queue is empty, waiting");
                consumerCond.await();
                log("awake");
            }
            Integer poll = queue.poll();
            log("signal producer");
            producerCond.signal();
            return poll;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
