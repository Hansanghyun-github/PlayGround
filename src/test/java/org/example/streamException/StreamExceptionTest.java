package org.example.streamException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StreamExceptionTest {
    StreamException streamException = new StreamException();

    @Test
    void Stream_API_throw_Unchecked_Exception() throws Exception {
        // when // then
        assertThatThrownBy(() -> streamException.streamUncheckedException())
                .isInstanceOf(RuntimeException.class);
    }

}