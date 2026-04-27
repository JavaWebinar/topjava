package ru.javaops.topjava.app;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import ru.javaops.topjava.user.model.Role;
import ru.javaops.topjava.user.model.User;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    public boolean hasRole(Role role) {
        return user.hasRole(role);
    }

    @Override
    public String toString() {
        return "AuthUser:" + id() + '[' + user.getEmail() + ']';
    }
}