package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(19, 0), LocalTime.of(23, 0), 2010);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> result = new ArrayList<UserMealWithExcess>();
        Map<LocalDate, Integer> usersExceesDay = new HashMap<LocalDate, Integer>() {
            @Override
            public Set<Entry<LocalDate, Integer>> entrySet() {
                return null;
            }
        };
        for (UserMeal meals2 : meals) {

            LocalDate userLocalDate = meals2.getDateTime().toLocalDate();
            Integer userExceesColDay = usersExceesDay.getOrDefault(userLocalDate, 0) + meals2.getCalories();

            usersExceesDay.put(userLocalDate, userExceesColDay);

            if (meals2.getDateTime().toLocalTime().isAfter(startTime) && meals2.getDateTime().toLocalTime().isBefore(endTime)) {

                boolean userExceesDay = (caloriesPerDay > usersExceesDay.getOrDefault(userLocalDate, 0));

                result.add(new UserMealWithExcess(meals2.getDateTime(), meals2.getDescription(), meals2.getCalories(), userExceesDay));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
