package com.schemaorg4j.domain.datatypes;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Time {

    private int hour;
    private int minute;
    private int second;
    private boolean isUTC;
    private Integer hourAdjustment;
    private Integer minuteAdjustment;

    public Time(int hour) {
        this.hour = hour;
    }

    public Time(int hour, int minute) {
        this(hour);
        this.minute = minute;
    }

    public Time(int hour, int minute, int second) {
        this(hour, minute);
        this.second = second;
    }

    public Time(int hour, int minute, int second, boolean isUTC, Integer hourAdjustment) {
        this(hour, minute, second);
        this.isUTC = isUTC;
        this.hourAdjustment = hourAdjustment;
    }

    public Time(int hour, int minute, int second, boolean isUTC, Integer hourAdjustment, Integer minuteAdjustment) {
        this(hour, minute, second, isUTC, hourAdjustment);
        this.minuteAdjustment = minuteAdjustment;
    }


    public static Time parse(String time) {
        Scanner scanner = new Scanner(time);

        int hour = integerPair(scanner);

        if (!scanner.hasNext()) {
            return new Time(hour);
        }

        skipOneCharacter(scanner);

        int minute = integerPair(scanner);

        if (!scanner.hasNext()) {
            return new Time(hour, minute);
        }

        skipOneCharacter(scanner);

        int second = integerPair(scanner);

        if (!scanner.hasNext()) {
            return new Time(hour, minute, second);
        }

        boolean isUTC = readIsUTC(scanner);
        int sign = readSign(scanner);
        int adjustmentHour = integerPair(scanner);

        if (!scanner.hasNext()) {
            return new Time(hour, minute, second, isUTC, sign * adjustmentHour);
        }

        skipOneCharacter(scanner);

        int adjustmentMinute = integerPair(scanner);

        return new Time(hour, minute, second, isUTC, sign * adjustmentHour, sign * adjustmentMinute);
    }

    private static int readSign(Scanner scanner) {
        return Objects.equals(scanner.next(Pattern.compile("[+-]")), "+") ? 1 : -1;
    }

    private static boolean readIsUTC(Scanner scanner) {
        return Objects.equals(scanner.next("Z"), "Z");
    }

    private static void skipOneCharacter(Scanner scanner) {
        scanner.nextByte();
    }

    private static int integerPair(Scanner scanner) {
        return scanner.nextInt() * 10 + scanner.nextInt();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public boolean isUTC() {
        return isUTC;
    }

    public Integer getHourAdjustment() {
        return hourAdjustment;
    }

    public Integer getMinuteAdjustment() {
        return minuteAdjustment;
    }
}
