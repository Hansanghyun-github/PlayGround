package org.example.countDownLatch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SyncThreadsTest {

    @Test
    void testMultiThreads() throws Exception {
        // given
        CountDownLatch latch1 = new CountDownLatch(100);
        CountDownLatch latch2 = new CountDownLatch(100);
        CountDownLatch latch3 = new CountDownLatch(100);

        // when
        for (int i = 0; i < 100; i++) {
            new SyncWork(latch1, latch2, latch3).start();
        }

        try {
            latch3.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // then
        System.out.println("check map");

        // check all key is map1 < map2 < map3

        List<LocalTime> list1 = SyncWork.map1.entrySet().stream().map(e -> e.getValue()).toList();
        List<LocalTime> list2 = SyncWork.map2.entrySet().stream().map(e -> e.getValue()).toList();
        List<LocalTime> list3 = SyncWork.map3.entrySet().stream().map(e -> e.getValue()).toList();

        // get list's boundary value
        LocalTime max1 = list1.stream().max(LocalTime::compareTo).get();
        LocalTime min2 = list2.stream().min(LocalTime::compareTo).get();
        LocalTime max2 = list2.stream().max(LocalTime::compareTo).get();
        LocalTime min3 = list3.stream().min(LocalTime::compareTo).get();

        assertThat(max1).isBefore(min2);
        assertThat(max2).isBefore(min3);

        // check map1 element == map2 element == map3 element
        Set<Long> keySet1 = SyncWork.map1.keySet();
        Set<Long> keySet2 = SyncWork.map2.keySet();
        Set<Long> keySet3 = SyncWork.map3.keySet();
        
        assertThat(keySet1).hasSize(100);
        assertThat(keySet2).hasSize(100);
        assertThat(keySet3).hasSize(100);

        assertThat(keySet1).containsExactlyInAnyOrderElementsOf(keySet2);
        assertThat(keySet1).containsExactlyInAnyOrderElementsOf(keySet3);
        assertThat(keySet2).containsExactlyInAnyOrderElementsOf(keySet3);

        // print localTime - max1, min2, max2, min3
        System.out.println("max1: " + max1);
        System.out.println("min2: " + min2);
        System.out.println("max2: " + max2);
        System.out.println("min3: " + min3);

    }

}