package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Chat;
import ru.itis.models.User;
import ru.itis.repositories.ChatRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.security.jwt.details.UserDetailsImpl;
import ru.itis.services.interfaces.ChatService;

import java.util.Optional;

@Component
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Chat getChat(UserDetailsImpl userDetails) {
        Optional<Chat> optionalChat = chatRepository.findByUserId(userDetails.getUser().getId());
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            Chat chat = Chat.builder()
                    .owner(userDetails.getUser())
                    .build();
            chatRepository.save(chat);
            return chat;
        }
    }

    @Override
    public Chat getChatByUserId(Long id) {
        Optional<Chat> optionalChat = chatRepository.findByUserId(id);
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            throw new IllegalArgumentException("This user has no chat with u!");
        }
    }
}
