package ru.itis.services.interfaces;

import ru.itis.models.Chat;
import ru.itis.security.jwt.details.UserDetailsImpl;

public interface ChatService {
    Chat getChat(UserDetailsImpl userDetails);

    Chat getChatByUserId(Long id);
}
