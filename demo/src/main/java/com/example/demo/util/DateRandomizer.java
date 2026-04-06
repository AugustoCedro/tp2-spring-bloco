package com.example.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;

public class DateRandomizer {

    public static LocalDateTime randomDateTime() {

        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 2, 28, 23, 59, 59);

        long startEpoch = start.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = end.atZone(ZoneId.systemDefault()).toEpochSecond();

        long randomEpoch = ThreadLocalRandom.current()
                .nextLong(startEpoch, endEpoch + 1);

        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneId.systemDefault().getRules().getOffset(start.atZone(ZoneId.systemDefault()).toInstant()));
    }
}