package ru.itis.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ChatMessageDto;
import ru.itis.security.jwt.details.UserDetailsImpl;
import ru.itis.services.interfaces.ChatMessageService;

import java.util.List;

@RestController
public class RestMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/rest/messages")
    public ResponseEntity<List<ChatMessageDto>> getMessages(@RequestParam("userId") Long id) {
        List<ChatMessageDto> messages = chatMessageService.getMessages(id);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/rest/messages")
    public ResponseEntity<List<ChatMessageDto>> receiveMessage(@RequestBody ChatMessageDto chatMessageDto, @RequestBody Long chatId) {
        chatMessageService.addChatMessage(chatMessageDto, chatId);
        return ResponseEntity.ok().build();
    }
}
