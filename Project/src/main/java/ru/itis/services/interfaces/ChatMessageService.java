package ru.itis.services.interfaces;

import ru.itis.dto.ChatMessageDto;
import ru.itis.models.Chat;
import ru.itis.models.ChatMessage;
import ru.itis.models.User;
import ru.itis.security.jwt.details.UserDetailsImpl;

import java.util.List;

public interface ChatMessageService {
    void addChatMessage(ChatMessageDto chatMessageDto, Long chatId);
    List<ChatMessageDto> getAllChatMessages(Chat chat);
    List<ChatMessageDto> getMessages(Long id);
}
