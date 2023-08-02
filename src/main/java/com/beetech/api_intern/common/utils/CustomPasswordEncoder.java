package com.beetech.api_intern.common.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The type Custom password encoder.
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword.toString(), BCrypt.gensalt(8));
    }

    @Override
    public boolean matches(CharSequence plainTextPassword, String passwordInDatabase) {
        return BCrypt.checkpw(plainTextPassword.toString(), passwordInDatabase);
    }

}
