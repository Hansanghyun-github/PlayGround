package org.example.mutliThreading.producerconsumer;

public interface Buffer {
    void produce(Integer data);
    Integer consume();
}
