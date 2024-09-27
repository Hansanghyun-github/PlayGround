package org.example.streamException;

import java.util.List;

public class StreamException {
    public void streamCheckedException() {
        List.of(1, 2, 3, 4, 5)
                .stream()
                .forEach(i -> {
                    //throw new Exception("Checked Exception");
                });
        // stream API does not allow checked exceptions
    }

    public void streamUncheckedException() {
        List.of(1, 2, 3, 4, 5)
                .stream()
                .forEach(i -> {
                    throw new RuntimeException("Unchecked Exception");
                });
    }
}
