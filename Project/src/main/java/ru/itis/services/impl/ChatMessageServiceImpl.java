package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.dto.ChatMessageDto;
import ru.itis.models.Chat;
import ru.itis.models.ChatMessage;
import ru.itis.models.User;
import ru.itis.repositories.ChatMessageRepository;
import ru.itis.repositories.ChatRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.security.jwt.details.UserDetailsImpl;
import ru.itis.services.interfaces.ChatMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChatMessageServiceImpl implements ChatMessageService {
    private final List<ChatMessage> messages = new ArrayList<>();

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void addChatMessage(ChatMessageDto chatMessageDto, Long chatId) {
        Optional<Chat> optionalChat = chatRepository.find(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            ChatMessage chatMessage = ChatMessage.builder()
                    .text(chatMessageDto.getText())
                    .user(usersRepository.find(chatMessageDto.getUserId()).get())
                    .chat(chat)
                    .build();
            messages.add(chatMessage);
            chatMessageRepository.save(chatMessage);
            messages.notifyAll();
        }
    }

    @Override
    public List<ChatMessageDto> getAllChatMessages(Chat chat) {
        return fromChatMessage(chatMessageRepository.getAllByChat(chat));
    }

    private List<ChatMessageDto> fromChatMessage(List<ChatMessage> chatMessage) {
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        for (ChatMessage message : chatMessage)
            chatMessageDtos.add(ChatMessageDto.builder()
                    .text(message.getText())
                    .userId(message.getUser().getId())
                    .build());
        return chatMessageDtos;
    }

    @Override
    public List<ChatMessageDto> getMessages(Long id) {
        synchronized (messages) {
            if (messages.isEmpty()) {
                try {
                    messages.wait();
                } catch (InterruptedException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        List<ChatMessage> chatMessages = messages.stream()
                .filter(message -> message
                        .getUser()
                        .getId()
                        .equals(id))
                .collect(Collectors.toList());
        messages.clear();
        return fromChatMessage(chatMessages);
    }
}
