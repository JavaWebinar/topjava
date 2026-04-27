package ru.javaops.topjava.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.AbstractControllerTest;
import ru.javaops.topjava.user.model.Role;
import ru.javaops.topjava.user.model.User;
import ru.javaops.topjava.user.repository.UserRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.user.UserTestData.*;
import static ru.javaops.topjava.user.web.AdminUserController.REST_URL;
import static ru.javaops.topjava.user.web.UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL;

class AdminUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-email?email=" + admin.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enableNotFound() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + NOT_FOUND)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        User updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(repository.getExisted(USER_ID), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        User newUser = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, "newPass")))
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin, guest, user));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + USER_ID)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(repository.getExisted(USER_ID).isEnabled());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithMeals() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_ID + "/with-meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_WITH_MEALS_MATCHER.contentJson(admin));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        User invalid = new User(null, null, "", "newPass", 7300, Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(invalid, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        User invalid = new User(user);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(invalid, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        User updated = new User(user);
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        User updated = new User(user);
        updated.setEmail(ADMIN_MAIL);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        User expected = new User(null, "New", USER_MAIL, "newPass", 2300, Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableContent())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }
}