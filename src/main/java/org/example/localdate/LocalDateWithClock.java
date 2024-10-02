package org.example.localdate;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateWithClock {
    public static LocalDateTime getLocalDateTime(Clock clock) {
        return LocalDateTime.now(clock);
    }
}
