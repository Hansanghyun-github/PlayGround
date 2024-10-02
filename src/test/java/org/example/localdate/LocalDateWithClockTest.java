package org.example.localdate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalDateWithClockTest {
    @Test
    void defaultZone_을_파라미터로_이용하면_항상_현재_시각을_반환한다() throws Exception {
        // given
        Clock clock = Clock.systemDefaultZone();

        // when
        LocalDateTime localDateTime1 = LocalDateWithClock.getLocalDateTime(clock);
        Thread.sleep(2000);
        LocalDateTime localDateTime2 = LocalDateWithClock.getLocalDateTime(clock);

        // then
        assertThat(localDateTime1)
                .isBefore(localDateTime2);
    }
    
    @Test
    void 고정된_시각의_Clock을_이용하면_고정된_시각을_반환한다() throws Exception {
        // given
        Clock clock = Clock.fixed(
                LocalDateTime.of(2021, 1, 1, 0, 0)
                        .toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC);
        
        // when
        LocalDateTime localDateTime1 = LocalDateWithClock.getLocalDateTime(clock);
        Thread.sleep(2000);
        LocalDateTime localDateTime2 = LocalDateWithClock.getLocalDateTime(clock);

        // then
        assertThat(localDateTime1)
                .isEqualTo(localDateTime2);
    }

}