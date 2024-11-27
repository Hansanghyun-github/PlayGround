package org.example.compare;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CompareExampleTest {
    @Test
    void 앞쪽이_크면_양수를_반환한다() throws Exception {
        // given
        Integer a = 10;
        Integer b = 5;

        // when
        int result = Integer.compare(a, b);
        int compareTo = a.compareTo(b);

        // then
        assertThat(result).isPositive();
        assertThat(compareTo).isPositive();
    }

    @Test
    void 뒷쪽이_크면_음수를_반환한다() throws Exception {
        // given
        Integer a = 5;
        Integer b = 10;

        // when
        int result = Integer.compare(a, b);
        int compareTo = a.compareTo(b);

        // then
        assertThat(result).isNegative();
        assertThat(compareTo).isNegative();
    }

    @Test
    void 같다면_0을_반환한다() throws Exception {
        // given
        Integer a = 10;
        Integer b = 10;

        // when
        int result = Integer.compare(a, b);
        int compareTo = a.compareTo(b);

        // then
        assertThat(result).isZero();
        assertThat(compareTo).isZero();
    }
}