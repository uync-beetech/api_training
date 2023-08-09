package com.beetech.api_intern.common.utils;

import org.apache.commons.text.CharacterPredicates;

import java.util.Random;

public class RandomStringGenerator {
    private RandomStringGenerator() {
        throw new IllegalStateException("Utility class");
    }

    private static final Random random = new Random();

    public static String getRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        randomString.append((char) (random.nextInt(10) + 48));
        randomString.append((char) (random.nextInt(26) + 65));
        randomString.append((char) (random.nextInt(26) + 97));
        org.apache.commons.text.RandomStringGenerator generator = new org.apache.commons.text.RandomStringGenerator.Builder()
                .withinRange('0', 'z') // Tạo ký tự từ '0' đến 'z'
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS) // Bao gồm chữ cái và số
                .build();
        randomString.append(generator.generate(length - 3));
        return randomString.toString();
    }

    public static String generatePassword() {
        return getRandomString(32);
    }
}
