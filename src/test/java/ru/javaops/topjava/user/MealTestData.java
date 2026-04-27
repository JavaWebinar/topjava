package ru.javaops.topjava.user;

import ru.javaops.topjava.MatcherFactory;
import ru.javaops.topjava.user.model.Meal;
import ru.javaops.topjava.user.to.MealTo;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "user");
    public static final int MEAL1_ID = 1;
    public static final int ADMIN_MEAL_ID = 8;
    public static final Meal meal1 = new Meal(MEAL1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510);
    public static final List<Meal> meals = List.of(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID, of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);
    public static MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingEqualsComparator(MealTo.class);

    public static Meal getNew() {
        return new Meal(null, of(2020, Month.FEBRUARY, 1, 18, 0), "Созданный ужин", 300);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, meal1.getDateTime().plusMinutes(2), "Обновленный завтрак", 200);
    }
}
