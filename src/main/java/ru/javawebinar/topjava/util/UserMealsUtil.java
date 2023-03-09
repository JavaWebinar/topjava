package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/*

UserMealsUtil с методои main  -создает список на клиенте котор пеедает методу getFilteredMealsExceeded
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 31, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 31, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2023, Month.APRIL, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredMealsExceeded = getFilteredMealsExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredMealsExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredMealsExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //сумма калорий по дням (сгруппировать по датам)
        //группирвка по датам по ключу. каждой дате будет соответствовать лист из записей которіе относяться к етой дате.
        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        //получение результата
        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
//преобразовать каждій елемент коллекции в чтото другое или получить новую коллекцию-стандартная операция map
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), caloriesSumByDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate mealData = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.put(mealData, caloriesSumPerDate.getOrDefault(mealData,0) + meal.getCalories());
        }

        List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new UserMealWithExceed(dateTime,meal.getDescription(),meal.getCalories(),
                        caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return mealExceeded;
    }

}


