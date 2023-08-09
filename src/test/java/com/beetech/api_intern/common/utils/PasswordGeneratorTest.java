package com.beetech.api_intern.common.utils;

import org.junit.jupiter.api.Test;


class PasswordGeneratorTest {
    @Test
    void testPasswordGenerator() {
        System.out.println(RandomStringGenerator.getRandomString(15));
    }

}