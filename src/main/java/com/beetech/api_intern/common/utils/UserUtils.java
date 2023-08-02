package com.beetech.api_intern.common.utils;

import com.beetech.api_intern.features.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
