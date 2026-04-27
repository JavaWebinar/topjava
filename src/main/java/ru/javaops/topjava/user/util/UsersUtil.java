package ru.javaops.topjava.user.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.user.model.Role;
import ru.javaops.topjava.user.model.User;
import ru.javaops.topjava.user.to.UserTo;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}