package ru.javaops.topjava.user.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.topjava.common.to.BaseTo;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class MealTo extends BaseTo {

    LocalDateTime dateTime;

    String description;

    int calories;

    boolean excess;

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
}
