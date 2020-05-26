package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignUpDto;
import ru.itis.dto.UserDto;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;


import ru.itis.services.interfaces.EmailService;
import ru.itis.services.interfaces.MessageConvertorService;
import ru.itis.services.interfaces.SignUpService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    @Qualifier("jpaUsersRepository")
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService threadPool;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageConvertorService messageConvertorService;

    @SneakyThrows
    @Override
    public boolean signUp(SignUpDto form) {
        if(usersRepository.findByEmail(form.getEmail()).isPresent())
            return false;
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .username(form.getUsername())
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .build();

        String text = messageConvertorService.fromEmailToFtl(user.getConfirmCode());

        usersRepository.save(user);
        threadPool.submit(() -> {
            emailService.sendMail("Регистрация", text,
                    user.getEmail());
        });
        return true;
    }

    @Override
    public Optional<UserDto> confirm(String code) {
        Optional<User> optionalUser = usersRepository.findByConfirmCode(code);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setState(State.CONFIRMED);
            usersRepository.update(user.getEmail());
            return Optional.of(UserDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .build());
        }
        return Optional.empty();
    }
}
