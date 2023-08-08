package com.beetech.api_intern.features.email;

public interface MailService {
    void sendEmail(String to, String subject, String body);
}
