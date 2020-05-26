package ru.itis.repositories;

import ru.itis.models.Chat;
import ru.itis.models.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<Long, ChatMessage>{
    List<ChatMessage> getAllByChat(Chat chat);
}
