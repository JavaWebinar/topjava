package ru.javaops.topjava.common.util;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

@UtilityClass
public class Util {
    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }
}
