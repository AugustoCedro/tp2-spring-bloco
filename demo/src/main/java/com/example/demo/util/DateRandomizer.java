package com.example.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class DateRandomizer {

    public static LocalDate randomDate() {

        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);

        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();

        long randomDay = ThreadLocalRandom.current()
                .nextLong(startEpochDay, endEpochDay + 1);

        return LocalDate.ofEpochDay(randomDay);
    }
}