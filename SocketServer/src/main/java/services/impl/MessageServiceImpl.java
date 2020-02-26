package services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import dao.repositories.MessageRepository;
import dto.MessageDto;
import dto.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import models.Message;
import services.interfaces.MessageService;

import java.util.Date;

@NoArgsConstructor
public class MessageServiceImpl implements MessageService {
    @Getter
    private MessageRepository messageRepository;

    @Override
    public MessageDto message(String token, String text) {
        DecodedJWT jwt = JWT.decode(token);
        Date date = new Date(System.currentTimeMillis());
        Long userId = jwt.getClaims().get("sub").asLong();
        String nickname = jwt.getClaims().get("nick").asString();
        Message message = Message.builder()
                .userId(userId)
                .date(date)
                .nickname(nickname)
                .text(text)
                .build();
        messageRepository.save(message);
        return new MessageDto(Type.ALL, text, date, nickname);
    }

}
