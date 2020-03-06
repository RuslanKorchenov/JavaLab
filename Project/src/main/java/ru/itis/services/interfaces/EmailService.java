package ru.itis.services.interfaces;

public interface EmailService {
    void sendMail(String subject, String text, String email);
}
