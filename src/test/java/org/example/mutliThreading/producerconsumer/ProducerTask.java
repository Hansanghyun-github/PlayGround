package org.example.mutliThreading.producerconsumer;

import org.example.util.ThreadUtils;

import static org.example.util.ThreadUtils.*;

public class ProducerTask implements Runnable {
    private Buffer buffer;
    private Integer value;

    public ProducerTask(Buffer buffer, Integer value) {
        this.buffer = buffer;
        this.value = value;
    }

    @Override
    public void run() {
        log("produce: " + buffer);
        buffer.produce(value);
        log("produced done: " + buffer);
    }
}
