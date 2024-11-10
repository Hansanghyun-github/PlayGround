package org.example.simple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CompareInteger {
    @Test
    @DisplayName("-128 ~ 127의 비교는 ==로 가능하다")
    void test_integer_compare() throws Exception {
        // when // then
        for(int i=-128;i<=127;i++){
            Integer i1 = i;
            Integer i2 = i;
            assertThat(i1 == i2).isTrue();
        }
    }

    @Test
    @DisplayName("-129보다 작은 수들과 128보다 큰 수들은 ==로 비교할 수 없다")
    void test_integer_compare2() throws Exception {
        // when // then
        Integer i1 = -129;
        Integer i2 = -129;
        assertThat(i1 == i2).isFalse();
        i1 = 128;
        i2 = 128;
        assertThat(i1 == i2).isFalse();
    }

    @Test
    @DisplayName("equals로 비교하면 모든 수를 비교할 수 있다")
    void test_integer_compare3() throws Exception {
        // when // then
        for(int i=-129;i<=128;i++){
            Integer i1 = i;
            Integer i2 = i;
            assertThat(i1.equals(i2)).isTrue();
        }
    }
}
