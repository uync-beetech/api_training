package com.beetech.api_intern.common.utils;

import com.beetech.api_intern.features.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    private UserUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<User> getAuthenticatedUser() {
        try {
            return Optional.of((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
