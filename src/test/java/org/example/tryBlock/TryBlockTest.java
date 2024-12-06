package org.example.tryBlock;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TryBlockTest {
    @Test
    void close_reader() throws Exception {
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // when
        br.close();

        // then
        assertThatThrownBy(br::readLine)
                .isInstanceOf(IOException.class);
    }

    @Test
    void close_with_finally() throws Exception {
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // when
        try {
            // readLine not throw exception
        } finally {
            br.close();
        }

        // then
        assertThatThrownBy(br::readLine)
                .isInstanceOf(IOException.class);
    }

    @Test
    void close_with_try_with_resource_block() throws Exception {
        // given
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // when
        try (br) {
            // readLine not throw exception
        }

        // then
        assertThatThrownBy(br::readLine)
                .isInstanceOf(IOException.class);
    }

    @Test
    void close_with_try_with_resource_block2() throws Exception {
        // when // then
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            // readLine not throw exception
        }

        // br is not accessible
        // br.readLine();
    }
}
