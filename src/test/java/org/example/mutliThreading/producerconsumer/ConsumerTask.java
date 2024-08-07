package org.example.mutliThreading.producerconsumer;

import org.example.util.ThreadUtils;

import static org.example.util.ThreadUtils.*;

public class ConsumerTask implements Runnable {
    private Buffer buffer;

    public ConsumerTask(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        log("consume: " + buffer);
        Integer i = buffer.consume();
        log("consumed done: " + i + " " + buffer);
    }
}