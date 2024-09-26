package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserMeal {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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
        UserMeal other = (UserMeal) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(dateTime, other.dateTime)
                && Objects.equals(description, other.description)
                && Objects.equals(calories, other.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories);
    }

    @Override
    public String toString() {
        return String.format("User [dateTime=%s, description=%s, calories=%s]",
                dateTime, description, calories
        );
    }

}
