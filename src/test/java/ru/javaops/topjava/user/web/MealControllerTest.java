package ru.javaops.topjava.user.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.AbstractControllerTest;
import ru.javaops.topjava.common.util.JsonUtil;
import ru.javaops.topjava.user.UserTestData;
import ru.javaops.topjava.user.model.Meal;
import ru.javaops.topjava.user.repository.MealRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.user.MealTestData.*;
import static ru.javaops.topjava.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava.user.util.MealsUtil.createTo;
import static ru.javaops.topjava.user.util.MealsUtil.getTos;
import static ru.javaops.topjava.user.web.MealController.REST_URL;

class MealControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MealRepository mealRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MEAL1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_MEAL_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + MEAL1_ID))
                .andExpect(status().isNoContent());
        assertFalse(mealRepository.get(UserTestData.USER_ID, MEAL1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + ADMIN_MEAL_ID))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealRepository.getExisted(MEAL1_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Meal created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealRepository.getExisted(newId), newMeal);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(meals, UserTestData.user.getCaloriesPerDay())));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "filter")
                .param("startDate", "2020-01-30").param("startTime", "07:00")
                .param("endDate", "2020-01-31").param("endTime", "11:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(createTo(meal5, true), createTo(meal1, false)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getBetweenAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(meals, UserTestData.user.getCaloriesPerDay())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Meal invalid = new Meal(null, null, "Dummy", 200);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        Meal invalid = new Meal(MEAL1_ID, null, null, 6000);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Meal invalid = new Meal(MEAL1_ID, LocalDateTime.now(), "<script>alert(123)</script>", 200);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = USER_MAIL)
    void updateDuplicate() throws Exception {
        Meal invalid = new Meal(MEAL1_ID, meal2.getDateTime(), "Dummy", 200);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Meal invalid = new Meal(null, adminMeal1.getDateTime(), "Dummy", 200);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}