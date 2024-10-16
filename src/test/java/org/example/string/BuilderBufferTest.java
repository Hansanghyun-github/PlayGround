package org.example.string;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BuilderBufferTest {
    
    @Test
    void StringBuilder_Race_Condition() throws Exception {
        // given
        StringBuilder sb = new StringBuilder();
        ExecutorService service = Executors.newFixedThreadPool(2);

        // when
        List<Future<?>> futures = new ArrayList<>();
        for(int i=0;i<1000;i++){
            Future<?> submit = service.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    sb.append("a");
                }
            });
            futures.add(submit);
        }
        for(Future<?> future : futures){
            future.get();
        }

        // then
        assertThat(sb.length()).isLessThan(10000);
        System.out.println(sb.length());
    }

    @Test
    void StringBuffer_Not_Race_Condition() throws Exception {
        // given
        StringBuffer sb = new StringBuffer();
        ExecutorService service = Executors.newFixedThreadPool(2);

        // when
        List<Future<?>> futures = new ArrayList<>();
        for(int i=0;i<1000;i++){
            Future<?> submit = service.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    sb.append("a");
                }
            });
            futures.add(submit);
        }
        for(Future<?> future : futures){
            future.get();
        }

        // then
        assertThat(sb.length()).isEqualTo(10000);
    }

}