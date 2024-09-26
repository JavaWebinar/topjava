package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return excess;
    }

    @Override
    public boolean equals(Object otherObject) {
        // Проверка объектов на идентичность
        if (this == otherObject) {
            return true;
        }
        // Проверка явного параметра == null
        if (otherObject == null) {
            return false;
        }
        // Проверка совпадения классов
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }
        // Приведение otherObject к типу текущего класа
        UserMealWithExcess other = (UserMealWithExcess) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(dateTime, other.dateTime)
                && Objects.equals(description, other.description)
                && Objects.equals(calories, other.calories)
                && Objects.equals(excess, other.excess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories, excess);
    }

    @Override
    public String toString() {
        return String.format("User [dateTime=%s, description=%s, calories=%s, excess=%s]",
                dateTime, description, calories, excess
        );
    }
}
